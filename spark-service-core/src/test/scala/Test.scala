
object Test {

  def is_same_time_pregnant(status: String,
                   babyDate: String,
                   array_pregnant_status_days: Seq[String]): Boolean = {


    import scala.util.Try
    import java.text.SimpleDateFormat
    val format = new SimpleDateFormat("yyyy-MM-dd")

    var is_same = false
    try {
      status match {
        case "备孕" => is_same = true
        case "有宝宝" => {
          val babyMonth: Long = ((System.currentTimeMillis() - format.parse(babyDate).getTime)/(1000 * 60 * 60 * 24))/30
          array_pregnant_status_days
            .map(data => Try(data.toInt).getOrElse(0))
            .foreach(days => {
              val month = days/30
              if (babyMonth   >= month -3 && babyMonth <= month  ) {
                is_same = true
                return is_same
              }
            })
        }
        case "怀孕" => {
          val pregnantWeek: Long = ((System.currentTimeMillis() - format.parse(babyDate).getTime)/(1000 * 60 * 60 * 24) + 280)/30
          array_pregnant_status_days
            .map(data => Try(data.toInt).getOrElse(0))
            .foreach(days => {
              val week = days/7
              if (pregnantWeek   >= week -12 && pregnantWeek <= week  ) {
                is_same = true
                return is_same

              }
            })
        }
        case _ =>
      }

    } catch {
      case e => println(e.getMessage)
    }
    is_same
  }


  def arrayIntersection(array1: Seq[String], array2: Seq[String]): Seq[String] = {
    if (array1 != null && array1.nonEmpty && array2 != null && array2.nonEmpty) {
      array1.intersect(array2)
    } else {
      Seq[String]()
    }

  }



  def array_flatten(array1: Seq[Seq[String]]): Seq[String] = {
    if (array1 != null && array1.nonEmpty){
      array1.flatten
    } else {
      Seq[String]()
    }
  }

  def is_any_array_first_contains_in_array_second(array1: Seq[String],
                                                   array2: Seq[String]): Boolean = {
    var flag = false
    if (array1 != null && array1.nonEmpty && array2 != null && array2.nonEmpty) {
      array1.foreach(data => {
        if (array2.contains(data)) {
          flag = true
        }
      })
    }

    flag
  }














  def main(args: Array[String]): Unit = {
    val x = 4

  }

}
