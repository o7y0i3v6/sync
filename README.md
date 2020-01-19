##### ���
- java�Ķ��󲼾�
- sync�ײ㹤����ԭ��synchronized
- sync�Ż�

##### synchronized
- synchronized �漰��
    - �漰JVMԴ��
    - �漰C++
    - �漰 VB���
    
- synchronized()��Ҫ����һ������
    - �ڷǾ�̬��������Ҫ����Ķ���
        - this 
    - �ھ�̬��������Ҫ����Ķ���
        - ����.class 
        - һ�������ʵ����ָ����ʵ���ı���Ӧ����static���Ρ�
- ����
    - java���е���
        - ��ƽ�����ǹ�ƽ������д����������������������������ƫ������������������������ 
    - ��ʲô��������黹��������

- �������Ǹı����Ķ���ͷ
    - ����ͷ�����ж���ͷ�Ĺ������֡�
        - ����ͷ����������ɡ� 
            - ��һ������MarkWord
            - �ڶ�������kiass pointer�����ԭ���ݵĵ�ַ���Դ˿��Ա�ʶһ�����ʵ���õ�����һ��ģ�塣
        - ʵ�����������ڶ���ͷ�����档
        - ÿ������ͷ�������˶Ѷ���Ĳ��֡����͡�GC״̬��ͬ��״̬�ͱ�ʶ��ϣ��Ļ���������Ϣ��
            - �ɴ˿���֪���������hashCode�ʹ��ڶ���Ķ���ͷ�� 
            - �����ɹ����ı����ͷ�Ķ������룬��¼ͬ��״̬��
            - �����������յ����ظ��㷨ʱ���ظ�״̬Ҳ�������ڶ���ͷ�
            - ������һ��ָ�룬��¼��һ�����ʵ�������ĸ��ࡣ

- �����ڶ���Ҫ������ڴ治�ǹ̶��ģ����Ի����ڴ������
- ��JVM�У��������ڴ��еĲ��ַ�Ϊ�������򣺶���ͷ��ʵ�����ݺͶ�����䡣
    - ����Java�Ķ�����ɡ�����Ĵ�С����Ҫ����ʵ�������ʵ�����ԣ�ʵ��������ɣ�
        - Java�����ʵ������ 
            - ��Ϊǿ��������ÿ��������Ҫռ�ֽڣ���ռ�Ĳ�һ����
            - ��ͬ������Ϊ������������ͬ��С��һ����
    - Ҫ���Ƕ���ͷ 
        - ����ͷ��Ҫ������ڴ��ǹ̶���
    - ���ݶ���
        - 64bit JVM Ҫ��һ������Ĵ�С������8������λ��
        - 1byte�Ķ���ᱻ���8byte
        
###### �����������С
- ���´�����ʾ������һ������,�ö����ڶ��ϡ�
    - ����ʵ������
        - boolean���͵ı�����ռһ���ֽڡ�
        - int���͵ı�����ռ4���ֽڡ�
    - �������ݶ���
        -  64bit JVM Ҫ��һ������Ĵ�С������8������λ��

```
public class L{
    
    boolean flag = false;
    int test = 0;
}
```

###### ��ӡ���󲼾�
- ����
```
<dependency>
	<groupId>org.openjdk.jol</groupId>
	<artifactId>jol-core</artifactId>
	<version>0.9</version>
</dependency>
```
- ����
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
- ��ӡ��ͬ�Ķ���
    - ��ӡ�Ѿ���������ʼ����һ��������������Ľ��
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
    - ��ӡһ���������κ����ԡ���������Ľ��
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
- ��С����
    - ��64λ������£���������������ʼ��һ��boolean�������������16 byte��
        - ��ʱǰ12 byte��(object)����ͷ��
        - Ȼ����1��byte�� boolean���͵ı�����
        - ����3��byte����� ͨ�������`Space losses: 0 bytes internal + 3 bytes external = 3 bytes total`Ҳ���Կ�����
        - ��Ϊ64bit JVMҪ��һ������Ĵ�С������8������λ���ڶ���ͷ�Ѿ�ռ12byte������£���4λ����䣬��boolean�ͱ���ռһ��byte������£��ͻ���3��byte��䡣
    - ��64λ������£������ڲ������κ����ԡ������������Ҳ��16 byte��
        - ��ʱǰ12 byte��(object)����ͷ�� 
- ���ַ���
    - ���ڶ�������Java�Ķ���ͷ������ʵ��synchronized��������Ļ�����һ����ԣ�synchronizedʹ�õ��������Ǵ洢��Java����ͷ��ġ�
    - JVMʹ�����������洢����ͷ���������������������3���֣��������һ���ּ�¼�������鳤�ȣ�������Ҫ�ṹ����Mark Word �� Class Metadata Address ��ɣ���ṹ˵�����±�
    - ����ͷ������������ʾ�Ķ���ͷ�Ķ������룬�����˸�����Ϣ���������ڶѶ���Ĳ��֡����͡�GC״̬��ͬ��״̬�ͱ�ʶ��ϣ����Ļ�����Ϣ��
    
    ```
     OFFSET  SIZE   TYPE DESCRIPTION       VALUE
      0     4        (object header)   01 00 00 00 (00000001 00000000 00000000 00000000) (1)
      4     4        (object header)   00 00 00 00 (00000000 00000000 00000000 00000000) (0)
      8     4        (object header)   91 2c fc 27 (10010001 00101100 11111100 00100111) (670837905)
    ```

- С��
    - �������ͷ��ʵ�����ݵĴ�С�޷���8�ı������������������ݣ�����java�Ķ�����ɲ��ǹ̶��ġ�
    - ��Ϊֻ�ж���ͷ�޷���8�ı���������û��ʵ�����ݾ�ֻ�ж���ͷ��ʵ�����ݡ�

�����λ�� | ͷ����ṹ | ˵��
---|---|---
32/64bit | Mark Word | �洢�����hashCode������Ϣ��ִ������GC��־����Ϣ
32/64bit | klass pointer | ����ָ��ָ��������Ԫ���ݣ�JVMͨ�����ָ��ȷ���ö������ĸ����ʵ����

- Mark Word��klass pointer�Ĵ�С
    - ��32 bit JVM��Mark Word��32 bit
        - ǰ25bit�����hashCode
        - �ڽ�����4��bit��GC�ֶ�����
        - �ڽ�����1��bit��ƫ��������Ϣ��
        - �ڽ�����2��bit�����ͬ��״̬
    ```
    hash:25 ------------>| age:4  biased_lock:1 lock:2 (normal object)
    JavaThread*:23 epoch:2 age:4  biased_lock:1 lock:2 (biased object)
    size:32 ----------------------------------------->|(CMS free block)
    PromotedObject*:29 --------->| promo_bits:3 ----->|(CMS promoted object)
    ```
    - ��64 bit JVM��Mark Word�Ĵ�СΪ64 bit
        - ���ݴ�ӡ�Ķ��󲼾֣��������ͷ��96 bit����klass pointer/Class Metadata Address��32 bit
        - ������ָ��ѹ���Ļ�klass pointer/Class Metadata Address��64 bit
    ```
    unused:25 hash:31 -->| unused:1  age:4  biased_lock:1 lock:2(normal object)
    JavaThread*:54 epoch:2 unused:1  age:4  biased_lock:1 lock:2(biased object)
    PromotedObject*:61 ------------------>| Promo_bits:3------>|(CMS promoted object)
    size:64 -------------------------------------------------->|(CMS free block)
    ```
###### Mark Word�������ǲ��̶��ģ����ݶ����״̬��ͬ����������ϢҲ��ͬ     
- ����״̬
    - ��״̬
        - ָ��new������ʱ��
        - �����ĵ�ע�ʹ����`unused:25 hash:31 -->| unused:1  age:4  biased_lock:1 lock:2(normal object)`
    - ƫ����
        - ��������ƫ������״̬��ֻ��һ���̣߳�ֻ���������
    - ������
    - ������
    - ����gc���
        - �����󲻱�����ʱ�ͻᱻgc���
- ����״̬��Ӧ�� Mark Word����ͨ����ӡ���Թ۲⵽��

- �±��Ӧ��״̬
    - ����
    - ƫ����
    - ������
    - ������
    - gc

```
|-----------------------------------------------------------------------------|------------------------| 
|           Object Header (128 bits/96 bits)                                  |                        |
|-----------------------------------------------------------------------------|------------------------| 
|               Mark Word(64 bits)                                            | Klass Word(64 bits)    | 
|-----------------------------------------------------------------------------|------------------------| 
| unused:25 | identity_hashcode:31 | unsed:1 | age:4 | biased_lock:1 | lock:2 | OOP to metadata object | 
|-----------------------------------------------------------------------------|------------------------| 
| thread:54 |      epoch��2        | unsed:1 | age:4 | biased_lock:1 | lock:2 | OOP to metadata object |
|-----------------------------------------------------------------------------|------------------------| 
|                      ptr_to_lock_record:62                         | lock:2 | OOP to metadata object |
|-----------------------------------------------------------------------------|------------------------| 
|                 ptr_to_heavyweight_monitor:62                      | lock:2 | OOP to metadata object |
|-----------------------------------------------------------------------------|------------------------| 
|                                                                    | lock:2 | OOP to metadata object |
|-----------------------------------------------------------------------------|------------------------| 

```
    
##### ̽��ReentrantLock��synchronized�����������ƵĶԱ�
###### ʹ��ReentrantLock�ļ�ʾ��

```
static ReentrantLock reentrantLock = new ReentrantLock();
```
```
/**
   ʹ�� ReentrantLock ��ʾ��
*/
public static void lockTest() {


/*
����juc���������ṩ����
��new������ReentrantLock����
*/
    reentrantLock.lock();
    System.out.println(0);
    reentrantLock.unlock();
}
```
###### ReentrantLock�ײ�

- ��.java�ļ����ReentrantLock��lock()�����ῴ��
```
public void lock() {
        sync.lock();
    }
```
- �ٰ�Դ�����lock()��һ����,��λ��
```
abstract void lock();
```
- ����ķ�����ReentrantLock�����Sync����
    - ��ReentrantLock����������������̳���Sync
        - FairSync 
        - NonfairSync
    - Sync�� �̳��� ������AbstractQueuedSynchronizer    
    
- �̳���Sync��FairSync������д��Sync��lock()����
```
final void lock() {
    acquire(1);
}
```
- С�᣺
    - �̳���Sync��FairSync������д��Sync��lock()����
    - ��д��lock()������� acquire(1)����������Sync�����̳е�AbstractQueuedSynchronizer�ࡣ

- ��acquire(1)����AbstractQueuedSynchronizer���еķ�������Sync�� �̳��� ������AbstractQueuedSynchronizer��
```
 public final void acquire(int arg) {
        if (!tryAcquire(arg) &&
            acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
            selfInterrupt();
    }
```
- �ٴ�tryAcquire(arg)��Ҳ��AbstractQueuedSynchronizer���еķ�����
    
```
protected boolean tryAcquire(int arg) {
        throw new UnsupportedOperationException();
    }
```
-  ��FairSync����ʵ�ֵ�tryAcquire(int arg)����
-  ����ʵ�ֵ��߼��Ǹı������ı�����ֵ��ͨ��ֵ�ĸı��ʹ�������ɹ���
```
protected final boolean tryAcquire(int acquires) {
            final Thread current = Thread.currentThread();
            int c = getState();
            if (c == 0) {
                if (!hasQueuedPredecessors() &&
                    //���ø÷���c/s����
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
- ��getState()���÷�����AbstractQueuedSynchronizer���еķ�����
```
protected final int getState() {
        return state;
    }
```
- ��Ҫ�ı�ı����� state�������0�ĳ�1
```
private volatile int state;
```
- С��
    - ReentrantLock��ʵ������lock()�����ı��������Ǹı�
    ReentrantLock���������һ������state��ֵ������ĳɹ����������ɹ���
    - ����
    
```
static ReentrantLock reentrantLock = new ReentrantLock();

    reentrantLock.lock();
	System.out.println(0);
	reentrantLock.unlock();	
```
    
###### �ھ�̬������ʹ��synchronized���뾲̬ʵ����Ϊ����ʾ��

- �������´��룬����lockTest()�����̰߳�ȫ����Ҫ����ͬ��
   - �����������Ǽ�synchronized�ؼ���
    
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

- ������ʾ�����Ҫ������һ���߳̽������һ���Ͳ��ܽ�����
- ����֮ǰ�Ķ�ReentrantLock�����������ReentrantLock���������Ҫ��һ����ʶ����¼�����ɹ���
    - ��ReentrantLock������ΪFairSync��̳���Sync�࣬Sync��̳��˳�����AbstractQueuedSynchronizer����FairSync������дAbstractQueuedSynchronizer���tryAcquire(int arg)����ʱ�����AbstractQueuedSynchronizer���е�getState()�������÷����᷵��state���ı���ʹ�ü����ɹ���
- ����˼����synchronized���������ʱ�Ƿ�����ͬ����ԭ�������Ƿ�ҲӦ����һ����ʶ 


