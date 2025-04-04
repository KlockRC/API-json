package api

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.kafka.common.serialization.StringSerializer
import java.util.Properties
import io.circe.syntax._
import scala.util.Random
import models.Produto


object Kafkaproducer {
   
    
    def produtos(produto: List[Produto]): Unit = {
        val props = new Properties()
        props.put("bootstrap.servers", "localhost:9092")
        props.put("key.serializer", classOf[StringSerializer].getName)
        props.put("value.serializer", classOf[StringSerializer].getName)
        val producer = new KafkaProducer[String, String](props)

        for (i <- produto) {
            val tempo = Random.between(500, 2500)
            val jsonString = i.asJson.noSpaces
            val record = new ProducerRecord[String, String]("Produto-topico", jsonString)
            producer.send(record)
            println(s"🔹 Mensagem enviada para o Kafka: $jsonString")
            Thread.sleep(tempo)
        }
    }
}

object teste {
    
}