import java.time.Duration
import java.util.Properties
import org.apache.kafka.clients.consumer.KafkaConsumer

fun main() {
    val consumer = KafkaConsumer<String, String>(Properties().apply {
        put("bootstrap.servers", "localhost:9092")
        put("group.id", "consumer-group")
        put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
        put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    })
    consumer.subscribe(listOf("prescriptions"))
    consumer.use { kafka ->
        while (true) {
            val records = kafka.poll(Duration.ofSeconds(1))
            records.forEach { record ->
                println(Prescription.fromJson(record.value()))
            }
        }
    }
}