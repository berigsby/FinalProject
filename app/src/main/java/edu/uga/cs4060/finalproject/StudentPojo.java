package edu.uga.cs4060.finalproject;

import com.google.firebase.database.Exclude;

public class StudentPojo extends FirebasePojo{
    private String studentId;
    private String name;

    @SuppressWarnings("unused")
    public StudentPojo() {
        studentId = "";
        name = "";
    }

    public StudentPojo(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Exclude
    @Override
    public String getDatabaseKey(){
        return "student";
    }

    @Exclude
    @Override
    public String getId(){
        return getStudentId();
    }

    @Exclude
    @Override
    public void setId(String studentId){
        setStudentId(studentId);
    }
}
