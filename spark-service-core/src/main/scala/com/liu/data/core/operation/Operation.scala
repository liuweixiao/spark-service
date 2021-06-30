package com.liu.data.core.operation

import com.liu.data.parser.listener.TableAuthResult
import dsl.parser.DSLSQLParser.SqlContext

trait Operation {

  def auth(_ctx: Any): TableAuthResult

  def analyze(_ctx: Any)

  def parse(ctx: SqlContext): Unit


}
