package in.ac.iiitd.projecto.Model;

public class StudentItem {
    private String name,degree,stream,email;
    public StudentItem(){}
    public StudentItem(String n,String s,String d,String e){
        this.name=n;
        this.stream=s;
        this.degree=d;
        this.email=e;
    }
    public String getName() {
        return name;
    }

    public String getDegree() {
        return degree;
    }

    public String getStream() {
        return stream;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }
}
