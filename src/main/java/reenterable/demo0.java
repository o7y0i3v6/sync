package reenterable;



/**
 * 本类演示synchronized的可重入性
 * 同一个线程多次执行synchronized会拿到同一把锁
 * 
 * 1.自定义一个线程类
 * 2.在线程类的run方法中使用嵌套的同步代码块
 * 3.使用两个线程来执行
 * @author mcunb
 *
 */
public class demo0 {
	//主线程从main方法开始会启动两个线程.
	public static void main(String[] args) {
		new MyThread().start();
		new MyThread().start();
	}
}
class MyThread extends Thread{
	@Override
	public void run() {
		/*
		 * 进入第一个代码快拿了一把锁
		 * 锁对象里有计数器会记录自己被取了几次
		 * 
		 * 另一个线程获知这个锁被拿到就会等着.
		 */
		synchronized (MyThread.class) {
			//这里会打印线程n进入1
			System.out.println(getName()+"进入了同步代码块1");
		/*
		 * 这里会打印线程n进入2
		 * 同时锁对象里的计数器会记录自己又被拿了一次.
		 * 进入第二个代码块又拿这把锁,说明了synchronized的可重入性
		 * 
		 * 再继续往下走,同步代码块结束会把锁释放.意味着计数器会减一
		 * 再
		 */
			synchronized(MyThread.class) {
				System.out.println(getName()+"进入了同步代码块2");
			}
		}
	}
}
