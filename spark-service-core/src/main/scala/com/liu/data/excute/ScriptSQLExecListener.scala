package com.liu.data.excute

import java.util.concurrent.atomic.AtomicReference

import com.liu.data.auth.{AuthProcessListener, TableAuth}
import com.liu.data.util.{CaseChangingCharStream, MLSQLErrorStrategy, MLSQLSyntaxErrorListener}
import dsl.parser.{DSLSQLLexer, DSLSQLListener, DSLSQLParser}
import dsl.parser.DSLSQLParser.SqlContext
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.misc.Interval
import org.antlr.v4.runtime.tree.ParseTreeWalker
import org.apache.spark.sql.SparkSession
import parser.lisener.BaseParseListenerextends


object ScriptSQLExec {

  def parse(sql : String,  context: ScriptSQLExecListener): Unit =  {

    val authListener = new AuthProcessListener(context._sparkSession)
    _parse(sql, authListener)

    val tableAuth = Class.forName("com.liu.data.auth.DefaultConsoleClient")
      .newInstance().asInstanceOf[TableAuth]
    tableAuth.auth(authListener.tables().tables.toList)


    _parse(sql, context)
  }


  def _parse(input: String, listener: DSLSQLListener) = {
    val loadLexer = new DSLSQLLexer(new CaseChangingCharStream(input))
    val tokens = new CommonTokenStream(loadLexer)
    val parser = new DSLSQLParser(tokens)

    parser.setErrorHandler(new MLSQLErrorStrategy)
    parser.addErrorListener(new MLSQLSyntaxErrorListener())

    val stat = parser.statement()
    ParseTreeWalker.DEFAULT.walk(listener, stat)
  }

}








class ScriptSQLExecListener(val _sparkSession: SparkSession
                            //                  , val _defaultPathPrefix: String
                            //                  , val _allPathPrefix: Map[String, String]
                 ) extends BaseParseListenerextends{

  private val lastSelectTable = new AtomicReference[String]()
  var authProcessListner: Option[AuthProcessListener] = None


  def setLastSelectTable(table: String) = {
    lastSelectTable.set(table)
  }

  def getLastSelectTable() = {
    if (lastSelectTable.get() == null) None else Some(lastSelectTable.get())
  }



  /**
    * Exit a parse tree produced by {@link DSLSQLParser#sql}.
    *
    * @param ctx the parse tree
    */
  override def exitSql(ctx: SqlContext): Unit = {

    val PREFIX = ctx.getChild(0).getText.toLowerCase()

    PREFIX match {
      case "load" => "执行 load 语法"

      case "select" => "执行 select 语法"

      case "save" =>  "执行 save 语法"

      case "connect" => "执行 connect 语法"

      case "create" => "执行 create 语法"

      case "insert" => "执行 insert 语法"

      case "drop" => "执行 drop 语法"

      case "refresh" => "执行 refresh 语法"

      case "set" => "执行 set 语法"

      case "train" | "run" | "predict" => "执行 train | run | predict语法"

      case "register" => "执行 register 语法"
      case _ => throw new RuntimeException(s"Unknow statement:${ctx.getText}")
    }

  }
}
