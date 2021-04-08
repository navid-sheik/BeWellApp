package com.example.bewell.models;

public class MoodQuestion {
    private String question;
    private boolean answer;
    private TypeQuestion typeQuestion;
    private boolean setQuestionAnswer;

    public MoodQuestion(String question,TypeQuestion typeQuestion) {
        this.question = question;
        this.typeQuestion = typeQuestion;
        this.answer = false;
        this.setQuestionAnswer = false;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    public TypeQuestion getTypeQuestion() {
        return typeQuestion;
    }

    public void setTypeQuestion(TypeQuestion typeQuestion) {
        this.typeQuestion = typeQuestion;
    }

    public boolean isSetQuestionAnswer() {
        return setQuestionAnswer;
    }

    public void setSetQuestionAnswer(boolean setQuestionAnswer) {
        this.setQuestionAnswer = setQuestionAnswer;
    }
}
