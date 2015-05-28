package org.apache.spark.streamdm.util

import scala.math.{ min, max, log }

object Util {

  def argmax(array: Array[Double]): Double = array.zipWithIndex.maxBy(_._1)._2

  def log2(v: Double): Double = log(v) / log(2)

  def transpose(input: Array[Array[Double]]): Array[Array[Double]] = {
    val output: Array[Array[Double]] = Array.fill(input(0).length)(new Array[Double](input.length))
    input.zipWithIndex.map {
      row =>
        row._1.zipWithIndex.map {
          col => output(col._2)(row._2) = input(row._2)(col._2)
        }
    }
    output
  }
  def splitTranspose(input: Array[Array[Double]], fIndex: Int): Array[Array[Double]] = {
    val output: Array[Array[Double]] = Array.fill(2)(new Array[Double](input.length))
    input.zipWithIndex.map {
      row =>
        row._1.zipWithIndex.map {
          col =>
            if (col._2 == fIndex) output(0)(row._2) = input(row._2)(col._2)
            else output(1)(row._2) += input(row._2)(col._2)
        }
    }
    output
  }

  def mergeArray(array1: Array[Double], array2: Array[Double]): Array[Double] = {
    val merge = new Array[Double](min(array1.length, array2.length))
    for (i <- 0 until merge.length)
      merge(i) = array1(i) + array2(i)
    merge
  }

  def matrixtoString(post: Array[Array[Double]]): String = {
    val sb = new StringBuffer("\n")
    for (i <- 0 until post.length) {
      for (j <- 0 until post(i).length) {
        sb.append(post(i)(j) + "\t")
      }
      sb.append("\n")
    }
    sb.substring(0, sb.length() - 1)
  }

  def arraytoString(pre: Array[Double]): String = {
    val sb = new StringBuffer("\n")
    for (i <- 0 until pre.length) {
      sb.append(pre(i) + "\t")
    }
    sb.substring(0, sb.length() - 1)
  }

  def main(args: Array[String]) = {
    val input: Array[Array[Double]] = Array(Array(1, 2, 3), Array(4, 5, 6))
    val output = Util.splitTranspose(input, 1)
    println(output.length == 2)
    println(output(0).length == 2)
    println(output(0)(0) == 2)
    println(output(0)(1) == 5)
    println(output(1)(0) == 4)
    println(output(1)(1) == 10)
  }
}