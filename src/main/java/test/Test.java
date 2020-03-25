package test;

public class Test {

	static LockObject lock = new LockObject();

	public static void main(String[] args) {
		lockTest();
	}


	public static void lockTest(){
		synchronized(Test.class) {
			System.out.println("xxx");
		}
	}
}