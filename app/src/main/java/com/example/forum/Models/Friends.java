package com.example.forum.Models;

public class Friends {
    private int idFriends;
    private int userId;
    private int friendId;
    private int relationId;
    private Users friendNavigation;

    public Friends(int idFriends, int userId, int friendId, int relationId) {
        this.idFriends = idFriends;
        this.userId = userId;
        this.friendId = friendId;
        this.relationId = relationId;
        this.friendNavigation = null;
    }

    public Users getFriendNavigation() {
        return friendNavigation;
    }

    public void setFriendNavigation(Users friendNavigation) {
        this.friendNavigation = friendNavigation;
    }

    public int getIdFriends() {
        return idFriends;
    }

    public void setIdFriends(int idFriends) {
        this.idFriends = idFriends;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public int getRelationId() {
        return relationId;
    }

    public void setRelationId(int relationId) {
        this.relationId = relationId;
    }
}
