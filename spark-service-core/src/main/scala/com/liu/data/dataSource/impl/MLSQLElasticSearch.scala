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

class MLSQLElasticSearch() extends MLSQLSource with MLSQLSink with MLSQLSourceInfo with MLSQLRegistry  {

  override def fullFormat: String = "org.elasticsearch.spark.sql"

  override def shortFormat: String = "es"

  override def dbSplitter: String = "/"

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

  override def save(writer: DataFrameWriter[Row], config: DataSinkConfig): Unit = {

  }

  override def register(): Unit = {
    DataSourceRegistry.register(MLSQLDataSourceKey(fullFormat, MLSQLSparkDataSourceType), this)
    DataSourceRegistry.register(MLSQLDataSourceKey(shortFormat, MLSQLSparkDataSourceType), this)
  }

  override def sourceInfo(config: DataAuthConfig): SourceInfo = {
    SourceInfo(shortFormat, "es_db", "es_table")
  }

  override def explainParams(spark: SparkSession) = {
    spark.sql("select * from spark")
  }

}
