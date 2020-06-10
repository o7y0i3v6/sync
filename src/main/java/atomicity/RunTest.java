package atomicity;

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
public class RunTest {

	public static void main(String[] args) throws InterruptedException {
		//从这里也可以看出,只要是一个类,始终共享一个全局变量.
		for(int i=0;i<10;i++) {
			AtomicityTest a = new AtomicityTest();
		}	
	}
}
