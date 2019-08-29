package com.liu.data

import com.liu.data.utils.JacksonUtil
import org.apache.spark.sql.SparkSession

object PlatformManger {

  val sparkSession = getSparkSession()

  def getSparkSession(): SparkSession = {
    SparkSession
      .builder()
      .master("local[2]")
      //      .enableHiveSupport()
      .getOrCreate()
  }

  def runSql(sql: String) : String = {
    val result = sparkSession.sql(sql).limit(1000).collectAsList()
    JacksonUtil.toJson(result)
  }

  def main(args: Array[String]): Unit = {

  }

}

