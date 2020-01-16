package concurrent;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestContainer<E> {
	private final LinkedList<E> list = new LinkedList<>();
	private final int Max = 10;
	private int count = 0;
	//生产者和消费者以及另外一种实现方式用的是ReentrantLock()。
	//条件Condition，为Lock增加条件。当条件满足时，做什么事情，或加锁或解锁。
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
