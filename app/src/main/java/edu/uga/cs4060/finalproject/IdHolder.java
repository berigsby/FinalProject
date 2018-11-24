package edu.uga.cs4060.finalproject;

public class IdHolder {
    private String classId;
    private String resourceId;
    private String teacherId;
    private String quizId;
    private String studentId;
    private String questionId;
    private String quizResultsToQuestionId;

    @SuppressWarnings("unused")
    public IdHolder() {
        classId = "";
        resourceId = "";
        teacherId = "";
        quizId = "";
        studentId = "";
        questionId = "";
        quizResultsToQuestionId = "";
    }

    public IdHolder(String classId, String resourceId, String teacherId, String quizId,
                    String studentId, String questionId, String quizResultsToQuestionId) {
        this.classId = classId;
        this.resourceId = resourceId;
        this.teacherId = teacherId;
        this.quizId = quizId;
        this.studentId = studentId;
        this.questionId = questionId;
        this.quizResultsToQuestionId = quizResultsToQuestionId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuizResultsToQuestionId() {
        return quizResultsToQuestionId;
    }

    public void setQuizResultsToQuestionId(String quizResultsToQuestionId) {
        this.quizResultsToQuestionId = quizResultsToQuestionId;
    }
}
