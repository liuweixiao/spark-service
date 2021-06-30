package com.liu.data

import com.liu.data.excute.{ScriptSQLExec, ScriptSQLExecListener}
import com.liu.data.utils.JacksonUtil
import org.apache.spark.sql.SparkSession

object PlatformManger {

   private var sparkSession: SparkSession = null

  def getSparkSession(): SparkSession = {
    SparkSession
      .builder()
      .master("local[2]")
      //      .enableHiveSupport()
      .getOrCreate()
  }
  def run(): Boolean = {
    sparkSession = getSparkSession()
    // 初始化脚本

    sparkSession.sparkContext.getConf.getBoolean("is_start_server", true)
  }

  def runSql(sql: String) : String = {
    val result = sparkSession.sql(sql).limit(1000).collectAsList()
    JacksonUtil.toJson(result)
  }

  def runScript(sql: String) : String = {
    val context = new ScriptSQLExecListener(sparkSession)
    ScriptSQLExec.parse(sql, context)

//    getScriptResult(context, sparkSession)

    JacksonUtil.toJson(Map("result" -> "执行成功"))
  }


  private def getScriptResult(context: ScriptSQLExecListener, sparkSession: SparkSession): String = {
    context.getLastSelectTable() match {
      case Some(table) =>
        val scriptJsonStringResult = sparkSession.sql(s"select * from $table limit 5000").toJSON.collect().mkString(",")
        "[" + scriptJsonStringResult + "]"
      case None => "[]"
    }
  }

  def main(args: Array[String]): Unit = {

  }

}

