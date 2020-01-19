##### 大纲
- java的对象布局
- sync底层工作的原理synchronized
- sync优化

##### synchronized
- synchronized 涉及点
    - 涉及JVM源码
    - 涉及C++
    - 涉及 VB汇编
    
- synchronized()需要传入一个对象
    - 在非静态方法里需要传入的对象
        - this 
    - 在静态方法里需要传入的对象
        - 类名.class 
        - 一个对象的实例，指向其实例的变量应当被static修饰。
- 问题
    - java当中的锁
        - 公平锁、非公平锁、读写锁、共享锁、互斥锁、自旋锁、偏向锁、轻量级锁、重量级锁 
    - 锁什么？锁代码块还是锁对象？

- 上锁就是改变对象的对象头
    - 对象头是所有对象开头的公共部分。
        - 对象头由两个词组成。 
            - 第一个词是MarkWord
            - 第二个词是kiass pointer，类的原数据的地址，以此可以辨识一个类的实例用的是哪一个模板。
        - 实例对象总是在对象头的下面。
        - 每个对象头都包括了堆对象的布局、类型、GC状态、同步状态和标识哈希码的基本基本信息。
            - 由此可以知道，对象的hashCode就存在对象的对象头里 
            - 加锁成功后会改变对象头的二进制码，记录同步状态。
            - 进行垃圾回收调用重复算法时，重复状态也被保存在对象头里。
            - 保存了一个指针，记录了一个类的实例属于哪个类。

- 对象在堆上要分配的内存不是固定的，所以会有内存溢出。
- 在JVM中，对象在内存中的布局分为三块区域：对象头、实例数据和对象填充。
    - 考虑Java的对象组成、对象的大小首先要考虑实例里面的实例属性（实例数据组成）
        - Java对象的实例数据 
            - 身为强类型语言每个变量都要占字节，各占的不一样。
            - 不同的类因为变量的数量不同大小不一样。
    - 要考虑对象头 
        - 对象头需要分配的内存是固定。
    - 数据对齐
        - 64bit JVM 要求一个对象的大小必须是8的整数位。
        - 1byte的对象会被存成8byte
        
###### 对象的组成与大小
- 如下代码所示，创建一个对象,该对象在堆上。
    - 考虑实例数据
        - boolean类型的变量会占一个字节。
        - int类型的变量会占4个字节。
    - 考虑数据对齐
        -  64bit JVM 要求一个对象的大小必须是8的整数位。

```
public class L{
    
    boolean flag = false;
    int test = 0;
}
```

###### 打印对象布局
- 导包
```
<dependency>
	<groupId>org.openjdk.jol</groupId>
	<artifactId>jol-core</artifactId>
	<version>0.9</version>
</dependency>
```
- 代码
```
public class LockTest {
	boolean b=false;
}
```
```
static LockTest lockTest = new LockTest();

@Test
public void testLock2() {
    String str = ClassLayout.parseInstance(lockTest).toPrintable();
		System.out.println(str);
}
```
- 打印不同的对象
    - 打印已经声明并初始化了一个布尔变量的类的结果
    ```
    res.LockTest0 object internals:
    OFFSET  SIZE     TYPE DESCRIPTION       VALUE
      0     4       (object header)   01 00 00 00 (00000001 00000000 00000000 00000000) (1)
      4     4       (object header)   00 00 00 00 (00000000 00000000 00000000 00000000) (0)
      8     4       (object header)   6b f0 fb 27 (01101011 11110000 11111011 00100111) (670822507)
     12     1   boolean LockTest0.b       false
     13     3       (loss due to the next object alignment)
    Instance size: 16 bytes
    Space losses: 0 bytes internal + 3 bytes external = 3 bytes total
    ```
    - 打印一个不包含任何属性、方法的类的结果
    ```
    res.LockTest1 object internals:
    OFFSET  SIZE   TYPE DESCRIPTION       VALUE
      0     4        (object header)   01 00 00 00 (00000001 00000000 00000000 00000000) (1)
      4     4        (object header)   00 00 00 00 (00000000 00000000 00000000 00000000) (0)
      8     4        (object header)   91 2c fc 27 (10010001 00101100 11111100 00100111) (670837905)
     12     4        (loss due to the next object alignment)
    Instance size: 16 bytes
    Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
    ```
- 大小分析
    - 在64位虚拟机下，对象在声明并初始化一个boolean变量的情况下是16 byte。
        - 此时前12 byte是(object)对象头。
        - 然后有1个byte是 boolean类型的变量。
        - 还有3个byte是填充 通过输出的`Space losses: 0 bytes internal + 3 bytes external = 3 bytes total`也可以看出。
        - 因为64bit JVM要求一个对象的大小必须是8的整数位，在对象头已经占12byte的情况下，有4位是填充，在boolean型变量占一个byte的情况下，就会有3个byte填充。
    - 在64位虚拟机下，对象在不包含任何属性、方法的情况下也是16 byte。
        - 此时前12 byte是(object)对象头。 
- 布局分析
    - 置于顶部的是Java的对象头，它是实现synchronized的锁对象的基础，一般而言，synchronized使用的锁对象是存储在Java对象头里的。
    - JVM使用两个字来存储对象头（如果对象是数组则会分配3个字，多出来的一个字记录的是数组长度），其主要结构是由Mark Word 和 Class Metadata Address 组成，其结构说明如下表。
    - 对象头分析，如下所示的对象头的二进制码，包含了各种信息：包括关于堆对象的布局、类型、GC状态、同步状态和标识哈希代码的基本信息。
    
    ```
     OFFSET  SIZE   TYPE DESCRIPTION       VALUE
      0     4        (object header)   01 00 00 00 (00000001 00000000 00000000 00000000) (1)
      4     4        (object header)   00 00 00 00 (00000000 00000000 00000000 00000000) (0)
      8     4        (object header)   91 2c fc 27 (10010001 00101100 11111100 00100111) (670837905)
    ```

- 小结
    - 如果对象头加实例数据的大小无法是8的倍数，则会产生对齐数据，所以java的对象组成不是固定的。
    - 因为只有对象头无法是8的倍数，所以没有实例数据就只有对象头和实例数据。

虚拟机位数 | 头对象结构 | 说明
---|---|---
32/64bit | Mark Word | 存储对象的hashCode、锁信息或分代年龄或GC标志等信息
32/64bit | klass pointer | 类型指针指向对象的类元数据，JVM通过这个指针确定该对象是哪个类的实例。

- Mark Word与klass pointer的大小
    - 在32 bit JVM下Mark Word是32 bit
        - 前25bit存的是hashCode
        - 在紧接着4个bit是GC分段年龄
        - 在紧接着1个bit是偏向锁的信息。
        - 在紧接着2个bit存的是同步状态
    ```
    hash:25 ------------>| age:4  biased_lock:1 lock:2 (normal object)
    JavaThread*:23 epoch:2 age:4  biased_lock:1 lock:2 (biased object)
    size:32 ----------------------------------------->|(CMS free block)
    PromotedObject*:29 --------->| promo_bits:3 ----->|(CMS promoted object)
    ```
    - 在64 bit JVM下Mark Word的大小为64 bit
        - 根据打印的对象布局，如果对象头是96 bit，则klass pointer/Class Metadata Address是32 bit
        - 不开启指针压缩的话klass pointer/Class Metadata Address是64 bit
    ```
    unused:25 hash:31 -->| unused:1  age:4  biased_lock:1 lock:2(normal object)
    JavaThread*:54 epoch:2 unused:1  age:4  biased_lock:1 lock:2(biased object)
    PromotedObject*:61 ------------------>| Promo_bits:3------>|(CMS promoted object)
    size:64 -------------------------------------------------->|(CMS free block)
    ```
###### Mark Word的内容是不固定的，根据对象的状态不同，里面存的信息也不同     
- 对象状态
    - 无状态
        - 指刚new出来的时候
        - 根据文档注释存的是`unused:25 hash:31 -->| unused:1  age:4  biased_lock:1 lock:2(normal object)`
    - 偏向锁
        - 上锁后是偏向锁的状态，只有一个线程，只有这个对象。
    - 轻量锁
    - 重量锁
    - 对象被gc标记
        - 当对象不被引用时就会被gc标记
- 以上状态对应的 Mark Word内容通过打印可以观测到。

- 下表对应的状态
    - 无锁
    - 偏向锁
    - 轻量锁
    - 重量锁
    - gc

```
|-----------------------------------------------------------------------------|------------------------| 
|           Object Header (128 bits/96 bits)                                  |                        |
|-----------------------------------------------------------------------------|------------------------| 
|               Mark Word(64 bits)                                            | Klass Word(64 bits)    | 
|-----------------------------------------------------------------------------|------------------------| 
| unused:25 | identity_hashcode:31 | unsed:1 | age:4 | biased_lock:1 | lock:2 | OOP to metadata object | 
|-----------------------------------------------------------------------------|------------------------| 
| thread:54 |      epoch：2        | unsed:1 | age:4 | biased_lock:1 | lock:2 | OOP to metadata object |
|-----------------------------------------------------------------------------|------------------------| 
|                      ptr_to_lock_record:62                         | lock:2 | OOP to metadata object |
|-----------------------------------------------------------------------------|------------------------| 
|                 ptr_to_heavyweight_monitor:62                      | lock:2 | OOP to metadata object |
|-----------------------------------------------------------------------------|------------------------| 
|                                                                    | lock:2 | OOP to metadata object |
|-----------------------------------------------------------------------------|------------------------| 

```
    
##### 探讨ReentrantLock和synchronized两种锁定机制的对比
###### 使用ReentrantLock的简单示例

```
static ReentrantLock reentrantLock = new ReentrantLock();
```
```
/**
   使用 ReentrantLock 的示例
*/
public static void lockTest() {


/*
这是juc并发包下提供的锁
给new出来的ReentrantLock上锁
*/
    reentrantLock.lock();
    System.out.println(0);
    reentrantLock.unlock();
}
```
###### ReentrantLock底层

- 在.java文件里打开ReentrantLock的lock()方法会看到
```
public void lock() {
        sync.lock();
    }
```
- 再把源码里的lock()进一步打开,定位到
```
abstract void lock();
```
- 上面的方法在ReentrantLock类里的Sync类里
    - 在ReentrantLock类里有以下两个类继承了Sync
        - FairSync 
        - NonfairSync
    - Sync类 继承了 抽象类AbstractQueuedSynchronizer    
    
- 继承了Sync的FairSync类里重写的Sync的lock()方法
```
final void lock() {
    acquire(1);
}
```
- 小结：
    - 继承了Sync的FairSync类里重写了Sync的lock()方法
    - 重写的lock()方法里的 acquire(1)方法来自于Sync类所继承的AbstractQueuedSynchronizer类。

- 打开acquire(1)，是AbstractQueuedSynchronizer类中的方法，而Sync类 继承了 抽象类AbstractQueuedSynchronizer。
```
 public final void acquire(int arg) {
        if (!tryAcquire(arg) &&
            acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
            selfInterrupt();
    }
```
- 再打开tryAcquire(arg)，也是AbstractQueuedSynchronizer类中的方法。
    
```
protected boolean tryAcquire(int arg) {
        throw new UnsupportedOperationException();
    }
```
-  在FairSync类里实现的tryAcquire(int arg)方法
-  最终实现的逻辑是改变对象里的变量的值，通过值的改变而使得上锁成功。
```
protected final boolean tryAcquire(int acquires) {
            final Thread current = Thread.currentThread();
            int c = getState();
            if (c == 0) {
                if (!hasQueuedPredecessors() &&
                    //调用该方法c/s操作
                    compareAndSetState(0, acquires)) {
                    setExclusiveOwnerThread(current);
                    return true;
                }
            }
            else if (current == getExclusiveOwnerThread()) {
                int nextc = c + acquires;
                if (nextc < 0)
                    throw new Error("Maximum lock count exceeded");
                setState(nextc);
                return true;
            }
            return false;
        }
```
- 打开getState()，该方法是AbstractQueuedSynchronizer类中的方法。
```
protected final int getState() {
        return state;
    }
```
- 需要改变的变量是 state，如果是0改成1
```
private volatile int state;
```
- 小结
    - ReentrantLock的实例调用lock()方法的本质意义是改变
    ReentrantLock对象里面的一个属性state的值，如果改成功就是上锁成功。
    - 所以
    
```
static ReentrantLock reentrantLock = new ReentrantLock();

    reentrantLock.lock();
	System.out.println(0);
	reentrantLock.unlock();	
```
    
###### 在静态方法里使用synchronized传入静态实例作为锁的示例

- 根据如下代码，假设lockTest()存在线程安全问题要进行同步
   - 基础的做法是加synchronized关键字
    
```
public class Utils {
static LockTest lockTest = new LockTest();
    public static void lockTest() {
        synchronized(lockTest){
		System.out.println("this has executed");
		}
	}
}	
```
```
    @Test
	public void testSync() {
		Utils.lockTest();
	}
```

- 如上所示，如果要加锁，一个线程进入后，另一个就不能进来。
- 而在之前的对ReentrantLock类的剖析，给ReentrantLock对象加锁需要有一个标识，记录加锁成功。
    - 在ReentrantLock类中因为FairSync类继承了Sync类，Sync类继承了抽象类AbstractQueuedSynchronizer，在FairSync类中重写AbstractQueuedSynchronizer类的tryAcquire(int arg)方法时会调用AbstractQueuedSynchronizer类中的getState()方法，该方法会返回state，改变它使得加锁成功。
- 所以思考用synchronized给对象加锁时是否是用同样的原理，其中是否也应该有一个标识 


