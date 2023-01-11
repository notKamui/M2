import java.time.Duration
import java.util.Properties
import org.apache.kafka.clients.consumer.KafkaConsumer

fun main() {
    val consumer = KafkaConsumer<String, ByteArray>(Properties().apply {
        put("bootstrap.servers", "localhost:9092")
        put("group.id", "consumer-group")
        put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
        put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer")
        put("value.deserializer.specific.avro.reader", "true")
    })
    consumer.subscribe(listOf("prescriptionsBin"))
    consumer.use { kafka ->
        while (true) {
            val records = kafka.poll(Duration.ofSeconds(1))
            records.forEach { record ->
                println(Prescription.fromAvroBinary(record.value()))
            }
        }
    }
}