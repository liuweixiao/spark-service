package com.liu.data.auth

import com.liu.data.parser.listener.{MLSQLTable, MLSQLTableSet}
import dsl.parser.DSLSQLParser
import org.apache.spark.sql.SparkSession
import parser.lisener.BaseParseListenerextends

import scala.collection.mutable.ArrayBuffer

class AuthProcessListener(sparkSession: SparkSession) extends BaseParseListenerextends {


  private val _tables = MLSQLTableSet(ArrayBuffer[MLSQLTable]())

  def addTable(table: MLSQLTable) = {
    _tables.tables.asInstanceOf[ArrayBuffer[MLSQLTable]] += table
  }

  def withDBs = {
    _tables.tables.filter(f => f.db.isDefined)
  }

  def withoutDBs = {
    _tables.tables.filterNot(f => f.db.isDefined)
  }

  def tables() = _tables



  override def exitSql(ctx: DSLSQLParser.SqlContext): Unit = {
    ctx.getChild(0).getText.toLowerCase() match {
      case "load" =>
        new LoadAuth(this).auth(ctx)

      case "select" =>

      case "save" =>

      case "connect" =>

      case "create" =>
      case "insert" =>
      case "drop" =>

      case "refresh" =>

      case "set" =>

      case "train" | "run" | "predict" =>

      case "register" =>

      case _ =>

    }
  }
}
