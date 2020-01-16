package sync;

import java.util.concurrent.locks.ReentrantLock;

import org.openjdk.jol.info.ClassLayout;

public class Utils {
	static ReentrantLock reentrantLock = new ReentrantLock();
	static LockTest lockTest = new LockTest();
	/*��������̰߳�ȫ����Ҫ����ͬ��
	�����������Ǽ�sync
	*/
	public static void lockTest0() {
		/*
		����juc���������ṩ����
		��new������ReentrrantLock����
		 */
		reentrantLock.lock(); 
		System.out.println(0);
		reentrantLock.unlock();		
	}
	public static void lockTest1() {
		//����Java��������
		synchronized(lockTest){
			System.out.println(1);
			System.out.println(2);
		}
	}
	public static void lockTest2(LockTest lockTest) {
		ClassLayout.parseInstance(lockTest);
	}
}
