package atomicity;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Ŀ��:��ʾԭ��������
 *֪ʶ׼��:ԭ����(Atomicity):��һ�λ��β�����,Ҫô���еĲ�����ִ��
 *���Ҳ������������ظ��Ŷ��ж�,Ҫô���еĲ�������ִ��.
 * ����:
 * 1.����һ���������number
 * 2.��number���в���
 * 3.ʹ��5���߳�������
 * @author mcunb
 *
 */
public class Demo0 {
	//1.����һ���������number
	private static int number = 0;
	public static void main(String[] args) throws InterruptedException {
		
		/*
		 * 2.дһ��Runnable��ʵ��,�Ժ�ŵ��߳���ִ��
		 * 	��int�ͱ���number����1000��++����
		 *    
		 */
		Runnable increment = ()->{
			for(int i =0;i<1000;i++) {
				/*
				 * �ò���������ָ�����
				 * ���ƻ�ԭ����
				 */
				number++;
			}
		};
		
		//һ��Ҫ��forѭ�������߳�,���������װ.
		List<Thread> list = new ArrayList<>();
		//3.��forѭ������5���߳���ִ��ͬһ��Runnable�ӿڵ�ʵ��.
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
