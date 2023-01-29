import java.sql.DriverManager
import java.util.Properties
import kotlin.random.Random
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.ByteArraySerializer
import org.apache.kafka.common.serialization.VoidSerializer

object Producer {

    val producer = KafkaProducer<Void, ByteArray>(Properties().apply {
        put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
        put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, VoidSerializer::class.java)
        put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer::class.java)
    })
}

fun main() {
    val (drugs, pharmas) = getDrugsAndPharma()

    Producer.producer.producerLoop(drugs, pharmas)
}

fun getDrugsAndPharma(): Pair<List<Drug>, List<Pharma>> {
    val databaseConnection = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres",
        "postgres",
        "postgres"
    )

    val drugs = buildList {
        databaseConnection.createStatement().executeQuery("SELECT * FROM drugs4projet").apply {
            while (next()) {
                val cip = getInt("cip")
                val price = getDouble("prix")
                add(Drug(cip, price))
            }
        }
    }

    val pharmas = buildList {
        databaseConnection.createStatement().executeQuery("SELECT * FROM pharm4projet").apply {
            while (next()) {
                val id = getInt("id")
                val name = getString("nom")
                val address = getString("adresse")
                val department = getString("depart")
                val region = getString("region")
                add(Pharma(id, name, address, department, region))
            }
        }
    }
    databaseConnection.close()

    return drugs to pharmas
}

fun KafkaProducer<Void, ByteArray>.producerLoop(
    drugs: List<Drug>,
    pharmas: List<Pharma>,
): Unit = use { kafka ->
    var sending = false
    while (true) {
        val prescription = fakePrescription(drugs, pharmas)
        val record = ProducerRecord<Void, ByteArray>(
            Topics.PRESCRIPTIONS,
            toAvroBinary(prescription),
        )
        if (!sending) {
            sending = true
            kafka.send(record) { _, e ->
                if (e?.printStackTrace() != null) return@send
                sending = false
                println("Value sent: $prescription")
            }
        }

        Thread.sleep(Random.nextLong(500))
    }
}
