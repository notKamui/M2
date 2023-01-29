import java.util.Properties
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.StreamsConfig

object Processor {

    val stream = StreamsBuilder().apply {
        stream<Void, ByteArray>(Topics.PRESCRIPTIONS)
            .mapValues { _, value -> anonymizePrescription(value) }
            .also { it.foreach { _, value -> println(fromAvroBinary(value)) } }
            .to(Topics.PRESCRIPTIONS_ANONYMIZED)
    }.let {
        KafkaStreams(it.build(), Properties().apply {
            put(StreamsConfig.APPLICATION_ID_CONFIG, "prescription-processor")
            put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
            put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Void()::class.java)
            put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.ByteArray()::class.java)
        })
    }

    private fun anonymizePrescription(value: ByteArray): ByteArray {
        val anonymized = fromAvroBinary(value).apply {
            firstname = "***"
            lastname = "***"
        }
        return toAvroBinary(anonymized)
    }
}

fun main() {
    Processor.stream.start()
    readln() // Wait for user input to stop the stream
    Processor.stream.close()
}
