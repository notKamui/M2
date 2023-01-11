import java.sql.DriverManager
import java.util.Date
import java.util.Properties
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord

fun main() {
    val (drugs, pharmas) = getDrugsAndPharma()

    KafkaProducer<String, ByteArray>(Properties().apply {
        put("bootstrap.servers", "localhost:9092")
        put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
        put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer")
        put("value.deserializer.specific.avro.reader", "true")
    }).producerLoop(drugs, pharmas)
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

fun KafkaProducer<String, ByteArray>.producerLoop(
    drugs: List<Drug>,
    pharmas: List<Pharma>
): Unit = use { kafka ->
    var sending = false
    while (true) {
        val prescription = fakePrescription(drugs, pharmas)
        val record = ProducerRecord("prescriptionsBin", "prescription-${Date()}", prescription.toAvroBinary())
        if (!sending) {
            sending = true
            kafka.send(record) { _, e ->
                if (e?.printStackTrace() != null) return@send
                sending = false
                println("Value sent: $prescription")
            }
        }

        Thread.sleep(kotlin.random.Random.nextLong(500))
    }
}
