package backendTesting.genericUtilities;

import java.util.List;

import com.jayway.jsonpath.JsonPath;

import io.restassured.response.Response;

public class JSON_Utility {
	public String getDataOnJsonPath(Response resp,String JsonXPath) {
		List<Object> list = JsonPath.read(resp.asString(), JsonXPath);
		return list.get(0).toString();
	}
	
	public String getDataOnXmlPath(Response resp,String XmlXPath) {
		return resp.xmlPath().getString(XmlXPath);
	}
	
	public boolean VerifyDataonJsonPath(Response resp, String JsonXPath, String expectedData) {
		List<Object> list = JsonPath.read(resp.asString(), JsonXPath);
		boolean flag = false;
		for(Object str : list) {
			if(str.equals(expectedData)) {
				flag = true;
				break;
			}
		}
		return flag;
		
	}
	
	
}
