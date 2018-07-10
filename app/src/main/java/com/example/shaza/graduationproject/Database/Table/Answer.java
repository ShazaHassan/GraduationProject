package com.example.shaza.graduationproject.Database.Table;

public class Answer {
    private String Answer, creatorID, time, AnswerID, userName;

    public Answer(String answer, String creatorID, String time, String answerID) {
        Answer = answer;
        this.creatorID = creatorID;
        this.time = time;
        AnswerID = answerID;
    }

    public Answer() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public String getAnswerID() {
        return AnswerID;
    }

    public void setAnswerID(String answerID) {
        AnswerID = answerID;
    }

    public String getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(String creator) {
        this.creatorID = creator;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
