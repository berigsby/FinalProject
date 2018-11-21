package edu.uga.cs4060.finalproject;

public class ResourcePojo {
    private String resourceId;
    private String title;
    private String text;

    @SuppressWarnings("unused")
    public ResourcePojo() {
    }

    public ResourcePojo(String resourceId, String title, String text) {
        this.resourceId = resourceId;
        this.title = title;
        this.text = text;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
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
}
