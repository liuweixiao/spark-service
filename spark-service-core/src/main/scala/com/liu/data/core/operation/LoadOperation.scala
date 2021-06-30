package com.liu.data.core.operation

import com.liu.data.parser.listener.TableAuthResult
import dsl.parser.DSLSQLParser

class LoadOperation extends  Operation {
  override def auth(_ctx: Any): TableAuthResult = ???

  override def analyze(_ctx: Any): Unit = ???

  override def parse(ctx: DSLSQLParser.SqlContext): Unit = ???
}
