package atomicity;

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
public class RunTest {

	public static void main(String[] args) throws InterruptedException {
		//������Ҳ���Կ���,ֻҪ��һ����,ʼ�չ���һ��ȫ�ֱ���.
		for(int i=0;i<10;i++) {
			AtomicityTest a = new AtomicityTest();
		}	
	}
}
