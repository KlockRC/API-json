package api

import io.circe.*
import io.circe.parser.*
import io.circe.syntax.*
import org.apache.kafka.common.serialization.{Serializer, Deserializer, Serde, Serdes}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Await}
import java.util.Properties
import scala.util.Random
import models.{Produto, Cliente, Item, Pagamento, Pedido, Review, Vendedor}

object connect {
    private val props = new Properties()
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
    val temporizador = Random.between(50, 100)
    val producer = connect.producer
    implicit val produtoSerde: Serde[Produto] = serializer.serializador[Produto]
    implicit val clienteSerde: Serde[Cliente] = serializer.serializador[Cliente]
    implicit val itemSerde: Serde[Item] = serializer.serializador[Item]
    implicit val pagamentoSerde: Serde[Pagamento] = serializer.serializador[Pagamento]
    implicit val pedidoSerde: Serde[Pedido] = serializer.serializador[Pedido]
    implicit val reviewSerde: Serde[Review] = serializer.serializador[Review]
    implicit val vendedorSerde: Serde[Vendedor] = serializer.serializador[Vendedor]
    
    def produtosKafka(produtos: List[Produto]): Future[Unit] = Future {
        for (produto <- produtos) {
            val serializedProduto = produtoSerde.serializer().serialize("Topico-Produto", produto)
            val record = new ProducerRecord[String, Array[Byte]]("Topico-Produto", "key1", serializedProduto)
            try {
                producer.send(record).get()
            } catch {
                case e: Exception =>
                    println(s"Erro ao enviar mensagem: ${e.getMessage}")
            }
            Thread.sleep(temporizador)
        }
        producer.close()
    }
    def clientesKafka(clientes: List[Cliente]): Future[Unit] = Future{
        for (cliente <- clientes) {
            val serializerCliente = clienteSerde.serializer().serialize("Topico-Cliente", cliente)
            val record = new ProducerRecord[String, Array[Byte]]("Topico-Cliente", "key2", serializerCliente)
            try {
                producer.send(record)
            } catch {
                case e: Exception =>
                    println(s"Erro ao enviar mensagem: ${e.getMessage}")
            }
            Thread.sleep(temporizador)
        }
        producer.close()
    }
}