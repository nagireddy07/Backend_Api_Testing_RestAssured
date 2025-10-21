package pojoclass.utility;

public class createProject {

   String projectName;
   String createdBy;
   int teamSize;
   String status;
   public createProject() {}
   	
    public createProject(String projectName, String createdBy, int teamSize, String status) {
	super();
	this.projectName = projectName;
	this.createdBy = createdBy;
	this.teamSize = teamSize;
	this.status = status;
}
	public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    public String getProjectName() {
        return projectName;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    public String getCreatedBy() {
        return createdBy;
    }
    
    public void setTeamSize(int teamSize) {
        this.teamSize = teamSize;
    }
    public int getTeamSize() {
        return teamSize;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
    
}