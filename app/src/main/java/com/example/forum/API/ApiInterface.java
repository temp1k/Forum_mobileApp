package com.example.forum.API;

import com.example.forum.Models.Admins;
import com.example.forum.Models.Friends;
import com.example.forum.Models.Messages;
import com.example.forum.Models.TopicDiscussions;
import com.example.forum.Models.Users;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("users")
    Call<ArrayList<Users>> getUsers();

    @GET("users/{id}")
    Call<Users> getUserById(@Path("id") int id);

    @GET("users/{login}/{password}")
    Call<Users> getAutorizationUser(@Path("login") String login, @Path("password") String password);

    @POST("Users/")
    Call<Users> addUser(@Body Users users);

    @POST("Admins/")
    Call<Admins> addAdmin(@Body Admins admin);

    @GET("TopicDiscussions")
    Call<ArrayList<TopicDiscussions>> getTopics();

    @GET("TopicDiscussions/{id}")
    Call<TopicDiscussions> getTopicsById(@Path("id") int id);

    @GET("Friends/{id}/{id2}")
    Call<Friends> checkFriends(@Path("id") int id, @Path("id2") int id2);

    @GET("Friends/{id}")
    Call<ArrayList<Friends>> getFriends(@Path("id") int id);

    @GET("Messages/count/{id}")
    Call<Integer> getCountMessages(@Path("id") int id);

    @GET("Messages/topic/{id}")
    Call<ArrayList<Messages>> getMessagesOfTopic(@Path("id") int id);

    @POST("Messages/")
    Call<Messages> sendMessage(@Body Messages message);

    @GET("TopicDiscussions/count/{id}")
    Call<Integer> getCountTopics(@Path("id") int id);

    @GET("TopicDiscussions/user/{id}")
    Call<ArrayList<TopicDiscussions>> getTopicsUser(@Path("id") int id);

    @POST("TopicDiscussions/")
    Call<TopicDiscussions> addTopic(@Body TopicDiscussions topic);
}
