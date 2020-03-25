package atomicity;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 目标:演示原子性问题
 *知识准备:原子性(Atomicity):在一次或多次操作中,要么所有的操作都执行
 *并且不会受其他因素干扰而中断,要么所有的操作都不执行.
 * 步骤:
 * 1.定义一个共享变量number
 * 2.对number进行操作
 * 3.使用5个线程来进行
 * @author mcunb
 *
 */
public class Demo0 {
	//1.定义一个共享变量number
	private static int number = 0;
	public static void main(String[] args) throws InterruptedException {
		
		/*
		 * 2.写一个Runnable的实现,稍后放到线程中执行
		 * 	对int型变量number进行1000的++操作
		 *    
		 */
		Runnable increment = ()->{
			for(int i =0;i<1000;i++) {
				/*
				 * 该操作由四条指令完成
				 * 会破坏原子性
				 */
				number++;
			}
		};
		
		//一会要用for循环创建线程,用这个集合装.
		List<Thread> list = new ArrayList<>();
		//3.用for循环创建5个线程来执行同一个Runnable接口的实现.
		for(int i=0;i<5;i++) {
			Thread t = new Thread(increment);
			t.start();
			list.add(t);
		}
		
		for(Thread t:list) {
			t.join();
		}
		
		System.out.println("number="+number);
	}
}
