### Lec 2.3 - Parallel Fold (Reduce) Operation



**map**: apply function to each element

**fold**: combine elements with a given operation



```scala
List(1,3,8).foldLeft(100)((s,x) => s - x) == ((100 - 1) - 3) - 8 == 88
List(1,3,8).foldRight(100)((s,x) => s - x) == 1 - (3 - (8 - 100)) == -94
List(1,3,8).reduceLeft((s,x) => s - x) == (1 - 3) - 8 == -10
List(1,3,8).reduceRight((s,x) => s - x) == 1 - (3 - 8) == 6
```



减法并不是 **associative** 的连接计算符。To enable parallel operations, we look at associative operations.



**Associative operation 定义：**

符合计算结合律：

x * (y * z) === (x * y) * z

x * y * z * w === (x * y) * (z * w)

如：加法、乘法、字符串连接等



```scala
sealed abstract class Tree[A]
case class leaf[A](value: A) extends Tree[A]
case class Node[A](left: Tree[A], right: Tree[A]) extends Tree[A]

def reduce[A](t: Tree[A], f: (A, A) => A): A = t match {
    case Leaf(v) => v
    case Node(l, r) => f(reduce[A](l, f), reduce[A](r, f))
}

// depth complexity = n
def reducePara[A](t: Tree[A], f: (A, A) => A): A = t match {
    case Leaf(v) => v
    case Node(l, r) => {
        val (lV, rV) = parallel(reduce[A](l, f), reduce[A](r, f))
        f(lV, rV)
    }
}


// 例子：
toList(t) == reduce(map(tree, List(_)), _ ++ _)



```

用计算式定义 Consequence（Scala）：

if 

​	*f: (A,A) => A* is associative

​	*t1:Tree[A]* and *t2:Tree[A]* and *toList(t1) == toList(t2)*     //// 前序遍历相等

then: 

​	*reduce(t1, f) == reduce(t2, f)*



**对于数组/向量来说，最好构建平衡树式计算（树高较低）。对于已经划分的较小的序列，直接顺序计算。**

```scala
def reduceSeg[A](inp: Array[A], left: Int, right: Int, f: (A,A) => A): A = {
    if (right - left < threshold) {
        var res = inp(left)
        var i = left + 1
        while (i < right) {
            res = f(res, inp(i))
            i = i + 1
        }
        f(a1, a2)
    }
}
def reduce[A](inp: Array[A], f: (A,A) => A): A = 
	reduceSeg(inp, 0, inp.length, f)
```



