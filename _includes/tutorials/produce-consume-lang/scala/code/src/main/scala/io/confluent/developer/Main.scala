package io.confluent.developer

import java.time.{Duration, LocalDate}
import java.util

import io.confluent.developer.schema.BookType.Romance
import io.confluent.developer.schema.{Book, TypedSerde}
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig
import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.{Deserializer, Serdes, Serializer}
import org.apache.kafka.common.utils.Bytes

import scala.jdk.CollectionConverters._

object Main extends App with TypedSerde {

  import TypedSerde._
  val bookSer: Serializer[Book] = typedSerializer[Book]

  val bookDes: Deserializer[Book] = typedDeserializer[Book]

  bookSer.configure(Map(SCHEMA_REGISTRY_URL_CONFIG -> "http://localhost:8081").asJava, false)
  bookDes.configure(Map(SCHEMA_REGISTRY_URL_CONFIG -> "http://localhost:8081").asJava, false)

  val producerConfig: util.Map[String, AnyRef] = Map[String, AnyRef](
    ProducerConfig.BOOTSTRAP_SERVERS_CONFIG -> "localhost:9092"
  ).asJava

  val consumerConfig: util.Map[String, AnyRef] = Map[String, AnyRef](
    ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> "localhost:9092",
    ConsumerConfig.GROUP_ID_CONFIG -> "TEST",
    KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG -> "true"
  ).asJava

  val producer1 = new KafkaProducer[Bytes, Book](producerConfig, Serdes.Bytes().serializer(), bookSer)

  val consumer1 = new KafkaConsumer[Bytes, Book](consumerConfig, Serdes.Bytes().deserializer(), bookDes)

  producer1.send(new ProducerRecord[Bytes, Book]("topicA", Book("ok", "title", Romance, 10, LocalDate.now())))

  producer1.flush()

  consumer1.assign(Seq(new TopicPartition("topicA", 0)).asJava)

  consumer1.seekToBeginning(Seq(new TopicPartition("topicA", 0)).asJava)

  consumer1.poll(Duration.ofSeconds(3)).asScala.foreach(println)
}
