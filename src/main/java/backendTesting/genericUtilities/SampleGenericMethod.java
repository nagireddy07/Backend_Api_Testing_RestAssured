package backendTesting.genericUtilities;

public class SampleGenericMethod {
	public static void main(String[] args) { //callimg function
		System.out.println(add(10,20));
	}
	public static int add(int a,int b) { //called method - generic method
		return a+b;
	}
}
