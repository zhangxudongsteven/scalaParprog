### Lec 1.7 - How Fast are Parallel Programs?



#### Asymtotic analysis（复杂度计算）

并行计算理论复杂度：**O(log(t-s))**

对比 Sequencial 结果：O(t-s)



但实际复杂度基于并行化程度：

Work *W(e)*: number of steps *e* would take if there was no parallelism.

Depth *D(e)*: number of steps if we had unbounded parallelism.

因为线程有限，当有 P 个线程时，时间估计为：**D(e) + W(e) / P**



基于以上定义：

The call to parallel function segmentRec had:

- work *W*: *O(t - s)*
- depth *D*: *O(log(t - s))*

On a platform with *P* parallel threads the running time is:

**[b1]*log(t - s)* + [b2] + ([b3] * (t-s) + [b4]) / P**

大致等于：总复杂度 / P



在实际计算中：

Suppose that we have two parts of a sequential computation:

- part1 takes fraction *f* of the computation time (e.g. 40%)
- part2 takes the remaining *1 - f* fraction of time (e.g. 60%) and we can speed it up.

Amdahl's Law 给出公式：**1 / (f + (1 - f) / P)**

上式的优化极限：1 / f

实际上被优化的是并行化部分

