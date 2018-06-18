### Lec 2.6 - Parallel Scan (Prefix Sum) Operation

**scanLeft**: list of the folds of all list prefixes

```scala
List(1, 3, 8).scanLeft(100)((s, x) => s + x) == List(100, 101, 104, 112)
// === 
List(1, 3, 8).scanLeft(100)(_ + _) == List(100, 101, 104, 112)

List(1, 3, 8).scanRight(100)(_ + _) == List(112, 111, 108, 100)
```



Even this function can be transformed into a parallel algorithm.

Use **reduceSeg** and **mapSeg** to implement **scanLeft**.

```scala
// Solution A: for each element in the array, use reduceSeg to get the result.
def scanLeft[A](inp: Array[A], a0: A, f: (A,A) => A, out: Array[A]) = {
    val fi = { (i:Int,v:A) => reduceSeg1(inp, 0, i, a0, f) }
    mapSeg(inp, 0, inp.length, fi, out)
    val last = inp.length - 1
    out(last + 1) = f(out(last), inp(last))
}
```



我们需要将部分数据预先计算并保存，因此需要两次并行遍历：

存储与计算数据的数据结构如下：

```scala
sealed abstract class TreeResA[A] {val res: A}
case class Leaf[A](from: Int, to: Int,
                   override val res: A) extends TreeResA[A]
case class Node[A](l: TreeResA[A],
                   override val res: A,
                   r: TreeResA[A]) extends TreeResA[A]
```

scan 的实现如下：

```scala
def scanLeft[A](inp: Array[A],
                a0: A, f: (A, A) => A,
                out: Array[A]) = {
    val t = upsweep(inp, 0, inp.length, f)
    downsweep(inp, a0, f, t, out)
    out(0) = a0 // prepends a0
}

def upsweep[A](inp: Array[A], from: Int, to: Int,
               f: (A, A) => A): TreeResA[A] = {
    if (to - from < threshold)
    	Leaf(from, to, reduceSeg1(inp, from + 1, to, inp(from), f))
    else {
        val mid = from + (to - from) / 2
        val (tL, tR) = parallel(
            upsweep(inp, from, mid, f),
            upsweep(inp, mid, to, f)
        )
        Node(tL, f(tL.res, tR.res), tR)
    }
}

def downsweep[A](inp: Array[A],
                 a0: A, f: (A, A) => A,
                 t: TreeResA[A],
                 out: Array[A]): Unit = t match {
    case Leaf(from, to, res) =>
    	scanLeftSeg(inp, from, to, a0, f, out)
    case Node(l, _, r) => {
        val (_,_) = parallel(
        	downsweep(inp, a0, f, l, out),
        	downsweep(inp, f(a0, l.res), f, r, out)
        )
    }
}

def scanLeftSeg(inp: Array[A], left: Int, right: Int,
                a0: A, f: (A, A) => A,
                out: Array[A]) = {
    if (left < right) {
        var i = left
        var a = a0
        while (i < right) {
            a = f(a, inp(i))
            i = i + 1
            out(i) = a
        }
    }
}
```























