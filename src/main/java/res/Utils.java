package res;

import java.util.concurrent.locks.ReentrantLock;

import org.openjdk.jol.info.ClassLayout;

public class Utils {
	static ReentrantLock reentrantLock = new ReentrantLock();
	 static LockTest0 lockTest0 = new LockTest0();
	 static LockTest1 lockTest1 = new LockTest1();
	 static LockTest2 lockTest2 = new LockTest2();
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
		synchronized(lockTest0){
			System.out.println(1);
			System.out.println(2);
		}
	}
	public static void printObject0() {
		//����һ�����󣬰Ѳ��ִ�ӡ������
		String str = ClassLayout.parseInstance(lockTest0).toPrintable();
		System.out.println(str);
	}
	public static void printObject1() {
		//����һ�����󣬰Ѳ��ִ�ӡ������
		String str = ClassLayout.parseInstance(lockTest1).toPrintable();
		System.out.println(str);
	}
	public static void printObject2() {
		//����һ�����󣬰Ѳ��ִ�ӡ������
		String str = ClassLayout.parseInstance(lockTest2).toPrintable();
		System.out.println(str);
	}
}
