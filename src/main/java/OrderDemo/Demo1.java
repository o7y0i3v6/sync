package OrderDemo;
import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.I_Result;

import org.openjdk.jcstress.infra.*;

/**
 * 有序性(Ordering):是指程序中代码的执行顺序
 * java在编译和运行时和运行时或对代码进行优化,会导致程序最终
 * 的执行顺序不一定就是编写代码时的顺序.
 * @author mcunb
 *
 */
//该注解识别测试类
@JCStressTest
//对输出结果的处理
@Outcome(id = {"1","4"},expect = Expect.ACCEPTABLE,desc="ok")
@Outcome(id = "0",expect = Expect.ACCEPTABLE_INTERESTING,desc="danger")
@State
public class Demo1 {
	int num = 0;
	boolean ready = false;
	//@Actor注解,表示由多个线程执行这两个方法
	@Actor
	public void actor1(I_Result r) {
		if(ready) {
			r.r1=num+num;
		}else {
			r.r1=1;
		}
	}
	
	@Actor
	public void actor2(I_Result r) {
		num=2;
		ready=true;
	}
}
