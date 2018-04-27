### Lec 1.2 - Parallelism on the JVM

There are many forms of parallelism. (GPU, clusters, multi-core….) In this course, our parallel progrmming model assumption

- multicore or multiprocessor systems with shared memory.
- Operating system and the JVM as the underlying runtime evironments.

**Operating System**: software that manages hardware and software resources, and schedules program execution.

**Process**: an instance of a program that is executing in ths OS.

The same program can be started as a process more than once, or even simultaneously in the same OS.

The operating system multiplexes many different processes and a limited number of CPUs, so that they get *time slices* of execution. This mechanism is called *multitasking*.

Two different processes cannot access each other;s memory dirctly - they are isolated.



Each process can contain multiple independent concurrency units called *threads*. Threads can be started from within the same program, and they share the same memory address space.  Each thread has a program counter and a program stack.



JVM 不允许线程间访问对方的私有栈空间（读/写），线程间信息沟通需要通过堆内存（heap memory）。

Each JVM process starts with a **main thread**. 

To start additional threads, we need to define a Thread subclass, instantiate a new Thread object and call *start* on the Thread object.

```scala
class HelloThread extends Thread {
    override def run() {
        println("Hello")
        println("world!")
    }
}
val t = new HelloThread
val s = new HelloThread
t.start()
s.start()
t.join() // wait for the t complete.
s.join()
// t and s will work at the same time, so may print 'Hello Hello World World'
// Atomicity problem 原子性问题
```



Java and Scala use *synchronized* block to ensure an object *x* is never executed by two threads at the same time. **JVM gives a monitor（类似于锁） for each object**.

```scala
private val x = new AnyRef {}
private var uidCount = 0L
def getUniqueId(): Long = x.synchronized {
    uidCount = uidCount + 1 
    uidCount
}
```

The *synchronized* block is an example of a *synchronization primitive*. Invocations of the synchronized block can nest.

```scala
// 同时锁住堆区的两个对象，但会出现死锁
class Account(private var amount: Int = 0) {
    def transfer(target: Account, n: Int) =
    	this.sychronized {
            target.synchronized {
                this.amount -= n
                target.amount += n
            }
    	}
}

// 以下为死锁，两个线程均等待对方释放锁
a transfer 10 to b
b transfer 10 to a
```

One approach to solve deadlocks, is to always acquire resources in the same order. This assumes an ordering relationship on the resources.



#### Memory model

Memory model is a set of rules that describes how threads interact when accessing shared memory.

Java Memory Model for JVM: (we need to remember 2 of them)

- Two threads writing to separate locations in memory do not need synchronization.
- A thread X that calls join on another thread Y is guaranteed to observe all the writes by thread Y after join returns.





