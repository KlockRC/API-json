package config

import org.apache.kafka.clients.producer.KafkaProducer
import java.util.Properties

 val myEnvVar = sys.env.getOrElse("KAFKA_BOOTSTRAP_SERVERS", "localhost:9092")

object KafkaConfig {
  private val props = new Properties()
  props.put("bootstrap.servers", myEnvVar)
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer")
  val producerConf = new KafkaProducer[String, Array[Byte]](props)
}
