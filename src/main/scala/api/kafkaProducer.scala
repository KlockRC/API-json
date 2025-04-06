package api

import io.circe.*
import io.circe.generic.auto.*
import io.circe.parser.*
import io.circe.syntax.*
import org.apache.kafka.common.serialization.{Serializer, Deserializer, Serde, Serdes}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

import java.util.Properties
import scala.util.Random
import models.Produto

object conect {
    val props = new Properties()
    props.put("bootstrap.servers", "172.22.90.83:9094")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer")
    val producer = new KafkaProducer[String, Array[Byte]](props)
}

object serializer {
    implicit def serializador[A >: Null](implicit encoder: Encoder[A], decoder: Decoder[A]): Serde[A] = {

        val serializer = new Serializer[A] {
            override def serialize(topic: String, data: A): Array[Byte] = {
                Option(data).map(_.asJson.noSpaces.getBytes("UTF-8")).orNull
            }
        }
        val deserializer = new Deserializer[A] {
            override def deserialize(topic: String, data: Array[Byte]): A = {
                Option(data).map(bytes => new String(bytes, "UTF-8")).flatMap(json => decode[A](json).toOption).orNull
            }
        }

        Serdes.serdeFrom(serializer, deserializer)
    }
}

object KafkaProducerApp {
    val tempo = Random.between(50, 100)
    implicit val produtoSerde: Serde[Produto] = serializer.serializador[Produto]

    def produtosKafka(produtos: List[Produto]): Unit = {
        val producer = conect.producer

        for (produto <- produtos) {
            val serializedProduto = produtoSerde.serializer().serialize("kafika", produto)
            val record = new ProducerRecord[String, Array[Byte]]("kafika", "key1", serializedProduto)

            try {
                producer.send(record).get()
                println(s"Mensagem enviada com sucesso: ${new String(serializedProduto, "UTF-8")}")
            } catch {
                case e: Exception =>
                    println(s"Erro ao enviar mensagem: ${e.getMessage}")
            }

            Thread.sleep(tempo)
        }

        producer.flush()
        producer.close()
    }
}