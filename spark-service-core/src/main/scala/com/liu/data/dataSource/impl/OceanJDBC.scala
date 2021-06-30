package com.liu.data.dataSource.impl

import com.liu.data.core.DataSource
import com.liu.data.dataSource._
import org.apache.spark.sql.{DataFrame, DataFrameReader, DataFrameWriter, Row}

@DataSource
class OceanJDBC extends OceanSource with OceanSink with OceanSourceInfo{

  def toSplit = "\\."

  override def load(reader: DataFrameReader, config: DataSourceConfig): DataFrame = ???

  override def save(writer: DataFrameWriter[Row], config: DataSinkConfig): Any = ???

  override def sourceInfo(config: DataAuthConfig): SourceInfo = {
    val Array(_dbname, _dbtable) = if (config.path.contains(dbSplitter)) {
      config.path.split(toSplit, 2)
    } else {
      Array("", config.path)
    }

    val url = if (config.config.contains("url")) {
      config.config.get("url").get
    } else {
      val format = config.config.getOrElse("implClass", fullFormat)

      ConnectMeta.options(DBMappingKey(format, _dbname)) match {
        case Some(item) => item("url")
        case None => throw new RuntimeException(
          s"""
             |format: ${format}
             |ref:${_dbname}
             |However ref is not found,
             |Have you  set the connect statement properly?
           """.stripMargin)
      }
    }

    val dataSourceType = url.split(":")(1)
    val dbName = url.substring(url.lastIndexOf('/') + 1).takeWhile(_ != '?')
    val si = SourceInfo(dataSourceType, dbName, _dbtable)
    SourceTypeRegistry.register(dataSourceType, si)
    si
  }

  override def fullFormat: String = "jdbc"

  override def shortFormat: String = fullFormat
}
