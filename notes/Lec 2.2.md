### Lec 2.2 - Data Operations and Parallel Mapping

**Parallel processing of collections** when fulfill these requirements:

- properties of collections: ability to split, combine
- properties of operations: associativity, independence



**map**: apply function to each element

**fold**: combine elements with a given operation

```scala
List(1,3,8).fold(100)((s,x) => s + x) == 112
```

**scan**: combine folds of all list prefixes

```scala
List(1,3,8).scan(100)((s,x) => s + x) == List(100, 101, 104, 112)
```



**For parallel programming, we use Arrays & Trees instead of List.**

树与数组在并行处理中，各有优劣势，但并行优化的程度大致相同。