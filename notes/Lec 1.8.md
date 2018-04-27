### Lec 1.8 - Benchmarking Parallel Programs

*Testing* is different from *benchmarking*.



Performance (specifically, running time) is subject to many factors:

- processor speed
- number of processors

// 内存速度与内存带宽

- memory access latency and throughput (affects contention)
- cache behavior (e.g. false sharing, associativity effects)
- runtime behavior (e.g. garbage collection, JIT compilation, thread scheduling)



Measuring performance is difficult - usually, the performance metric is a random variable.

- multiple repetitions 
- statistical treatment - computing mean and variance
- eliminating outliers
- ensuring steady state
- preventing anomalies



We use **ScalaMeter** to do benchmarking.



```scala
import org.scalameter._

val time = withWarmer(new Warmer.Default) measure {
    (0 until 100000).toArray
}

val time = config(
    Key.exec.minWarmupRuns -> 20,
    Key.exec.maxWarmupRuns -> 60,
    Key.verbose -> true
) withWarmer(new Warmer.Default) measure {
    (0 until 100000).toArray
}

// measure the total amount of memory occupied by the object
withMeasurer(new Measurer.MemoryFootprint) measure {
    (0 until 100000).toArray
} 
```





