### Lec 2.4 / 2.5 - Associativity (结合律)



Operation *f: (A,A) => A* is **associative** if for every *x,y,z*:

​	*f(x, f(y, z)) = f(f(x, y), z)*

Consequence:

- two expressions with same list of operands connected with *x*, but different parentheses evaluate to the same result
- reduce on any tree with this list of operands gives the same result



Associativity != Commutativity （结合律不等于交换律）

For the correctness of reduce, we need (just) associativity.



Making an operation commutative is easy:

```scala
def f(x: A, y: A) = if (less(y,x)) g(y,x) else g(x,y)
```



#### Construct Associative Operations

构建方式一：

Suppose *f1: (X,X) => X* and *f2: (Y,Y) => Y* are associative

Then *f: ((X, Y), (X, Y)) => (f1(X, X), f2(Y, Y))*   defined by    *f((x1, x2), (y1, y2)) = (f1(x1, y1), f2(x2, y2))*

```
[x,y
 x,y
 x,y]
```

Example：average

```scala
// two reductions
val sum = reduce(collection, _ + _)
val length = reduce(map(collection, (x: Int) => 1), _ + _)
sum / length

// one reduction
f((x1,y2), (x2,y2)) = (x1 + x2, y1 + y2)
val (sum, length) = reduce(map(collection, (x: Int) => (x, 1)), f)
sum / length
```

构建方式二：**Associativity through symmetry and commutativity**

*E(x, y, z) = f(f(x, y), z)*

if *E(x, y, z) = E(y, z, x)*

then *f* is **associative**.

举例：符合特定条件的集合运算，详见视频

