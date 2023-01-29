import java.time.Duration
import java.util.Properties
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord

fun main() {
    val consumer = KafkaConsumer<String, ByteArray>(Properties().apply {
        put("bootstrap.servers", "localhost:9092")
        put("group.id", "consumer-group")
        put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
        put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer")
    })
    val producer = KafkaProducer<String, ByteArray>(Properties().apply {
        put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094")
        put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
        put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer")
    })

    consumer.subscribe(listOf("prescriptions"))
    consumer.use { kafkaConsumer ->
        while (true) {
            val records = kafkaConsumer.poll(Duration.ofSeconds(1))
            records.forEach { record ->
                val prescription = fromAvroBinary(record.value())
                producer.send(ProducerRecord(
                    "prescriptionsDispatched",
                    prescription.cip.toString(),
                    record.value())
                ) { _, e ->
                    if (e?.printStackTrace() != null) return@send
                    println("Prescription $prescription dispatched")
                }
            }
        }
    }
}