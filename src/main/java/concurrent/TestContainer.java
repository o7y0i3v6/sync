package concurrent;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestContainer<E> {
	private final LinkedList<E> list = new LinkedList<>();
	private final int Max = 10;
	private int count = 0;
	//�����ߺ��������Լ�����һ��ʵ�ַ�ʽ�õ���ReentrantLock()��
	//����Condition��ΪLock��������������������ʱ����ʲô���飬������������
	private Lock lock = new ReentrantLock();
	private Condition producer = lock.newCondition();
	private Condition consumer = lock.newCondition();
	
	public int getCount() {
		return count;
	}
	
	public void put(E e) {
		lock.lock();
		try {
			while(list.size()== Max) {
				System.out.println(Thread.currentThread().getName());
				producer.await();
			}
			System.out.println(Thread.currentThread().getName()+"put...");
			list.add(e);
			count++;
			consumer.signalAll();
		}catch(InterruptedException e1) {
			e1.printStackTrace();
		}finally {
			lock.unlock();	
		}
	}
}
