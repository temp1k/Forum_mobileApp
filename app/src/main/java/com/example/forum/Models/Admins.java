package com.example.forum.Models;

public class Admins {
    private int idAdmin, userId;
    private Users user;

    public Admins(int idAdmin, int userId) {
        this.idAdmin = idAdmin;
        this.userId = userId;
        this.user = null;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
