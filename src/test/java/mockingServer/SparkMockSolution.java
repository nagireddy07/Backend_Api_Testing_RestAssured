package mockingServer;

import com.jayway.jsonpath.JsonPath;

import spark.Spark;
import org.apache.commons.lang3.StringUtils;

public class SparkMockSolution {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		Spark.port(8989);
		Spark.post("/credit-card", (req,res)->{
			String response="";
			String card = JsonPath.read(req.body().toString(), "$.creditcard");
			if(StringUtils.equalsAny(card, "123456789123","123456789124")) {
				response = "{\"status\" : \" Payment Success\"}";
				res.status(200);
			}else {
				response = "{\"status\" : \" Payment Failed\"}";
				res.status(404);
			}
			res.type("application/json");
			return response;
		});
		System.out.println("===Running====");
	}
}
