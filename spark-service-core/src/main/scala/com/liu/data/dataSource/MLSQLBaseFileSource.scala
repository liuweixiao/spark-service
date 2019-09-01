package com.liu.data.dataSource

import org.apache.spark.sql.{DataFrame, DataFrameReader, DataFrameWriter, Row}

abstract class MLSQLBaseFileSource extends MLSQLSource with MLSQLSink with MLSQLSourceInfo with MLSQLRegistry {
  override def load(reader: DataFrameReader, config: DataSourceConfig): DataFrame = {

    var dbtable = config.path
    // if contains splitter, then we will try to find dbname in dbMapping.
    // otherwize we will do nothing since elasticsearch use something like index/type
    // it will do no harm.
    val format = config.config.getOrElse("implClass", fullFormat)

    //load configs should overwrite connect configs
    reader.options(config.config)
    reader.format(format).load(dbtable)
  }

  def rewriteConfig(config: Map[String, String]) = {
    config
  }


  override def save(writer: DataFrameWriter[Row], config: DataSinkConfig): Any = {

   }

  override def register(): Unit = {
    DataSourceRegistry.register(MLSQLDataSourceKey(fullFormat, MLSQLSparkDataSourceType), this)
    DataSourceRegistry.register(MLSQLDataSourceKey(shortFormat, MLSQLSparkDataSourceType), this)
  }

  override def sourceInfo(config: DataAuthConfig): SourceInfo = {
    SourceInfo(shortFormat, "db", "table")

  }

}