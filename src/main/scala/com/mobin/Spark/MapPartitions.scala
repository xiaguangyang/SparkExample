package com.mobin.Spark

import org.apache.spark.{SparkContext, SparkConf}

/**
  * Created by hadoop on 4/7/16.
  */
object MapPartitions {
 // 输出有女性的名字：
  def partitionsFun(iter : Iterator[(String,String)]) : Iterator[String] = {
    var woman = List[String]()
    while (iter.hasNext){
      val next = iter.next()
      next match {
        case (_,"female") => woman = next._1 :: woman
        //case (_,"female") => woman =  next._1.toList .:: (woman)  错误写法
        case _ =>
      }
    }
    return  woman.iterator
  }


  def main(args: Array[String]) {
    val conf = new SparkConf().setMaster("local").setAppName("flatmap")
    val sc = new SparkContext(conf)
    val l = List(("kpop","female"),("lucy","female"),("mobin","male"))
    val rdd = sc.parallelize(l,2)
   // val mp = rdd.mapPartitions(x => x.filter(_._2 == "female")).map(x => x._1)
    val mp = rdd.mapPartitions(partitionsFun,true)
    println(mp.count)
    mp.collect.foreach(x => (print(x +" "))) //将分区中的元素转换成Aarray再输出
  }
}
