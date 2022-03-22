package in.ac.iiitd.projecto.Model;

public class ProjectItem {

    private String projectTitle, projectAdvisorName;
    public ProjectItem(){

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

}
