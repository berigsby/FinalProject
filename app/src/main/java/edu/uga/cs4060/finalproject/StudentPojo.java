package edu.uga.cs4060.finalproject;

public class StudentPojo {
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
}
