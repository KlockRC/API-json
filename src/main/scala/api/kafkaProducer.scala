package api

import org.slf4j.LoggerFactory
import io.circe.*
import io.circe.parser.*
import io.circe.syntax.*
import org.apache.kafka.common.serialization.{Serializer, Deserializer, Serde, Serdes}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import java.util.Properties
import scala.util.Random
import models.{Produto, Cliente, Item, Pagamento, Pedido, Review, Vendedor}

val logger = LoggerFactory.getLogger(getClass)

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
    private val temporizador = Random.between(5, 10)
    private val producer = connect.producer
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
                logger.info("Mensagem enviada: {}", produto)
            } catch {
                case e: Exception =>
                    logger.error(s"Erro ao enviar mensagem: ${e.getMessage}")
            }
            Thread.sleep(temporizador)
        }
    }
    def clientesKafka(clientes: List[Cliente]): Future[Unit] = Future{
        for (cliente <- clientes) {
            val serializedCliente = clienteSerde.serializer().serialize("Topico-Cliente", cliente)
            val record = new ProducerRecord[String, Array[Byte]]("Topico-Cliente", "key2", serializedCliente)
            try {
                producer.send(record).get()
                logger.info("Mensagem enviada: {}", cliente)
            } catch {
                case e: Exception =>
                    logger.error(s"Erro ao enviar mensagem: ${e.getMessage}")
            }
            Thread.sleep(temporizador)
        }
    }
    def itemKafka (items: List[Item]): Future[Unit] = Future {
        for (item <- items) {
            val serializedItem = itemSerde.serializer().serialize("Topico-Item", item)
            val record = new ProducerRecord[String, Array[Byte]]("Topico-Item", "key3", serializedItem)
            try {
                producer.send(record).get()
                logger.info("Mensagem enviada: {}", item)
            }catch {
                case e: Exception =>
                logger.error(s"Erro ao enviar mensagem: ${e.getMessage}")

            }
            Thread.sleep(temporizador)
        }
    }
    def pagamentoKafka(pagamentos: List[Pagamento]): Future[Unit] = Future{
        for (pagamento <- pagamentos) {
            val serializedPagamento = pagamentoSerde.serializer().serialize("Topico-Pagamento", pagamento)
            val record = new ProducerRecord[String, Array[Byte]]("Topico-Pagamento", "Key4", serializedPagamento)
            try {
                producer.send(record).get()
                logger.info("Mensagem enviada: {}", pagamento)
            }catch
                case e: Exception =>
                    logger.error(s"Erro ao enviar mensagem: ${e.getMessage}")
            Thread.sleep(temporizador)        
        }
    }
    def pedidoKafka (pedidos: List[Pedido]): Future[Unit] = Future {
        for (pedido <- pedidos) {
            val serializedPedido = pedidoSerde.serializer().serialize("Topico-Pedido", pedido)
            val record = new ProducerRecord[String, Array[Byte]]("Topico-Pedido", "Key5", serializedPedido)
            try {
                producer.send(record).get()
                logger.info("Mensagem enviada: {}", pedido)
            }catch
                case e: Exception =>
                    logger.error(s"Erro ao enviar mensagem: ${e.getMessage}")
            Thread.sleep(temporizador)
        }
    }
    def reviewKafka (reviews: List[Review]): Future[Unit] = Future{
        for (review <- reviews) {
            val serializedReview = reviewSerde.serializer().serialize("Topico-Review", review)
            val record = new ProducerRecord[String, Array[Byte]]("Topico-Review", "Key6", serializedReview)
            try {
                producer.send(record).get()
                logger.info("Mensagem enviada: {}", review)
            }catch
                case e: Exception =>
                    logger.error(s"Erro ao enviar mensagem: ${e.getMessage}")
            Thread.sleep(temporizador)
        }
    }
    def vendedorKafka (vendedores: List[Vendedor]): Future[Unit] = Future {
        for (vendedor <- vendedores) {
            val serializedVendedor = vendedorSerde.serializer().serialize("Topico-Vendedor", vendedor)
            val record = new ProducerRecord[String, Array[Byte]]("Topico-Vendedor", "Key7", serializedVendedor)
            try {
                producer.send(record).get()
                logger.info("Mensagem enviada: {}", vendedor)
            }catch
                case e: Exception =>
                    logger.error(s"Erro ao enviar mensagem: ${e.getMessage}")
            Thread.sleep(temporizador)
        }
    }
}