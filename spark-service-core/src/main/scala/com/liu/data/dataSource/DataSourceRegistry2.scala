package com.liu.data.dataSource

import com.liu.data.core.DataSource
import org.reflections.Reflections



class DataSourceRegistry2 {

  import scala.collection.JavaConverters._

  private val inputWithAnnotation = new Reflections(this.getClass.getPackage.getName)
    .getTypesAnnotatedWith(classOf[DataSource])

  private val sourceMapping = inputWithAnnotation.asScala.map{subclass =>
    val name = subclass.getAnnotation(classOf[DataSource]).name()
    (name, subclass)
  }.toMap[String, Class[_]]


  def fetch(name: String, option: Map[String, String] = Map()) = {
    DataSource
  }




}

