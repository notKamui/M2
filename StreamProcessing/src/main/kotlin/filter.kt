import java.util.Properties
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.StreamsConfig

object Filter {

    val stream = StreamsBuilder().apply {
        stream<Void, ByteArray>(Topics.PRESCRIPTIONS_ANONYMIZED)
            .mapValues { _, value -> fromAvroBinary(value) }
            .filter { _, value -> value.price > 4 }
            .also { it.foreach { _, value -> println(value) } }
    }.let {
        KafkaStreams(it.build(), Properties().apply {
            put(StreamsConfig.APPLICATION_ID_CONFIG, "prescription-filter")
            put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
            put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Void()::class.java)
            put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.ByteArray()::class.java)
        })
    }
}

fun main() {
    Filter.stream.start()
    readln() // Wait for user input to stop the stream
    Filter.stream.close()
}