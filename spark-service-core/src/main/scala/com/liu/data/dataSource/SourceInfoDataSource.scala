package com.liu.data.dataSource

import org.apache.spark.sql._

trait  SourceInfoDataSource {

  def dbSplitter = {
    "."
  }

  def fullFormat: String

  def shortFormat: String

  def aliasFormat: String = {
    shortFormat
  }

  def sourceInfo(config: DataAuthConfig): SourceInfo

}

trait OceanSource extends SourceInfoDataSource {
  def load(reader: DataFrameReader, config: DataSourceConfig): DataFrame
}


trait OceanSink extends SourceInfoDataSource {
  def save(writer: DataFrameWriter[Row], config: DataSinkConfig): Any
}

trait OceanDirectSource extends SourceInfoDataSource {
  def load(reader: DataFrameReader, config: DataSourceConfig): DataFrame
}

trait OceanDirectSink extends SourceInfoDataSource {
  def save(writer: DataFrameWriter[Row], config: DataSinkConfig): Any
}

trait OceanSourceInfo extends SourceInfoDataSource {
  def sourceInfo(config: DataAuthConfig): SourceInfo

  def explainParams(spark: SparkSession): DataFrame = {
    import spark.implicits._
    spark.createDataset[String](Seq()).toDF("name")
  }
}

case class SourceInfo(sourceType: String, db: String, table: String)

