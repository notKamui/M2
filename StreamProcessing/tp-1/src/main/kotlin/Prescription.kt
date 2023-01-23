import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.github.javafaker.Faker
import com.twitter.bijection.avro.GenericAvroCodecs
import org.apache.avro.Schema
import org.apache.avro.generic.GenericRecord
import org.apache.avro.specific.SpecificRecordBase

data class Prescription(
    var firstname: String,
    var lastname: String,
    var cip: Int,
    var price: Double,
    var idPharma: Int,
) : SpecificRecordBase() {

    fun toJson(): String = mapper.writeValueAsString(this)

    fun toAvroBinary(): ByteArray = avroConverter.apply(this)

    companion object {

        private val mapper = ObjectMapper().registerModule(KotlinModule())

        private val avroSchema = Schema.Parser().parse(this::class.java.getResourceAsStream("/prescription.avsc"))
        private val avroConverter = GenericAvroCodecs.toBinary<Prescription>(avroSchema)

        fun fromJson(json: String): Prescription = mapper.readValue(json, Prescription::class.java)

        fun fromAvroBinary(binary: ByteArray): Prescription = fromJson((avroConverter.invert(binary).get() as GenericRecord).toString())
    }

    override fun getSchema(): Schema {
        return avroSchema
    }

    override fun put(i: Int, v: Any?) = when (i) {
        0 -> firstname = v as String
        1 -> lastname = v as String
        2 -> cip = v as Int
        3 -> price = v as Double
        4 -> idPharma = v as Int
        else -> throw IllegalArgumentException("Unknown index $i")
    }

    override fun get(i: Int): Any = when (i) {
        0 -> firstname
        1 -> lastname
        2 -> cip
        3 -> price
        4 -> idPharma
        else -> throw IllegalArgumentException("Unknown index $i")
    }
}

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
