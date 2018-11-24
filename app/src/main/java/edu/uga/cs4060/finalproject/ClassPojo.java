package edu.uga.cs4060.finalproject;

import com.google.firebase.database.Exclude;

public class ClassPojo extends FirebasePojo {
    private String classId;
    private String teacherId;
    private String title;
    private String desc;

    @SuppressWarnings("unused")
    public ClassPojo() {
        classId = "";
        teacherId = "";
        title = "";
        desc = "";
    }

    public ClassPojo(String classId, String teacherId, String title, String desc) {
        this.teacherId = teacherId;
        this.classId = classId;
        this.title = title;
        this.desc = desc;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Exclude
    @Override
    public String getDatabaseKey() {
        return "class";
    }

    @Exclude
    @Override
    public String getId() {
        return getClassId();
    }

    @Exclude
    @Override
    public void setId(String classId) {
        setClassId(classId);
    }
}
