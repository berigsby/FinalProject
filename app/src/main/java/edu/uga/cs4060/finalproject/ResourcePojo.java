package edu.uga.cs4060.finalproject;

import com.google.firebase.database.Exclude;

public class ResourcePojo extends FirebasePojo{
    private String resourceId;
    private String classId;
    private String title;
    private String text;

    @SuppressWarnings("unused")
    public ResourcePojo() {
        resourceId = "";
        classId = "";
        title = "";
        text = "";
    }

    public ResourcePojo(String resourceId, String classId, String title, String text) {
        this.resourceId = resourceId;
        this.classId = classId;
        this.title = title;
        this.text = text;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Exclude
    @Override
    public String getDatabaseKey(){
        return "resource";
    }

    @Exclude
    @Override
    public String getId(){
        return getResourceId();
    }

    @Exclude
    @Override
    public void setId(String resourceId){
        setResourceId(resourceId);
    }
}
