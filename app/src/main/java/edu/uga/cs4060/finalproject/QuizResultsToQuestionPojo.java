package edu.uga.cs4060.finalproject;

public class QuizResultsToQuestionPojo {
    private String quizResultsToQuestionId;
    private String quizId;
    private String questionId;
    private int userAnswerChoice;

    @SuppressWarnings("unused")
    public QuizResultsToQuestionPojo() {
    }

    public QuizResultsToQuestionPojo(String quizResultsToQuestionId, String quizId, String questionId, int userAnswerChoice) {
        this.quizResultsToQuestionId = quizResultsToQuestionId;
        this.quizId = quizId;
        this.questionId = questionId;
        this.userAnswerChoice = userAnswerChoice;
    }

    public String getQuizResultsToQuestionId() {
        return quizResultsToQuestionId;
    }

    public void setQuizResultsToQuestionId(String quizResultsToQuestionId) {
        this.quizResultsToQuestionId = quizResultsToQuestionId;
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
}
