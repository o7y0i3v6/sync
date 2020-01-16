package sync;

import java.util.concurrent.locks.ReentrantLock;

import org.openjdk.jol.info.ClassLayout;

public class Utils {
	static ReentrantLock reentrantLock = new ReentrantLock();
	static LockTest lockTest = new LockTest();
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
		synchronized(lockTest){
			System.out.println(1);
			System.out.println(2);
		}
	}
	public static void lockTest2(LockTest lockTest) {
		ClassLayout.parseInstance(lockTest);
	}
}
