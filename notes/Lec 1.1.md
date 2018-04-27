### Lec 1.1 - Introduction to Parallel Computing

*Parallel computing* is a type of computation in which many calculations are performed at the same time.

**Basic principle**: computation can be divided into smaller subproblems, each of which can be solved simultaneously.

**Assumption**: we have parallel hardware at our disposal, which is capable of executing these computations in parallel.



parallel programming is much harder than sequential programming.

- Separating squential computations into parallel subcomputations can be challenging, or even impossible.
- Ensuring program correctness is more difficult, due to new types of errors.



#### Parallel Programming（并行编程） vs. Concurrent Programming（并发编程）

They are closed to each other.

Parallel program - uses parallel hardware to execute compuation more quickly. Efficiency is its main concern. （提升计算速度）

Concurrent program - may or may not execute multiple executions at the same time. Improves modularity, responsiveness or maintainability.（处理高并发量）



#### Parallelism manifests itself at different granularity levels（粒度）.

- bit-level parallelism - processing multiple bits of data in parallel. (可逐级拆分的模式，如归并排序)
- instruction-level parallelism - executing different instructions from the same instruction stream in parallel.（树形依赖）
- task-level parallelism - executing separate instruction streams in parallel. （**本课程主要关注的模式**）



We have many different forms of parallel hardware. Our foucs will be **programming for multi-cores and SMPs**.



week 1 - basics of parallel computing and parallel program analysis.

week 2 - task-parallelism, basic parallel algorithms

week 3 - data-parallelism, Scala parallel collections

week 4 - data structures for parallel computing