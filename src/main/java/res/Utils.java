package res;

import java.util.concurrent.locks.ReentrantLock;

import org.openjdk.jol.info.ClassLayout;

public class Utils {
	static ReentrantLock reentrantLock = new ReentrantLock();
	 static LockTest0 lockTest0 = new LockTest0();
	 static LockTest1 lockTest1 = new LockTest1();
	 static LockTest2 lockTest2 = new LockTest2();
	/*假设存在线程安全问题要进行同步
	基础的做法是加sync
	*/
	public static void lockTest0() {
		/*
		这是juc并发包下提供的锁
		给new出来的ReentrrantLock上锁
		 */
		reentrantLock.lock(); 
		System.out.println(0);
		reentrantLock.unlock();		
	}
	public static void lockTest1() {
		//这是Java的内置锁
		synchronized(lockTest0){
			System.out.println(1);
			System.out.println(2);
		}
	}
	public static void printObject0() {
		//传入一个对象，把布局打印出来。
		String str = ClassLayout.parseInstance(lockTest0).toPrintable();
		System.out.println(str);
	}
	public static void printObject1() {
		//传入一个对象，把布局打印出来。
		String str = ClassLayout.parseInstance(lockTest1).toPrintable();
		System.out.println(str);
	}
	public static void printObject2() {
		//传入一个对象，把布局打印出来。
		String str = ClassLayout.parseInstance(lockTest2).toPrintable();
		System.out.println(str);
	}
}
