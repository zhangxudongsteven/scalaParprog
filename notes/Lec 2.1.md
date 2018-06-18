### Lec 2.1 - Parallel Sorting

归并排序：

```scala
def sort(from: Int, until: Int, depth: Int): Unit = {
    if (depth == masDepth) {
        quickSort(xs, from, until - from)
    } else {
        val mid = (from + until) / 2
        parallel(sort(mid, until, depth + 1), sort(from, mid, depth + 1))

        // 交叉做临时存储，节省计算空间
        val flip = (maxDepth - depth) % 2 == 0
        val src = if (flip) ys else xs
        val dst = if (flip) xs else ys
        
        // 该环节也可以在一定程度上并行
        merge(src, dst, from, mid, until)
    }
}
// sort(0, xs.length, 0)

def parMergeSort(xs: Array[Int], maxDepth: Int): Unit = {
    
}
```

在执行效率上，由于可以高度并行化，其执行效率可提升数倍（实测为 2 倍余）