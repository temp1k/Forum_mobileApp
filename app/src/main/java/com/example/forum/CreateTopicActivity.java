package com.example.forum;

import static com.example.forum.MainActivity.USER_PREFERENCES;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.forum.API.ApiInterface;
import com.example.forum.API.RequestBuilder;
import com.example.forum.Models.TopicDiscussions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateTopicActivity extends AppCompatActivity {

    ImageButton back;
    EditText etName, etDescription;
    int id;
    SharedPreferences userSettings;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_topic);

        back = findViewById(R.id.back_create);
        etName = findViewById(R.id.etNameTopic_create);
        etDescription = findViewById(R.id.etDescription_create);

        apiInterface = RequestBuilder.buildRequest().create(ApiInterface.class);

        userSettings = getSharedPreferences(MainActivity.USER_PREFERENCES, Context.MODE_PRIVATE);
        userSettings = getSharedPreferences(MainActivity.USER_PREFERENCES, Context.MODE_PRIVATE);
        id = userSettings.getInt(MainActivity.USER_PREFERENCES_ID, -1);
    }

    public void onClickBack(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onClickCreatTopic(View view) {
        String name = etName.getText().toString();
        String description = etDescription.getText().toString();

        if (name != null){
            TopicDiscussions topic = new TopicDiscussions(0, name, description, Registration.getDateNow(), id);

            Call<TopicDiscussions> addTopic = apiInterface.addTopic(topic);

            addTopic.enqueue(new Callback<TopicDiscussions>() {
                @Override
                public void onResponse(Call<TopicDiscussions> call, Response<TopicDiscussions> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(CreateTopicActivity.this, "Вы усшено создали новую тему", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(CreateTopicActivity.this,
                                "Ошибка подключения к серверу. Повторите попытку позже. Ошибка: " + response.code(),
                                Toast.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onFailure(Call<TopicDiscussions> call, Throwable t) {
                    Toast.makeText(CreateTopicActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
        else{
            Toast.makeText(this, "Название темы не может быть пустым", Toast.LENGTH_LONG).show();
        }
    }
}