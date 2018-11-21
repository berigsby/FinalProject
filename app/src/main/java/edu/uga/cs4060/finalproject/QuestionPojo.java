package edu.uga.cs4060.finalproject;

public class QuestionPojo {
    private String questionId;
    private String questionText;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private int correctAnswerChoice;

    @SuppressWarnings("unused")
    public QuestionPojo() {
    }

    public QuestionPojo(String questionId, String questionText, String answer1, String answer2, String answer3, String answer4, int correctAnswerChoice) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.correctAnswerChoice = correctAnswerChoice;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public int getCorrectAnswerChoice() {
        return correctAnswerChoice;
    }

    public void setCorrectAnswerChoice(int correctAnswerChoice) {
        this.correctAnswerChoice = correctAnswerChoice;
    }
}
