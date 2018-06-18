package reductions

import scala.annotation._
import org.scalameter._
import common._

object ParallelParenthesesBalancingRunner {

  @volatile var seqResult = false

  @volatile var parResult = false

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 40,
    Key.exec.maxWarmupRuns -> 80,
    Key.exec.benchRuns -> 120,
    Key.verbose -> true
  ) withWarmer(new Warmer.Default)

  def main(args: Array[String]): Unit = {
    val length = 100000000
    val chars = new Array[Char](length)
    val threshold = 10000
    val seqtime = standardConfig measure {
      seqResult = ParallelParenthesesBalancing.balance(chars)
    }
    println(s"sequential result = $seqResult")
    println(s"sequential balancing time: $seqtime ms")

    val fjtime = standardConfig measure {
      parResult = ParallelParenthesesBalancing.parBalance(chars, threshold)
    }
    println(s"parallel result = $parResult")
    println(s"parallel balancing time: $fjtime ms")
    println(s"speedup: ${seqtime / fjtime}")
  }
}

object ParallelParenthesesBalancing {

  /** Returns `true` iff the parentheses in the input `chars` are balanced.
   */
  def balance(chars: Array[Char]): Boolean = {
    var index = 0
    var am = 0
    while (index < chars.length) {
      if (am < 0)
        return false
      else {
        if (chars(index) == '(') am = am + 1
        else if (chars(index) == ')') am = am - 1
      }
      index = index + 1
    }
    am == 0
  }

  /** Returns `true` iff the parentheses in the input `chars` are balanced.
   */
  def parBalance(chars: Array[Char], threshold: Int): Boolean = {

    def traverse(idx: Int, until: Int): (Int, Int) = {
      var l = 0
      var r = 0
      var index = idx
      while (index < until) {
        if (chars(index) == '(') {
          r = r + 1
        } else if (chars(index) == ')') {
          if (r > 0)
            r = r - 1
          else
            l = l + 1
        }
        index = index + 1
      }
      (l, r)
    }

    def reduce(from: Int, until: Int): (Int, Int) = {
      if (until - from <= threshold)
        traverse(from, until)
      else {
        val middle = from + (until - from) / 2
        val (l, r) = parallel(
          reduce(from, middle),
          reduce(middle, until)
        )
        var t = l._2 - r._1
        if (t > 0)
          (l._1, t + r._2)
        else
          (l._1 - t, r._2)
      }
    }

    reduce(0, chars.length) == (0, 0)
  }


  // For those who want more:
  // Prove that your reduction operator is associative!

}
