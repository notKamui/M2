import com.github.javafaker.Faker
import kotlin.random.Random

fun fakePrescription(drugs: List<Drug>, pharmas: List<Pharma>): Prescription {
    val (cip, price) = drugs.random()
    val (id) = pharmas.random()
    val (firstname, lastname) = Faker().name().let { it.firstName() to it.lastName() }
    return Prescription(firstname, lastname, cip, price.offset(10.0), id)
}

fun Double.offset(maxOffsetPercent: Double): Double {
    val offset = this * maxOffsetPercent / 100
    return this + offset * (if (Random.nextBoolean()) 1 else -1)
}