package spark.optimize

import com.liu.data.PlatformManger
import org.apache.spark.sql.SaveMode

object TestOptimize {
  def main(args: Array[String]): Unit = {
    val sql =
      s"""
        | select "a"  as a
        | union all
        | select "a"  as  a
        | union all
        | select "b"  as  a
        | union all
        | select "a"  as  a
        | union all
        | select "a"  as  a
        | union all
        | select "c"  as  a
        | union all
        | select "a"  as  a
        | union all
        | select "d"  as  a
        | union all
        | select "a"  as  a
        | union all
        | select "e"  as  a
        | union all
        | select "a"  as  a
        |
        |
      """.stripMargin
    val df = PlatformManger.sparkSession.sql(sql)
    df.write.mode(SaveMode.Overwrite).json("spark/data/tmp.json")
    df.write.mode(SaveMode.Overwrite).csv("spark/data/tmp.csv")

    PlatformManger.sparkSession.read.json("")

  }

}
