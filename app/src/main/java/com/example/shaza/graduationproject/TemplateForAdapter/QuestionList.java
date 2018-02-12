package com.example.shaza.graduationproject.TemplateForAdapter;

/**
 * Created by Mariam on 2/11/2018.
 */

public class QuestionList {

    private String Question, Categoery,Time;

    public QuestionList(String question, String categoery, String time) {
        Question = question;
        Categoery = categoery;
        Time = time;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public void setCategoery(String categoery) {
        Categoery = categoery;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getQuestion() {
        return Question;
    }

    public String getCategoery() {
        return Categoery;
    }

    public String getTime() {
        return Time;
    }
}
