package com.example.forum.Models;

import java.util.Date;

public class Users {
    private int idUser;
    private String login;
    private String password;
    private String fio;
    private String email;
    private String dateRegistration;
    private boolean block;

    public Users(int idUser, String login, String password, String fio, String email, String dateRegistration, boolean block) {
        this.idUser = idUser;
        this.login = login;
        this.password = password;
        this.fio = fio;
        this.email = email;
        this.dateRegistration = dateRegistration;
        this.block = block;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateRegistration() {
        return dateRegistration;
    }

    public void setDateRegistration(String dateRegistration) {
        this.dateRegistration = dateRegistration;
    }

    public boolean isBlock() {
        return block;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }
}
