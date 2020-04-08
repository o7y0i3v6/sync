package atomicity;

import java.util.ArrayList;
import java.util.List;

public class AtomicityTest {
	//1.定义一个共享变量number
		private static int number = 0;
		
		public AtomicityTest(int arg) throws InterruptedException  {
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
				//加上join表示会先执行完当前线程.
				t.join();
			}
			
			System.out.println("number="+number);
		}
}
