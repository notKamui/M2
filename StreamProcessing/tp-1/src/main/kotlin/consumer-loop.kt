import java.time.Duration
import java.util.Properties
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.errors.WakeupException
import org.apache.kafka.common.serialization.ByteArrayDeserializer
import org.apache.kafka.common.serialization.StringDeserializer


fun main() {
    val numConsumers = 3
    val groupId = "consumer-group2"
    val topics: List<String> = mutableListOf("prescriptionsDispatched")
    val executor = Executors.newFixedThreadPool(numConsumers)
    val consumers = mutableListOf<ConsumerLoop>()
    for (i in 0 until numConsumers) {
        val consumer = ConsumerLoop(i, groupId, topics)
        consumers.add(consumer)
        executor.submit(consumer)
    }
    Runtime.getRuntime().addShutdownHook(object : Thread() {
        override fun run() {
            for (consumer in consumers) {
                consumer.shutdown()
            }
            executor.shutdown()
            try {
                executor.awaitTermination(5000, TimeUnit.MILLISECONDS)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    })
}

class ConsumerLoop(
    private val id: Int,
    groupId: String?,
    private val topics: List<String>,
) : Runnable {

    private val consumer: KafkaConsumer<String, ByteArray>

    init {
        val props = Properties()
        props["bootstrap.servers"] = "localhost:9092,localhost:9093,localhost:9094"
        props["group.id"] = groupId
        props["key.deserializer"] = StringDeserializer::class.java.name
        props["value.deserializer"] = ByteArrayDeserializer::class.java.name
        consumer = KafkaConsumer<String, ByteArray>(props)
    }

    override fun run() {
        try {
            consumer.subscribe(topics)
            while (true) {
                val records = consumer.poll(Duration.ofSeconds(1))
                records.forEach { record ->
                    val data = mapOf<String, Any?>(
                        "partition" to record.partition(),
                        "offset" to record.offset(),
                        "key" to record.key(),
                    )
                    val prescription = fromAvroBinary(record.value())
                    synchronized(history) {
                        history.merge(prescription.cip, prescription.price, Double::plus)
                    }
                    println("$id: $data -> $prescription | $history")
                }
            }
        } catch (e: WakeupException) {
            // ignore for shutdown
        } finally {
            consumer.close()
        }
    }

    fun shutdown() {
        consumer.wakeup()
    }

    companion object {

        // cip to price total
        val history = mutableMapOf<Int, Double>()
    }
}
