package sync;

import org.junit.Test;

import ObjectDemo.LockTest0;
import ObjectDemo.Utils;

public class Tests {
	static LockTest0 lockTest = new LockTest0();
	@Test
	public void testLock0() {
		Utils.lockTest0();
	}
	/**
	 * ±æ¿‡—› æreentrantLock
	 */
	@Test
	public void testLock1() {
		Utils.lockTest1();
	}
	@Test
	public void printObject0() {
		Utils.printObject0();
	}
	@Test
	public void printObject1() {
		Utils.printObject1();
	}
	@Test
	public void printObject2() {
		Utils.printObject2();
	}
	@Test
	public void printObject3() {
		Utils.printObject3();
	}
}
