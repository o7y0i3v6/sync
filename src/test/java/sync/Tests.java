package sync;

import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

import sync.Utils;

public class Tests {
	static LockTest lockTest = new LockTest();
	@Test
	public void testLock0() {
		Utils.lockTest0();
	}
	@Test
	public void testLock1() {
		Utils.lockTest1();
	}


	@Test
	public void testLock2() {
		Utils.lockTest2(lockTest);
	}
}
