package org.apache.spark.sql.execution

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.catalyst.expressions.{Literal, Multiply}
import org.apache.spark.sql.execution.datasources.jdbc.{JDBCRelation, JdbcRelationProvider}
import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan
import org.apache.spark.sql.catalyst.rules.Rule
import org.apache.spark.sql.execution.datasources.csv.CSVFileFormat
import org.apache.spark.sql.execution.datasources.json.JsonFileFormat
import org.apache.spark.sql.execution.datasources.{FileFormat, HadoopFsRelation, LogicalRelation}
import org.apache.spark.sql.sources.BaseRelation

case class ReplaceToJsonRelation(sparkSession: SparkSession) extends Rule[LogicalPlan] {

  override def apply(plan: LogicalPlan): LogicalPlan = {
    plan resolveOperators {
      case lr@LogicalRelation(jr@JDBCRelation(_, _, jdbcOptions), output, catalogTable, isStreaming) => {

        lr
      }
      case hadoopLR@LogicalRelation(jsonR@HadoopFsRelation(location, partitionSchema, dataSchema, bucketSpec, fileFormat, hadoopOptions),  output, catalogTable, isStreaming) => {
        if (fileFormat.isInstanceOf[JsonFileFormat]) {

          val ht: BaseRelation = HadoopFsRelation(location, partitionSchema, dataSchema, bucketSpec, new CSVFileFormat(), hadoopOptions)(sparkSession)

          LogicalRelation(ht, output, catalogTable, isStreaming)
        } else {
          hadoopLR
        }


      }

    }
  }

}
