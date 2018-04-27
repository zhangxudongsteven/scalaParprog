### Lec 1.4 - Running Computations in Parallel

```scala
// 并行计算，但不便于通用化
val (sum1, sum2) = parallel(sumSegment(a, p, 0, m), 
                            sumSegment(a, p, m, a.length))

// 递归调用实现通用化
def pNormRec(a: Array[Int], p: Double): Int = 
	power(segmentRec(a, p, 0, a.length), 1/p)

// 归并方式实现
def segmentRec(a: Array[Int], p: Double, s: Int, t: Int) = {
  if (t - s < threshold)
    sumSegment(a, p, s, t)
    else {
        val m = s + (t - s)/2
        val (sum1, sum2) = parallel(segmentRec(a, p, s, m),
                                   segmentRec(a, p, m, t))
        sum1 + sum2
    }
}

```

```scala
// 通用化表达，必须是 call by name
def parallel[A, B](taskA: => A, taskB: => B): (A, B) = { ... }


```