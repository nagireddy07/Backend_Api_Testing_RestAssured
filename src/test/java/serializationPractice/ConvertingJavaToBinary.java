package serializationPractice;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ConvertingJavaToBinary implements Serializable {
	String name;
	int age;
	public ConvertingJavaToBinary(String name,int age) {
		this.name = name;
		this.age=age;
	}
	public static void main(String[] args) throws Throwable {
		ConvertingJavaToBinary obj = new ConvertingJavaToBinary("Venkat", 23);
		FileOutputStream fos = new FileOutputStream("./SerializationPractice.txt");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(obj);
		System.out.println("Converted");
	}
}
