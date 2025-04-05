package api

import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._
import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import java.util.Properties
import scala.util.Random
import models.Produto

object Teste {
    val props = new Properties()
    props.put("bootstrap.servers", "172.22.90.83:9094")
    props.put("max.request.size", "200000")
    props.put("log.level", "DEBUG")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer")
    val producer = new KafkaProducer[String, Array[Byte]](props)
}

object KafkaProducerApp {

    implicit def jsonSerializer: Serde[Produto] = {
        val serializer = (produto: Produto) => produto.asJson.noSpaces.getBytes("UTF-8")
        val deserializer = (bytes: Array[Byte]) => {
            val jsonString = new String(bytes, "UTF-8")
            decode[Produto](jsonString).toOption
        }

        Serdes.serdeFrom(
            new org.apache.kafka.common.serialization.Serializer[Produto] {
                override def serialize(topic: String, data: Produto): Array[Byte] =
                    Option(data).map(serializer).orNull
            },
            new org.apache.kafka.common.serialization.Deserializer[Produto] {
                override def deserialize(topic: String, data: Array[Byte]): Produto =
                    Option(data).flatMap(deserializer).orNull
            }
        )
    }

    def produtos(produtoList: List[Produto]): Unit = {
        val producer = Teste.producer

        for (produto <- produtoList) {
            val tempo = Random.between(0, 100)
            val serializedProduto = jsonSerializer.serializer().serialize("kafika", produto)
            val record = new ProducerRecord[String, Array[Byte]]("kafika", "key1", serializedProduto)

            try {
                producer.send(record).get() // Garantir envio síncrono para capturar exceções
                println(s"🔹 Mensagem enviada com sucesso: ${new String(serializedProduto, "UTF-8")}")
            } catch {
                case e: Exception =>
                    println(s"⚠️ Erro ao enviar mensagem: ${e.getMessage}")
            }

            // Thread.sleep(tempo) // Simula um intervalo aleatório entre envios
        }

        producer.flush()
        producer.close() // Fecha o producer após o envio
    }
}