package config

import org.apache.kafka.clients.producer.KafkaProducer
import java.util.Properties

object KafkaConfig {
  private val props = new Properties()
  props.put("bootstrap.servers", "172.22.90.83:9094")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer")
  val producerConf = new KafkaProducer[String, Array[Byte]](props)
}
