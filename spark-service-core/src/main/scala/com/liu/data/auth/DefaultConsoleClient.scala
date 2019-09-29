package com.liu.data.auth

import java.util.concurrent.atomic.AtomicReference

import com.liu.data.parser.listener.{MLSQLTable, TableAuthResult}
import com.liu.data.utils.JacksonUtil

class DefaultConsoleClient extends TableAuth {
  override def auth(tables: List[MLSQLTable]): List[TableAuthResult] = {
    val owner = "test"
    println((s"auth ${owner}  want access tables: ${JacksonUtil.toJson(tables)}"))
    DefaultConsoleClient.set(tables)
    List(TableAuthResult(true, ""))
  }

}

// for testing only
object DefaultConsoleClient {
  private val value = new AtomicReference[List[MLSQLTable]]()

  def set(tables: List[MLSQLTable]) = {
    value.set(tables)
  }

  def get = {
    value.get()
  }
}
