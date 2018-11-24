package edu.uga.cs4060.finalproject;

import com.google.firebase.database.Exclude;

public class TeacherPojo extends FirebasePojo{
    private String name;
    private String teacherId;

    @SuppressWarnings("unused")
    TeacherPojo(){
        name = "";
        teacherId = "";
    }

    TeacherPojo(String name, String teacherId){
        this.name = name;
        this.teacherId = teacherId;
    }

    public String getName(){
        return this.name;
    }

    public String getTeacherId(){
        return this.teacherId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    @Exclude
    @Override
    public String getDatabaseKey(){
        return "teacher";
    }

    @Exclude
    @Override
    public String getId(){
        return getTeacherId();
    }

    @Exclude
    @Override
    public void setId(String teacherId){
        setTeacherId(teacherId);
    }

}
