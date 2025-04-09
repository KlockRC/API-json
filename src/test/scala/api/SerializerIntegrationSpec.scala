package api

import models.Produto
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import io.circe.generic.auto._
import org.apache.kafka.common.serialization.Serde



class SerializerIntegrationSpec extends AnyWordSpec with Matchers {
  "Serializer" should {
    "Serializar e Deserializar Models para Array Bytes" in {
      val test = Produto(
        "1e9e8ef04dbcff4541ed26657ea517e5",
        "perfumaria",
        "40",
        "287",
        "1",
        "225",
        "16",
        "10",
        "14"
      )
      val serde: Serde[Produto] = KafkaSerializer.serializador[Produto]
      val serializer = serde.serializer()
      val deserializer = serde.deserializer()

      val bytes: Array[Byte] = serializer.serialize("test-topic", test)

      val resultado: Produto = deserializer.deserialize("test-topic", bytes)

      resultado shouldEqual test

    }
  }
}
