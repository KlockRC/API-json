package api

import io.circe.parser._
import io.circe.syntax._
import io.circe.{Decoder, Encoder}
import org.apache.kafka.common.serialization.{Deserializer, Serde, Serdes, Serializer}

object KafkaSerializer {
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