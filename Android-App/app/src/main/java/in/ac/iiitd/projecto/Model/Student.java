package in.ac.iiitd.projecto.Model;

import java.util.ArrayList;

public class Student {

    public String studentName, studentDegree, studentStream, studentResumeLink;
    public ArrayList<String> arrayList;
    public Student(){}

    public void setArrayList(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }

    public ArrayList<String> getArrayList() {
        return arrayList;
    }

    public String getStudentResumeLink() {
        return studentResumeLink;
    }

    public void setStudentResumeLink(String studentResumeLink) {
        this.studentResumeLink = studentResumeLink;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentStream() {
        return studentStream;
    }

    public void setStudentStream(String studentStream) {
        this.studentStream = studentStream;
    }

    public String getStudentDegree() {
        return studentDegree;
    }

    public void setStudentDegree(String studentDegree) {
        this.studentDegree = studentDegree;
    }
}
