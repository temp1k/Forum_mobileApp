package com.example.forum.Models;

public class Messages {
    private int idMessage;
    private String textMessage;
    private boolean statusChange;
    private int rating;
    private int userId;
    private int topicId;

    public Messages(int idMessage, String textMessage, boolean statusChange, int rating, int userId, int topicId) {
        this.idMessage = idMessage;
        this.textMessage = textMessage;
        this.statusChange = statusChange;
        this.rating = rating;
        this.userId = userId;
        this.topicId = topicId;
    }

    public int getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(int idMessage) {
        this.idMessage = idMessage;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public boolean isStatusChange() {
        return statusChange;
    }

    public void setStatusChange(boolean statusChange) {
        this.statusChange = statusChange;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }
}
