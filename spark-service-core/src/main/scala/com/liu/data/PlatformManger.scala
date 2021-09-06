package com.liu.data

import com.liu.data.excute.{ScriptSQLExec, ScriptSQLExecListener}
import com.liu.data.utils.JacksonUtil
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.execution.ReplaceToJsonRelation

object PlatformManger {
  println("=================222222=====================")

  val sparkSession = getSparkSession()
  println("=================11111111=====================")


  def getSparkSession(): SparkSession = {
    SparkSession
      .builder()
      .master("local[2]")
      .enableHiveSupport()
      .withExtensions(e => e.injectPostHocResolutionRule(ReplaceToJsonRelation))
      //      .enableHiveSupport()
      .getOrCreate()
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

    println("======================================")
//    sparkSession.sql(
//      """
//        |select  'a' as a ,  20 as b
//        |""".stripMargin).createOrReplaceTempView("temp")
//    println(sparkSession.sql("select a  from temp where b > 18").explain())
    println(sparkSession.sql("select 1 as a ； ").count )
  }

}

