import kotlin.random.Random

fun Double.offset(maxOffsetPercent: Double): Double {
    val offset = this * maxOffsetPercent / 100
    return this + offset * (if (Random.nextBoolean()) 1 else -1)
}