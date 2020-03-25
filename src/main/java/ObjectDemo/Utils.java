package ObjectDemo;

import java.util.concurrent.locks.ReentrantLock;

import org.openjdk.jol.info.ClassLayout;

public class Utils {
	static ReentrantLock reentrantLock = new ReentrantLock();
	static LockTest0 lockTest0 = new LockTest0();
	static LockTest1 lockTest1 = new LockTest1();
	static LockTest2 lockTest2 = new LockTest2();

	/*��������̰߳�ȫ����Ҫ����ͬ��
		�����������Ǽ�sync
		�������synchronized
	 */
	public static void lockTest0() {
		//����Java��������
		synchronized(lockTest0){
			System.out.println(1);
			System.out.println(2);
		}
	}
	/**
	 * �������ReentrrantLock
	 */
	public static void lockTest1() {
		/*
		����juc���������ṩ����
		��new������ReentrrantLock����
		 */
		reentrantLock.lock(); 
		System.out.println(0);
		reentrantLock.unlock();		
	}

	/**
	 * ��������ӡLockTest0
	 * LockTest0��ֻ��һ������ֵ
	 */
	public static void printObject0() {
		//����һ�����󣬰Ѳ��ִ�ӡ������
		String str = ClassLayout.parseInstance(lockTest0).toPrintable();
		System.out.println(str);
	}
	/**
	 * ��������ӡLockTest1
	 * LockTest1��û���κ�ʵ������
	 */
	public static void printObject1() {
		//����һ�����󣬰Ѳ��ִ�ӡ������
		String str = ClassLayout.parseInstance(lockTest1).toPrintable();
		System.out.println(str);
	}
	/**
	 * ��������ӡLockTest2
	 * LockTest2��ֻ��һ�� int ֵ
	 */
	public static void printObject2() {
		//����һ�����󣬰Ѳ��ִ�ӡ������
		String str = ClassLayout.parseInstance(lockTest2).toPrintable();
		System.out.println(str);
	}
	
	/**
	 * ���������Դ�ӡ�Զ��󲼾ֵ�ͬʱ��ӡhashCode.
	 */
	public static void printObject3() {
		//����Ĳ���
		String objectMessage = ClassLayout.parseInstance(lockTest0).toPrintable();
		
		lockTest0.hashCode();
		//��ϣֵ
		String hashMessage = Integer.toHexString(lockTest0.hashCode());

		System.out.println(hashMessage);
		System.out.println(objectMessage);
	}
}
