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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.forum.API.ApiInterface;
import com.example.forum.API.RequestBuilder;
import com.example.forum.Adapters.UsersAdapter;
import com.example.forum.MainActivity;
import com.example.forum.Models.Users;
import com.example.forum.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendsFragment extends Fragment {

    private EditText etFindUser;
    private Button btnFindUser;
    private RecyclerView rwUsers;
    SharedPreferences userSettings;

    private ApiInterface apiInterface;

    private ArrayList<Users> users = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friends, container, false);

        etFindUser = view.findViewById(R.id.etFindUser_main);
        btnFindUser = view.findViewById(R.id.btnFindUser_main);
        rwUsers = view.findViewById(R.id.rwUsers_main);

        userSettings = getContext().getSharedPreferences(MainActivity.USER_PREFERENCES, Context.MODE_PRIVATE);

        int id = userSettings.getInt(MainActivity.USER_PREFERENCES_ID, -1);



        apiInterface = RequestBuilder.buildRequest().create(ApiInterface.class);

        Call<ArrayList<Users>> getUsers = apiInterface.getUsers();

        getUsers.enqueue(new Callback<ArrayList<Users>>() {
            @Override
            public void onResponse(Call<ArrayList<Users>> call, Response<ArrayList<Users>> response) {
                if (response.isSuccessful()){
                    users = response.body();

                    UsersAdapter adapter = new UsersAdapter(getContext(), users, id);

                    rwUsers.setHasFixedSize(true);
                    rwUsers.setLayoutManager(new LinearLayoutManager(getContext()));
                    rwUsers.setAdapter(adapter);
                }
                else{
                    Toast.makeText(getContext(), "Ошибка подключения к серверу. Проверьте подключение к интернету", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Users>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}