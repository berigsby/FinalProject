package edu.uga.cs4060.finalproject;

import com.google.firebase.database.Exclude;

public class QuizResultsToQuestionPojo extends FirebasePojo{
    private String quizResultsToQuestionId;
    private String quizId;
    private String questionId;
    private String studentId;
    private int userAnswerChoice;

    @SuppressWarnings("unused")
    public QuizResultsToQuestionPojo() {
    }

    public QuizResultsToQuestionPojo(String quizResultsToQuestionId, String quizId, String questionId, String studentId, int userAnswerChoice) {
        this.quizResultsToQuestionId = quizResultsToQuestionId;
        this.quizId = quizId;
        this.questionId = questionId;
        this.userAnswerChoice = userAnswerChoice;
        this.studentId = studentId;
    }

    public String getQuizResultsToQuestionId() {
        return quizResultsToQuestionId;
    }

    public void setQuizResultsToQuestionId(String quizResultsToQuestionId) {
        this.quizResultsToQuestionId = quizResultsToQuestionId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public int getUserAnswerChoice() {
        return userAnswerChoice;
    }

    public void setUserAnswerChoice(int userAnswerChoice) {
        this.userAnswerChoice = userAnswerChoice;
    }

    @Exclude
    @Override
    public String getDatabaseKey(){
        return "quizResultsToQuestion";
    }

    @Exclude
    @Override
    public String getId(){
        return getQuizResultsToQuestionId();
    }

    @Exclude
    @Override
    public void setId(String quizResultsToQuestionId){
        setQuizResultsToQuestionId(quizResultsToQuestionId);
    }
}
