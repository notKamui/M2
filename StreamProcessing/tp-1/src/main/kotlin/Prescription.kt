import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule

data class Prescription(
    val firstname: String,
    val lastname: String,
    val cip: Int,
    val price: Double,
    val idPharma: Int,
) {

    fun toJson(): String = mapper.writeValueAsString(this)

    companion object {
        private val mapper = ObjectMapper().registerModule(KotlinModule())

        fun fromJson(json: String): Prescription = mapper.readValue(json, Prescription::class.java)
    }
}