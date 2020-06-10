package atomicity;

import java.util.ArrayList;
import java.util.List;

public class AtomicityTest {
	//1.定义一个共享变量number
	private static int data = 0;

	public AtomicityTest() throws InterruptedException  {
		//一会要用for循环创建线程,用这个集合装.
		List<Thread> list = new ArrayList<>();

		//3.用for循环创建5个线程来执行同一个Runnable接口的实现.
		for(int i=0;i<5;i++) {
			Thread thread = new Thread(() -> {
				increment();
			});
			thread.start();
			list.add(thread);
		}

		for(Thread t:list) {
			//加上join表示会先执行完当前线程.
			//保证线程执行时不会被干扰的太厉害
			t.join();

		}
		System.out.println("data="+data);
	}
	//封装for循环
    private void increment() {
        for (int i = 0; i < 1000; i++) {
            data++;
        }
    }
}
