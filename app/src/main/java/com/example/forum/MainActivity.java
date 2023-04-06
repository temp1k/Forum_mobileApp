package com.example.forum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.forum.API.ApiInterface;
import com.example.forum.API.RequestBuilder;
import com.example.forum.Models.Users;
import com.example.forum.fragments.FriendsFragment;
import com.example.forum.fragments.TopicsFragment;
import com.example.forum.fragments.YourProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String USER_PREFERENCES = "usersettings";
    public static final String USER_PREFERENCES_ID = "id";
    SharedPreferences userSettings;

    private FrameLayout frameLayout;
    private BottomNavigationView bottomNavigationView;

    private ApiInterface apiInterface;

    private Fragment mainFragment = new TopicsFragment();
    private Fragment friendsFragment = new FriendsFragment();
    private Fragment yourProfileFragment = new YourProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout = findViewById(R.id.fragment_layout);
        bottomNavigationView = findViewById(R.id.bottom_nav);

        userSettings = getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);


        // Получение id текущего пользователя
        int id = getIntent().getIntExtra("idUser", 0);
        if (id <= 0){
            userSettings = getSharedPreferences(MainActivity.USER_PREFERENCES, Context.MODE_PRIVATE);
            id = userSettings.getInt(MainActivity.USER_PREFERENCES_ID, -1);
        }
        SharedPreferences.Editor editor = userSettings.edit();
        editor.putInt(USER_PREFERENCES_ID, id);
        editor.apply();



        apiInterface = RequestBuilder.buildRequest().create(ApiInterface.class);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int idItem =item.getItemId();
                switch(idItem){
                    case R.id.main:
                        setFragment(mainFragment);
                        return true;
                    case R.id.friends:
                        setFragment(friendsFragment);
                        return true;
                    case R.id.profile:
                        setFragment(yourProfileFragment);
                        return true;
                }
                return false;
            }
        });

        setFragment(mainFragment);

    }

    private void setFragment(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_layout, fragment);
        ft.commit();
    }
}