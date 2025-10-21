package serializationPractice;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertJSONToJava {
	public static void main(String[] args) throws StreamReadException, DatabindException, IOException {
		ObjectMapper obj = new ObjectMapper();
		Project pObj = obj.readValue(new File("./JavaToJson"), Project.class);
		System.out.println(pObj.getProjectName());
		System.out.println(pObj.getCreatedBy());
		System.out.println(pObj.getStatus());
		System.out.println(pObj.getTeamSize());
	}
}
