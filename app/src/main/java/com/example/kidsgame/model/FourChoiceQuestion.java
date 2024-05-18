package com.example.kidsgame.model;

import com.google.gson.annotations.SerializedName;

public class FourChoiceQuestion {
    @SerializedName("question")
    private String question;

    @SerializedName("questionString")
    private String questionString;

    @SerializedName("choiceA")
    private String choiceA;

    @SerializedName("choiceB")
    private String choiceB;

    @SerializedName("choiceC")
    private String choiceC;

    @SerializedName("choiceD")
    private String choiceD;

    @SerializedName("correctAnswer")
    private String correctAnswer;

    // Getters and setters

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestionString() {
        return questionString;
    }

    public void setQuestionString(String questionString) {
        this.questionString = questionString;
    }

    public String getChoiceA() {
        return choiceA;
    }

    public void setChoiceA(String choiceA) {
        this.choiceA = choiceA;
    }

    public String getChoiceB() {
        return choiceB;
    }

    public void setChoiceB(String choiceB) {
        this.choiceB = choiceB;
    }

    public String getChoiceC() {
        return choiceC;
    }

    public void setChoiceC(String choiceC) {
        this.choiceC = choiceC;
    }

    public String getChoiceD() {
        return choiceD;
    }

    public void setChoiceD(String choiceD) {
        this.choiceD = choiceD;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
