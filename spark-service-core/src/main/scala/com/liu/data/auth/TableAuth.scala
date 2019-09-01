package com.liu.data.auth

import com.liu.data.parser.listener.{MLSQLTable, TableAuthResult}

trait TableAuth {
  def auth(table: List[MLSQLTable]): List[TableAuthResult]
}
