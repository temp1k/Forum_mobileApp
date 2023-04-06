package com.example.forum.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forum.API.ApiInterface;
import com.example.forum.API.RequestBuilder;
import com.example.forum.Adapters.TopicAdapter;
import com.example.forum.CreateTopicActivity;
import com.example.forum.MainActivity;
import com.example.forum.Models.TopicDiscussions;
import com.example.forum.Models.Users;
import com.example.forum.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopicsFragment extends Fragment {

    private TextView tvHello, linkCreateTopic;
    private EditText etFindTopic;
    private Button btnFindTopic;
    private RecyclerView rvTopics;
    private ApiInterface apiInterface;
    SharedPreferences userSettings;
    private Users user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_topics, container, false);

        tvHello = view.findViewById(R.id.hello_main);
        etFindTopic = view.findViewById(R.id.etFindTopic_main);
        btnFindTopic = view.findViewById(R.id.btnFindTopic);
        rvTopics = view.findViewById(R.id.rvTopics);
        linkCreateTopic = view.findViewById(R.id.linkCreateTopic);
        apiInterface = RequestBuilder.buildRequest().create(ApiInterface.class);

        userSettings = getContext().getSharedPreferences(MainActivity.USER_PREFERENCES, Context.MODE_PRIVATE);
        int id = userSettings.getInt(MainActivity.USER_PREFERENCES_ID, -1);

        Call<Users> getUserById = apiInterface.getUserById(id);

        getUserById.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if (response.isSuccessful()){
                    user = response.body();
                    tvHello.setText(String.format("Привет %s", user.getLogin()));
                }
                else{
                    Toast.makeText(view.getContext(), "Ошибка на стороне клиента", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast.makeText(view.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        Call<ArrayList<TopicDiscussions>> getTopics = apiInterface.getTopics();
        getTopics.enqueue(new Callback<ArrayList<TopicDiscussions>>() {
            @Override
            public void onResponse(Call<ArrayList<TopicDiscussions>> call, Response<ArrayList<TopicDiscussions>> response) {
                if (response.isSuccessful()){
                    ArrayList<TopicDiscussions> topics = response.body();

                    TopicAdapter adapter = new TopicAdapter(getView().getContext(),topics);
                    rvTopics.setLayoutManager(new LinearLayoutManager(getView().getContext()));
                    rvTopics.setHasFixedSize(true);
                    rvTopics.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<TopicDiscussions>> call, Throwable t) {
                Toast.makeText(getView().getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        linkCreateTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getView().getContext(), CreateTopicActivity.class);
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private ArrayList<TopicDiscussions> includeUsersInTopics(ArrayList<TopicDiscussions> topics){
        Users noneUser = new Users(0, null, null, null, null, null, false);
        for(int i = 0; i < topics.size(); i++){
            Call<Users> getUser = apiInterface.getUserById(topics.get(i).getUserId());

            int finalI = i;
            getUser.enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    if (response.isSuccessful()){
                        topics.get(finalI).setUser(response.body());
                    }
                    else{
                        topics.get(finalI).setUser(noneUser);
                    }

                }

                @Override
                public void onFailure(Call<Users> call, Throwable t) {
                    Toast.makeText(getView().getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
        return topics;
    }
}