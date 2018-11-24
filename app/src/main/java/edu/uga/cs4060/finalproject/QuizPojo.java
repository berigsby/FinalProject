package edu.uga.cs4060.finalproject;

import com.google.firebase.database.Exclude;

public class QuizPojo extends FirebasePojo {
    private String quizId;
    private String classId;
    private String title;
    private String desc;

    @SuppressWarnings("unused")
    public QuizPojo() {
        classId = "";
        quizId = "";
        title = "";
        desc = "";
    }

    public QuizPojo(String quizId, String classId, String title, String desc) {
        this.classId = classId;
        this.quizId = quizId;
        this.title = title;
        this.desc = desc;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
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
        return "quiz";
    }

    @Exclude
    @Override
    public String getId() {
        return getQuizId();
    }

    @Exclude
    @Override
    public void setId(String quizId) {
        setQuizId(quizId);
    }
}
