package com.liu.data.auth

import com.liu.data.parser.listener.TableAuthResult

trait MLSQLAuth {

  def isForbidden = false

  // ctx should be SQLContext; you should include
  // streamingpro-dsl or streamingpro-dsl-legcy which depends
  // your spark version
  def auth(_ctx: Any): TableAuthResult
}
