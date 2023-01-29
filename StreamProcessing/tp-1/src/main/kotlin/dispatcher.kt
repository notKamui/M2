import java.time.Duration
import java.util.Properties
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.ByteArrayDeserializer
import org.apache.kafka.common.serialization.ByteArraySerializer
import org.apache.kafka.common.serialization.StringSerializer
import org.apache.kafka.common.serialization.VoidDeserializer

object Dispatcher {

    val consumer = KafkaConsumer<Void, ByteArray>(Properties().apply {
        put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
        put(ConsumerConfig.GROUP_ID_CONFIG, "consumer-group")
        put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, VoidDeserializer::class.java)
        put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer::class.java)
    })
    val producer = KafkaProducer<String, ByteArray>(Properties().apply {
        put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093,localhost:9094")
        put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java)
        put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer::class.java)
    })
}

fun main() {
    Dispatcher.consumer.subscribe(listOf(Topics.PRESCRIPTIONS))
    Dispatcher.consumer.use { kafkaConsumer ->
        while (true) {
            val records = kafkaConsumer.poll(Duration.ofSeconds(1))
            records.forEach(ConsumerRecord<Void, ByteArray>::dispatch)
        }
    }
}

fun ConsumerRecord<Void, ByteArray>.dispatch() {
    val prescription = fromAvroBinary(value())
    Dispatcher.producer.send(
        ProducerRecord(
            Topics.PRESCRIPTIONS_DISPATCHED,
            prescription.cip.toString(),
            value()
        )
    ) { _, e ->
        if (e?.printStackTrace() != null) return@send
        println("Prescription $prescription dispatched")
    }
}