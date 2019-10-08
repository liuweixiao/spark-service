package com.liu.data

import java.util.UUID

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.streaming.Trigger


object StructuredKafkaWordCount {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder
      .master("local[2]")
      .appName("StructuredKafkaWordCount")
      .config("spark.metrics.namespace", "StructuredKafkaWordCount")
      .config("spark.metrics.conf", "./metrics.properties")
      .getOrCreate()
    spark.conf.set("spark.sql.streaming.metricsEnabled", "true")

    import spark.implicits._

    // Create DataSet representing the stream of input lines from kafka
    val lines = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("subscribe", "word-count")
      .load()
      .selectExpr("CAST(value AS STRING)")
      .as[String]

    // Generate running word count
    val wordCounts = lines.flatMap(_.split(" ")).groupBy("value").count()

    // Start running the query that prints the running counts to the console
    val queryName = this.getClass.getName
    val query = wordCounts.writeStream
        .queryName(queryName)
      .outputMode("complete")
      .trigger(Trigger.ProcessingTime("10 seconds"))
      .format("console")
      .option("checkpointLocation", s"./checkpointLocation/${queryName}")
      .start()

    query.awaitTermination()
  }

}
// scalastyle:on println
