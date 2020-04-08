package visibility;
/**
 * 
 * @ClassName  Demo0 
 * @Description  本类演示可见性问题
 * @author  blue  
 * @date  2020年4月7日 下午5:43:35     
 *
 */
public class Demo0 {
	//1.创建一个共享变量,观察共享变量的值用来测试.
	private static boolean flag = true;
	public static void main(String[] args) throws InterruptedException {
	/*
	 * 多线程运行具有随机性,有可能先跑线程1,也有可能先跑线程2?
	 * 经过测试一直都是1先跑.
	 */
		new Thread(()->{
			System.out.println("线程1执行了");
			while(flag) {
				/*
				 *可以看到始终没有打印出,
				 *说明在另一个线程里的赋值操作,
				 *这里并没有感知到.
				 */
				if(flag==false) {
					System.out.println(1);
				}
			}
		}).start();
		
		//睡眠两秒
		Thread.sleep(2000);
		
		new Thread(()->{
			System.out.println("线程2执行了");
			flag = false;
			System.out.println("线程修改了变量的值为false");
		}).start();
	}
}
