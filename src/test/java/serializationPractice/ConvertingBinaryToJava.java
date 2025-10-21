package serializationPractice;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class ConvertingBinaryToJava {
	public static void main(String[] args) throws Throwable {
		FileInputStream fis = new FileInputStream("./SerializationPractice.txt");
		ObjectInputStream ois = new ObjectInputStream(fis);
		ConvertingJavaToBinary javaObj = (ConvertingJavaToBinary)ois.readObject();
		System.out.println(javaObj.name);
		System.out.println(javaObj.age);
	}
	
}
