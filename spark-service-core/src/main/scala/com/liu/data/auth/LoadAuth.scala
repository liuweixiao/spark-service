package com.liu.data.auth
import java.io.{StringReader, StringWriter}

import com.liu.data.dataSource.{DataAuthConfig, DataSourceRegistry}
import com.liu.data.parser.listener.{MLSQLTable, OperateType, TableAuthResult, TableType}
import dsl.parser.DSLSQLParser._
import org.apache.velocity.VelocityContext
import org.apache.velocity.app.Velocity
import org.joda.time.DateTime

class LoadAuth(authProcessListener: AuthProcessListener) extends MLSQLAuth {
  override def auth(_ctx: Any): TableAuthResult = {
    val ctx = _ctx.asInstanceOf[SqlContext]
    var format = ""
    var option = Map[String, String]()
    var path = ""
    var tableName = ""
    (0 to ctx.getChildCount() - 1).foreach { tokenIndex =>
      ctx.getChild(tokenIndex) match {
        case s: FormatContext =>
          format = s.getText
        case s: ExpressionContext =>
          option += (cleanStr(s.qualifiedName().getText) -> evaluate(getStrOrBlockStr(s)))
        case s: BooleanExpressionContext =>
          option += (cleanStr(s.expression().qualifiedName().getText) -> evaluate(getStrOrBlockStr(s.expression())))
        case s: PathContext =>
          path = evaluate(s.getText)

        case s: TableNameContext =>
          tableName = evaluate(s.getText)
        case _ =>
      }
    }

    val tableType = TableType.from(format) match {
      case Some(tt) => tt
      case None =>
        println(s"format ${format} is not supported yet by auth.")
        TableType.UNKNOW

    }

    val mLSQLTables = DataSourceRegistry.fetch(format, option).map { datasource =>
    {
      val dataAuthConfig = DataAuthConfig(cleanStr(path), option)
      val sourceInfo = datasource.sourceInfo(dataAuthConfig)

      List(MLSQLTable(Some(sourceInfo.db), Some(sourceInfo.table), OperateType.LOAD, Some(format), tableType))

    }}

    mLSQLTables.foreach { tables =>
      tables.foreach(authProcessListener.addTable(_))
    }

    authProcessListener.addTable(MLSQLTable(None, Some(cleanStr(tableName)), OperateType.LOAD, None, TableType.TEMP))
    TableAuthResult.empty()
  }

  def cleanStr(str: String) = {
    if (str.startsWith("`") || str.startsWith("\"") || (str.startsWith("'") && !str.startsWith("'''")))
      str.substring(1, str.length - 1)
    else str
  }


  def getStrOrBlockStr(ec: ExpressionContext) = {
    if (ec.STRING() == null || ec.STRING().getText.isEmpty) {
      cleanBlockStr(ec.BLOCK_STRING().getText)
    } else {
      cleanStr(ec.STRING().getText)
    }
  }

  def cleanBlockStr(str: String) = {
    if (str.startsWith("'''") && str.endsWith("'''"))
      str.substring(3, str.length - 3)
    else str
  }

  def evaluate(value: String) = {
    merge(value, Map[String, String]())
  }

  def merge(sql: String, root: Map[String, String]) = {

    val dformat = "yyyy-MM-dd"
    //2018-03-24
    val predified_variables = Map[String, String](
      "yesterday" -> DateTime.now().minusDays(1).toString(dformat),
      "today" -> DateTime.now().toString(dformat),
      "tomorrow" -> DateTime.now().plusDays(1).toString(dformat),
      "theDayBeforeYesterday" -> DateTime.now().minusDays(2).toString(dformat)
    )
    val newRoot = Map("date" -> new DateTime()) ++ predified_variables ++ root

   render(sql, newRoot)
  }

  def render(templateStr: String, root: Map[String, AnyRef]) = {
    val context: VelocityContext = new VelocityContext
    root.map { f =>
      context.put(f._1, f._2)
    }
    val w: StringWriter = new StringWriter
    Velocity.evaluate(context, w, "", new StringReader(templateStr))
    w.toString
  }

}
