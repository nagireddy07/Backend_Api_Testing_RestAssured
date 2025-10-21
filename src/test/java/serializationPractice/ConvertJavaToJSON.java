package serializationPractice;

import java.io.File;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonPropertyOrder(
value = {
		"projectName",
		"createdBy",
		"teamSize",
		"status"
})
@JsonIgnoreProperties(
		value = {
				"teamSize"
		}
)

class Project{
	private String projectName;
	@JsonProperty(value = "Created By")
	private String createdBy;
	private String status;
	private int teamSize;
	
	public Project() {
		
	}
	public Project(String projectName, String createdBy, String status, int teamSize) {
		super();
		this.projectName = projectName;
		this.createdBy = createdBy;
		this.status = status;
		this.teamSize = teamSize;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getTeamSize() {
		return teamSize;
	}
	public void setTeamSize(int teamSize) {
		this.teamSize = teamSize;
	}
	
}
public class ConvertJavaToJSON {
	public static void main(String[] args) throws Throwable {
	
		Project pObj = new Project("Sample Project", "Venkat", "Created", 0);
		ObjectMapper obj = new ObjectMapper();
		obj.writeValue(new File("./JavaToJson.json"), pObj);
		System.out.println("Converted From Java to JSON");
	}
}
