/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.liu.data.dataSource.impl

import com.liu.data.dataSource._
import org.apache.spark.sql._

/**
  * Created by latincross on 12/29/2018.
  */
class MLSQLHbase extends MLSQLSource with MLSQLSink with MLSQLSourceInfo with MLSQLRegistry {


  override def fullFormat: String = "org.apache.spark.sql.execution.datasources.hbase"

  override def shortFormat: String = "hbase"

  override def dbSplitter: String = ":"

  override def load(reader: DataFrameReader, config: DataSourceConfig): DataFrame = {
    val Array(_dbname, _dbtable) = if (config.path.contains(dbSplitter)) {
      config.path.split(dbSplitter, 2)
    } else {
      Array("", config.path)
    }

    var namespace = ""

    val format = config.config.getOrElse("implClass", fullFormat)

    if (config.config.contains("namespace")) {
      namespace = config.config("namespace")
    }

    val inputTableName = if (namespace == "") _dbtable else s"${namespace}:${_dbtable}"

    reader.option("inputTableName", inputTableName)

    //load configs should overwrite connect configs
    reader.options(config.config)
    reader.format(format).load()
  }

  override def save(writer: DataFrameWriter[Row], config: DataSinkConfig): Unit = {
    println("save hbase")
  }

  override def register(): Unit = {
    DataSourceRegistry.register(MLSQLDataSourceKey(fullFormat, MLSQLSparkDataSourceType), this)
    DataSourceRegistry.register(MLSQLDataSourceKey(shortFormat, MLSQLSparkDataSourceType), this)
  }

  override def sourceInfo(config: DataAuthConfig): SourceInfo = {

    SourceInfo(shortFormat, "hbase_db", "hbase_table")
  }

  override def explainParams(spark: SparkSession) = {
    spark.sql("select * from spark")
  }

}
