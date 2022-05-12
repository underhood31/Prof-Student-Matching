package in.ac.iiitd.projecto.Model;

public class ProjectItem {

    private String projectTitle, projectAdvisorName, projectTechStack, projectDescription;
    private int projectTimeRequired, projectRequiredStudents, projectId;
    private Boolean projectStatus;
    public ProjectItem(){

    }
     public ProjectItem(String title, String projectDescription,String projectTechStack, int projectTimeRequired, int projectRequiredStudents, Boolean projectStatus){
        this.projectTitle = title;
        this.projectTimeRequired = projectTimeRequired;
        this.projectRequiredStudents= projectRequiredStudents;
        this.projectStatus = projectStatus;
        this.projectTechStack= projectTechStack;
        this.projectDescription=projectDescription;
     }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectTitle(){
        return projectTitle;
    }

    public void setProjectTitle(String title){
        this.projectTitle = title;
    }

    public String getProjectAdvisorName(){
        return projectAdvisorName;
    }

    public void setProjectAdvisorName(String name){
        this.projectAdvisorName = name;
    }

    public String getProjectTechStack() {
        return projectTechStack;
    }

    public void setProjectTechStack(String projectTechStack) {
        this.projectTechStack = projectTechStack;
    }


    public Boolean getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(Boolean projectStatus) {
        this.projectStatus = projectStatus;
    }

    public int getProjectRequiredStudents() {
        return projectRequiredStudents;
    }

    public int getProjectTimeRequired() {
        return projectTimeRequired;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public void setProjectTimeRequired(int projectTimeRequired) {
        this.projectTimeRequired = projectTimeRequired;
    }

    public void setProjectRequiredStudents(int projectRequiredStudents) {
        this.projectRequiredStudents = projectRequiredStudents;
    }
}
