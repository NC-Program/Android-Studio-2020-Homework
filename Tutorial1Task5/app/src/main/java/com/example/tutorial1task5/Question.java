package com.example.tutorial1task5;

public class Question {
    private int questionNumber;
    private Boolean answer;

    public Question(int questionNumber, Boolean answer) {
        this.questionNumber = questionNumber;
        this.answer = answer;
    }

    public int GetQuestionNumber(){
        return questionNumber;
    }
    public Boolean GetAnswer(){
        return answer;
    }
}
