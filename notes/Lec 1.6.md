### Lec 1.6 - First-Class Tasks

```scala
val t1 = task(e1)
val t2 = task(e2)
val v1 = t1.join
val v2 = t2.join

// 定义
def task(c: => A): Task[A]

trait Task[A] {
    def join: A
}

// 基于上述定义，我们可以有如下表达
val t1 = task {sumSegment(a, p, 0, mid1)}
val t2 = task {sumSegment(a, p, mid1, mid2)}
val t3 = task {sumSegment(a, p, mid2, mid3)}
val t4 = task {sumSegment(a, p, mid3, a.length)}
power(t1 + t2 + t3 + t4, 1/p)
// equal to 
// power(t1.join + t2.join + t3.join + t4.join, 1/p)
// 因为隐式表达
implicit def getJoin[T](x:Task[T]): T = x.join

```

我们可以统一 parallel 与 task，通过用 task 来表达 parallel：

```scala
def parallel[A, B](cA: => A, cB: => B): (A, B) = {
    val tB: Task[B] = task{ cB }
    // 直接计算 cA 的值
    // 在我们调用 tA 时，它将被计算
    val tA: A = cA
    (tA, tB.join)
}
def parallelWrong[A, B](cA: => A, cB: => B): (A, B) = {
	// 调用 join 会立刻触发计算，完成后才会进入下一行指令
    val tB: Task[B] = (task{ cB }).join
    val tA: A = cA
    (tA, tB)
}
```

