package com.liu.data.dataSource.impl

import com.liu.data.dataSource.{DataSourceRegistry, MLSQLBaseFileSource, MLSQLDataSourceKey, MLSQLSparkDataSourceType}
import org.apache.spark.sql._

/**
  * 2019-02-25 WilliamZhu(allwefantasy@gmail.com)
  */
class MLSQLCSV() extends MLSQLBaseFileSource {

  override def explainParams(spark: SparkSession) = {
    spark.sql("select * from spark")
  }

  override def register(): Unit = {
    DataSourceRegistry.register(MLSQLDataSourceKey(fullFormat, MLSQLSparkDataSourceType), this)
    DataSourceRegistry.register(MLSQLDataSourceKey(shortFormat, MLSQLSparkDataSourceType), this)
  }


  override def fullFormat: String = "csv"

  override def shortFormat: String = "csv"

  }
