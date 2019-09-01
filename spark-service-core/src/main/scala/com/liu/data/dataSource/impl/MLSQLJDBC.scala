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

import java.util.Properties

import com.liu.data.dataSource._
import org.apache.spark.sql._
import org.apache.spark.sql.execution.datasources.jdbc.JDBCOptions

class MLSQLJDBC() extends MLSQLSource with MLSQLSink with MLSQLSourceInfo with MLSQLRegistry  {


  override def fullFormat: String = "jdbc"

  override def shortFormat: String = fullFormat

  override def dbSplitter: String = "."

  def toSplit = "\\."

  override def load(reader: DataFrameReader, config: DataSourceConfig): DataFrame = {
    val dbtable = ""
    val format = ""
    reader.format(format).load(dbtable)
  }


  override def save(writer: DataFrameWriter[Row], config: DataSinkConfig): Unit = {
    println("save to hbase table")
  }

  override def register(): Unit = {
    DataSourceRegistry.register(MLSQLDataSourceKey(fullFormat, MLSQLSparkDataSourceType), this)
    DataSourceRegistry.register(MLSQLDataSourceKey(shortFormat, MLSQLSparkDataSourceType), this)
  }

  def parseTableAndColumnFromStr(str: String): (String, String) = {
    val cleanedStr = cleanStr(str)
    val dbAndTable = cleanedStr.split("\\.")
    if (dbAndTable.length > 1) {
      val table = dbAndTable(0)
      val column = dbAndTable.splitAt(1)._2.mkString(".")
      (table, column)
    } else {
      (cleanedStr, cleanedStr)
    }
  }

  def cleanStr(str: String): String = {
    if (str.startsWith("`") || str.startsWith("\""))
      str.substring(1, str.length - 1)
    else str
  }

  override def sourceInfo(config: DataAuthConfig): SourceInfo = {
    SourceInfo(shortFormat, "mysql_db", "hive_table")

  }

  override def explainParams(spark: SparkSession) = {
    spark.sql("select * from spark")
  }

}

