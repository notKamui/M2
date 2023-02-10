import com.bigdata.journal.Options
import com.bigdata.rdf.sail.BigdataSail
import com.bigdata.rdf.sail.BigdataSailRepository
import java.util.Properties
import org.openrdf.OpenRDFException
import org.openrdf.model.impl.LiteralImpl
import org.openrdf.model.impl.StatementImpl
import org.openrdf.model.impl.URIImpl
import org.openrdf.query.QueryLanguage


fun main() {
    val props = Properties()
    props[Options.BUFFER_MODE] = "DiskRW" // persistent file system located journal

    props[Options.FILE] = "/tmp/blazegraph/test.jnl" // journal file location

    val sail = BigdataSail(props) // instantiate a sail

    val repo = BigdataSailRepository(sail) // create a Sesame repository


    repo.initialize()

    try {
        // prepare a statement
        val sub = URIImpl("http://example.com/Macaron")
        val pred = URIImpl("http://example.com/loves")
        val obj = LiteralImpl("Cookies")
        val stmt = StatementImpl(sub, pred, obj)

        // open repository connection
        var cxn = repo.connection

        // upload data to repository
        try {
            cxn.begin()
            cxn.add(stmt)
            cxn.commit()
        } catch (ex: OpenRDFException) {
            cxn.rollback()
            throw ex
        } finally {
            // close the repository connection
            cxn.close()
        }

        // open connection
        cxn = repo.readOnlyConnection

        // evaluate sparql query
        try {
            val tupleQuery = cxn.prepareTupleQuery(QueryLanguage.SPARQL, "select * where { ?s ?p ?o . }")
            val result = tupleQuery.evaluate()
            try {
                while (result.hasNext()) {
                    val bindingSet = result.next()
                    bindingSet.bindingNames.forEach { name ->
                        println("$name = ${bindingSet.getValue(name)}")
                    }
                    println()
                }
            } finally {
                result.close()
            }
        } finally {
            // close the repository connection
            cxn.close()
        }
    } finally {
        repo.shutDown()
    }
}