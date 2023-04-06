package com.example.forum.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forum.API.ApiInterface;
import com.example.forum.API.RequestBuilder;
import com.example.forum.Adapters.FriendsAdapter;
import com.example.forum.Adapters.TopicAdapterShort;
import com.example.forum.MainActivity;
import com.example.forum.Models.Friends;
import com.example.forum.Models.TopicDiscussions;
import com.example.forum.Models.Users;
import com.example.forum.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YourProfileFragment extends Fragment {

    RecyclerView rvFriends, rvTopics;
    TextView tvLoginUser, tvFioUser, tvCountMessages, tvCountTopics;

    ApiInterface apiInterface;
    SharedPreferences userSettings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_your_profile, container, false);

        rvFriends = view.findViewById(R.id.rvFriends_you);
        rvTopics = view.findViewById(R.id.rvTopics_you);
        tvLoginUser = view.findViewById(R.id.tvLoginUser_main);
        tvFioUser = view.findViewById(R.id.tvNameUser_main);
        tvCountMessages = view.findViewById(R.id.tvCountMessages);
        tvCountTopics = view.findViewById(R.id.tvCountTopics);

        apiInterface = RequestBuilder.buildRequest().create(ApiInterface.class);

        userSettings = getContext().getSharedPreferences(MainActivity.USER_PREFERENCES, Context.MODE_PRIVATE);
        int id = userSettings.getInt(MainActivity.USER_PREFERENCES_ID, -1);

        Call<Users> getUser = apiInterface.getUserById(id);
        getUser.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if (response.isSuccessful()){
                    Users user = response.body();

                    tvLoginUser.setText(user.getLogin());
                    tvFioUser.setText(user.getFio());
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

            }
        });

        Call<ArrayList<Friends>> getFriends = apiInterface.getFriends(id);
        getFriends.enqueue(new Callback<ArrayList<Friends>>() {
            @Override
            public void onResponse(Call<ArrayList<Friends>> call, Response<ArrayList<Friends>> response) {
                if (response.isSuccessful()){
                    ArrayList<Friends> friends = response.body();
                    rvFriends.setLayoutManager(new LinearLayoutManager(getContext()));
                    rvFriends.setHasFixedSize(true);

                    FriendsAdapter adapter = new FriendsAdapter(getContext(), friends);

                    rvFriends.setAdapter(adapter);
                }
                else{
                    Toast.makeText(getContext(), "Ошибка подключения к серверу. Проверьте подключение к интернету", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Friends>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Call<Integer> getCountMessages = apiInterface.getCountMessages(id);
        getCountMessages.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()){
                    tvCountMessages.setText(response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        Call<Integer> getCountTopics = apiInterface.getCountTopics(id);
        getCountTopics.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()){
                    tvCountTopics.setText(response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        Call<ArrayList<TopicDiscussions>> getTopics = apiInterface.getTopicsUser(id);
        getTopics.enqueue(new Callback<ArrayList<TopicDiscussions>>() {
            @Override
            public void onResponse(Call<ArrayList<TopicDiscussions>> call, Response<ArrayList<TopicDiscussions>> response) {
                if (response.isSuccessful()){
                    ArrayList<TopicDiscussions> topics = response.body();

                    TopicAdapterShort adapter = new TopicAdapterShort(getView().getContext(), topics);

                    rvTopics.setLayoutManager(new LinearLayoutManager(getContext()));
                    rvTopics.setHasFixedSize(true);
                    rvTopics.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<TopicDiscussions>> call, Throwable t) {

            }
        });

        return view;
    }
}