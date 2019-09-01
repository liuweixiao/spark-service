/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package parser.lisener

import dsl.parser.DSLSQLListener
import dsl.parser.DSLSQLParser._
import org.antlr.v4.runtime._
import org.antlr.v4.runtime.tree.{ErrorNode, TerminalNode}

/**
  * Created by allwefantasy on 11/9/2018.
  */
abstract class BaseParseListenerextends extends DSLSQLListener {
  /**
    * Enter a parse tree produced by {@link DSLSQLParser#statement}.
    *
    * @param ctx the parse tree
    */
  override def enterStatement(ctx: StatementContext): Unit = {}

  /**
    * Exit a parse tree produced by {@link DSLSQLParser#statement}.
    *
    * @param ctx the parse tree
    */
  override def exitStatement(ctx: StatementContext): Unit = {}

  /**
    * Enter a parse tree produced by {@link DSLSQLParser#sql}.
    *
    * @param ctx the parse tree
    */
  override def enterSql(ctx: SqlContext): Unit = {}


  /**
    * Enter a parse tree produced by {@link DSLSQLParser#as}.
    *
    * @param ctx the parse tree
    */
  override def enterAs(ctx: AsContext): Unit = {}

  /**
    * Exit a parse tree produced by {@link DSLSQLParser#as}.
    *
    * @param ctx the parse tree
    */
  override def exitAs(ctx: AsContext): Unit = {}

  /**
    * Enter a parse tree produced by {@link DSLSQLParser#into}.
    *
    * @param ctx the parse tree
    */
  override def enterInto(ctx: IntoContext): Unit = {}

  /**
    * Exit a parse tree produced by {@link DSLSQLParser#into}.
    *
    * @param ctx the parse tree
    */
  override def exitInto(ctx: IntoContext): Unit = {}

  /**
    * Enter a parse tree produced by {@link DSLSQLParser#saveMode}.
    *
    * @param ctx the parse tree
    */
  override def enterSaveMode(ctx: SaveModeContext): Unit = {}

  /**
    * Exit a parse tree produced by {@link DSLSQLParser#saveMode}.
    *
    * @param ctx the parse tree
    */
  override def exitSaveMode(ctx: SaveModeContext): Unit = {}

  /**
    * Enter a parse tree produced by {@link DSLSQLParser#where}.
    *
    * @param ctx the parse tree
    */
  override def enterWhere(ctx: WhereContext): Unit = {}

  /**
    * Exit a parse tree produced by {@link DSLSQLParser#where}.
    *
    * @param ctx the parse tree
    */
  override def exitWhere(ctx: WhereContext): Unit = {}

  /**
    * Enter a parse tree produced by {@link DSLSQLParser#whereExpressions}.
    *
    * @param ctx the parse tree
    */
  override def enterWhereExpressions(ctx: WhereExpressionsContext): Unit = {}

  /**
    * Exit a parse tree produced by {@link DSLSQLParser#whereExpressions}.
    *
    * @param ctx the parse tree
    */
  override def exitWhereExpressions(ctx: WhereExpressionsContext): Unit = {}

  /**
    * Enter a parse tree produced by {@link DSLSQLParser#overwrite}.
    *
    * @param ctx the parse tree
    */
  override def enterOverwrite(ctx: OverwriteContext): Unit = {}

  /**
    * Exit a parse tree produced by {@link DSLSQLParser#overwrite}.
    *
    * @param ctx the parse tree
    */
  override def exitOverwrite(ctx: OverwriteContext): Unit = {}

  /**
    * Enter a parse tree produced by {@link DSLSQLParser#append}.
    *
    * @param ctx the parse tree
    */
  override def enterAppend(ctx: AppendContext): Unit = {}

  /**
    * Exit a parse tree produced by {@link DSLSQLParser#append}.
    *
    * @param ctx the parse tree
    */
  override def exitAppend(ctx: AppendContext): Unit = {}

  /**
    * Enter a parse tree produced by {@link DSLSQLParser#errorIfExists}.
    *
    * @param ctx the parse tree
    */
  override def enterErrorIfExists(ctx: ErrorIfExistsContext): Unit = {}

  /**
    * Exit a parse tree produced by {@link DSLSQLParser#errorIfExists}.
    *
    * @param ctx the parse tree
    */
  override def exitErrorIfExists(ctx: ErrorIfExistsContext): Unit = {}

  /**
    * Enter a parse tree produced by {@link DSLSQLParser#ignore}.
    *
    * @param ctx the parse tree
    */
  override def enterIgnore(ctx: IgnoreContext): Unit = {}

  /**
    * Exit a parse tree produced by {@link DSLSQLParser#ignore}.
    *
    * @param ctx the parse tree
    */
  override def exitIgnore(ctx: IgnoreContext): Unit = {}

  /**
    * Enter a parse tree produced by {@link DSLSQLParser#booleanExpression}.
    *
    * @param ctx the parse tree
    */
  override def enterBooleanExpression(ctx: BooleanExpressionContext): Unit = {}

  /**
    * Exit a parse tree produced by {@link DSLSQLParser#booleanExpression}.
    *
    * @param ctx the parse tree
    */
  override def exitBooleanExpression(ctx: BooleanExpressionContext): Unit = {}

  /**
    * Enter a parse tree produced by {@link DSLSQLParser#expression}.
    *
    * @param ctx the parse tree
    */
  override def enterExpression(ctx: ExpressionContext): Unit = {}

  /**
    * Exit a parse tree produced by {@link DSLSQLParser#expression}.
    *
    * @param ctx the parse tree
    */
  override def exitExpression(ctx: ExpressionContext): Unit = {}

  /**
    * Enter a parse tree produced by {@link DSLSQLParser#ender}.
    *
    * @param ctx the parse tree
    */
  override def enterEnder(ctx: EnderContext): Unit = {}

  /**
    * Exit a parse tree produced by {@link DSLSQLParser#ender}.
    *
    * @param ctx the parse tree
    */
  override def exitEnder(ctx: EnderContext): Unit = {}

  /**
    * Enter a parse tree produced by {@link DSLSQLParser#format}.
    *
    * @param ctx the parse tree
    */
  override def enterFormat(ctx: FormatContext): Unit = {}

  /**
    * Exit a parse tree produced by {@link DSLSQLParser#format}.
    *
    * @param ctx the parse tree
    */
  override def exitFormat(ctx: FormatContext): Unit = {}

  /**
    * Enter a parse tree produced by {@link DSLSQLParser#path}.
    *
    * @param ctx the parse tree
    */
  override def enterPath(ctx: PathContext): Unit = {}

  /**
    * Exit a parse tree produced by {@link DSLSQLParser#path}.
    *
    * @param ctx the parse tree
    */
  override def exitPath(ctx: PathContext): Unit = {}

  /**
    * Enter a parse tree produced by {@link DSLSQLParser#commandValue}.
    *
    * @param ctx the parse tree
    */
  override def enterCommandValue(ctx: CommandValueContext): Unit = {}

  /**
    * Exit a parse tree produced by {@link DSLSQLParser#commandValue}.
    *
    * @param ctx the parse tree
    */
  override def exitCommandValue(ctx: CommandValueContext): Unit = {}

  /**
    * Enter a parse tree produced by {@link DSLSQLParser#rawCommandValue}.
    *
    * @param ctx the parse tree
    */
  override def enterRawCommandValue(ctx: RawCommandValueContext): Unit = {}

  /**
    * Exit a parse tree produced by {@link DSLSQLParser#rawCommandValue}.
    *
    * @param ctx the parse tree
    */
  override def exitRawCommandValue(ctx: RawCommandValueContext): Unit = {}

  /**
    * Enter a parse tree produced by {@link DSLSQLParser#setValue}.
    *
    * @param ctx the parse tree
    */
  override def enterSetValue(ctx: SetValueContext): Unit = {}

  /**
    * Exit a parse tree produced by {@link DSLSQLParser#setValue}.
    *
    * @param ctx the parse tree
    */
  override def exitSetValue(ctx: SetValueContext): Unit = {}

  /**
    * Enter a parse tree produced by {@link DSLSQLParser#setKey}.
    *
    * @param ctx the parse tree
    */
  override def enterSetKey(ctx: SetKeyContext): Unit = {}

  /**
    * Exit a parse tree produced by {@link DSLSQLParser#setKey}.
    *
    * @param ctx the parse tree
    */
  override def exitSetKey(ctx: SetKeyContext): Unit = {}

  /**
    * Enter a parse tree produced by {@link DSLSQLParser#db}.
    *
    * @param ctx the parse tree
    */
  override def enterDb(ctx: DbContext): Unit = {}

  /**
    * Exit a parse tree produced by {@link DSLSQLParser#db}.
    *
    * @param ctx the parse tree
    */
  override def exitDb(ctx: DbContext): Unit = {}

  /**
    * Enter a parse tree produced by {@link DSLSQLParser#asTableName}.
    *
    * @param ctx the parse tree
    */
  override def enterAsTableName(ctx: AsTableNameContext): Unit = {}

  /**
    * Exit a parse tree produced by {@link DSLSQLParser#asTableName}.
    *
    * @param ctx the parse tree
    */
  override def exitAsTableName(ctx: AsTableNameContext): Unit = {}

  /**
    * Enter a parse tree produced by {@link DSLSQLParser#tableName}.
    *
    * @param ctx the parse tree
    */
  override def enterTableName(ctx: TableNameContext): Unit = {}

  /**
    * Exit a parse tree produced by {@link DSLSQLParser#tableName}.
    *
    * @param ctx the parse tree
    */
  override def exitTableName(ctx: TableNameContext): Unit = {}

  /**
    * Enter a parse tree produced by {@link DSLSQLParser#functionName}.
    *
    * @param ctx the parse tree
    */
  override def enterFunctionName(ctx: FunctionNameContext): Unit = {}

  /**
    * Exit a parse tree produced by {@link DSLSQLParser#functionName}.
    *
    * @param ctx the parse tree
    */
  override def exitFunctionName(ctx: FunctionNameContext): Unit = {}

  /**
    * Enter a parse tree produced by {@link DSLSQLParser#colGroup}.
    *
    * @param ctx the parse tree
    */
  override def enterColGroup(ctx: ColGroupContext): Unit = {}

  /**
    * Exit a parse tree produced by {@link DSLSQLParser#colGroup}.
    *
    * @param ctx the parse tree
    */
  override def exitColGroup(ctx: ColGroupContext): Unit = {}

  /**
    * Enter a parse tree produced by {@link DSLSQLParser#col}.
    *
    * @param ctx the parse tree
    */
  override def enterCol(ctx: ColContext): Unit = {}

  /**
    * Exit a parse tree produced by {@link DSLSQLParser#col}.
    *
    * @param ctx the parse tree
    */
  override def exitCol(ctx: ColContext): Unit = {}

  /**
    * Enter a parse tree produced by {@link DSLSQLParser#qualifiedName}.
    *
    * @param ctx the parse tree
    */
  override def enterQualifiedName(ctx: QualifiedNameContext): Unit = {}

  /**
    * Exit a parse tree produced by {@link DSLSQLParser#qualifiedName}.
    *
    * @param ctx the parse tree
    */
  override def exitQualifiedName(ctx: QualifiedNameContext): Unit = {}

  /**
    * Enter a parse tree produced by {@link DSLSQLParser#identifier}.
    *
    * @param ctx the parse tree
    */
  override def enterIdentifier(ctx: IdentifierContext): Unit = {}

  /**
    * Exit a parse tree produced by {@link DSLSQLParser#identifier}.
    *
    * @param ctx the parse tree
    */
  override def exitIdentifier(ctx: IdentifierContext): Unit = {}

  /**
    * Enter a parse tree produced by {@link DSLSQLParser#strictIdentifier}.
    *
    * @param ctx the parse tree
    */
  override def enterStrictIdentifier(ctx: StrictIdentifierContext): Unit = {}

  /**
    * Exit a parse tree produced by {@link DSLSQLParser#strictIdentifier}.
    *
    * @param ctx the parse tree
    */
  override def exitStrictIdentifier(ctx: StrictIdentifierContext): Unit = {}

  /**
    * Enter a parse tree produced by {@link DSLSQLParser#quotedIdentifier}.
    *
    * @param ctx the parse tree
    */
  override def enterQuotedIdentifier(ctx: QuotedIdentifierContext): Unit = {}

  /**
    * Exit a parse tree produced by {@link DSLSQLParser#quotedIdentifier}.
    *
    * @param ctx the parse tree
    */
  override def exitQuotedIdentifier(ctx: QuotedIdentifierContext): Unit = {}

  override def visitTerminal(terminalNode: TerminalNode): Unit = {}

  override def visitErrorNode(errorNode: ErrorNode): Unit = {}

  override def enterEveryRule(parserRuleContext: ParserRuleContext): Unit = {}

  override def exitEveryRule(parserRuleContext: ParserRuleContext): Unit = {}
}
