package com.liu.data.caculator

import dsl.parser.{CalculatorBaseVisitor, CalculatorLexer, CalculatorParser}
import org.antlr.v4.runtime.{CharStreams, CommonTokenStream}


class MyCalculatorClass extends CalculatorBaseVisitor[AnyRef] {
  self =>

  override def visitFloat(ctx: CalculatorParser.FloatContext): AnyRef = ctx.getText.toFloat.asInstanceOf[AnyRef]

  override def visitAddOrSub(ctx: CalculatorParser.AddOrSubContext): AnyRef = {
    val expr0 = ctx.expr(0).accept(self).asInstanceOf[Float]
    val expr1 = ctx.expr(1).accept(self).asInstanceOf[Float]
    var rst: Float = 0
    if (ctx.getChild(1).getText == "+") rst = expr0 + expr1
    else if (ctx.getChild(1).getText == "-") rst = expr0 - expr1
    rst.asInstanceOf[AnyRef]
  }

  override def visitMultOrDiv(ctx: CalculatorParser.MultOrDivContext): AnyRef = {
    val expr0 = ctx.expr(0).accept(self).asInstanceOf[Float]
    val expr1 = ctx.expr(1).accept(self).asInstanceOf[Float]
    var rst: Float = 0
    if (ctx.getChild(1).getText == "*") rst = expr0 * expr1
    else if (ctx.getChild(1).getText == "/") rst = expr0 / expr1
    rst.asInstanceOf[AnyRef]
  }

  override def visitParenExpr(ctx: CalculatorParser.ParenExprContext): AnyRef = {
    val rst = ctx.expr.accept(self).asInstanceOf[Float]
    rst.asInstanceOf[AnyRef]
  }

}

object MyCalculator {
  def main(args: Array[String]): Unit = {
    val query: String = "3.1 * (6.3 - 4.3)"
    val stream = CharStreams.fromString(query)
    val lexer = new CalculatorLexer(stream)
    val tokens = new CommonTokenStream(lexer)
    val parser = new CalculatorParser(tokens)
    val visitor = new MyCalculatorClass()
    println(visitor.visit(parser.expr()))
  }

}


