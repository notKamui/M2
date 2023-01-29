import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.javafaker.Faker
import com.twitter.bijection.Bijection

private val prescriptionBijection = Bijection.build<Prescription, ByteArray>(
    { Prescription.getEncoder().encode(it).array() },
    { Prescription.getDecoder().decode(it) }
)

fun toAvroBinary(prescription: Prescription): ByteArray = prescriptionBijection.apply(prescription)
fun fromAvroBinary(binary: ByteArray): Prescription = prescriptionBijection.invert(binary)

fun toJson(prescription: Prescription): String = prescription.toString()
fun fromJson(json: String): Prescription = ObjectMapper().readValue(json)

data class Pharma(
    val id: Int,
    val name: String,
    val address: String,
    val department: String,
    val region: String,
)

data class Drug(val cip: Int, val price: Double)

fun fakePrescription(drugs: List<Drug>, pharmas: List<Pharma>): Prescription {
    val (cip, price) = drugs.random()
    val (id) = pharmas.random()
    val (firstname, lastname) = Faker().name().let { it.firstName() to it.lastName() }
    return Prescription(firstname, lastname, cip, price.offset(10.0), id)
}
