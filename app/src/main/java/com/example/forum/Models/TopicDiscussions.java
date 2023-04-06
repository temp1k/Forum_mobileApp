package com.example.forum.Models;

import java.util.ArrayList;

public class TopicDiscussions {
    private int idTopic;
    private String name, description, dateCreation;
    private int userId;

    private Users user;
    private ArrayList<Messages> messages;

    public TopicDiscussions(int idTopic, String name, String description, String dateCreation, int userId) {
        this.idTopic = idTopic;
        this.name = name;
        this.description = description;
        this.dateCreation = dateCreation;
        this.userId = userId;
        this.user = null;
        this.messages = null;
    }

    public ArrayList<Messages> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Messages> messages) {
        this.messages = messages;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public int getIdTopic() {
        return idTopic;
    }

    public void setIdTopic(int idTopic) {
        this.idTopic = idTopic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
