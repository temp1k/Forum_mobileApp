package com.example.forum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forum.API.ApiInterface;
import com.example.forum.API.RequestBuilder;
import com.example.forum.Adapters.MessagesAdapter;
import com.example.forum.Models.Messages;
import com.example.forum.Models.TopicDiscussions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopicActivity extends AppCompatActivity {

    TextView tvNameTopic, tvDescription;
    RecyclerView rvMessages;
    EditText etText;
    Button btnSend;
    int userId = 0;
    int topicId;
    SharedPreferences settings;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        tvNameTopic = findViewById(R.id.tvNameTopic_topic);
        tvDescription = findViewById(R.id.tvDescriptionTopic_topic);
        rvMessages = findViewById(R.id.rvMessages);
        etText = findViewById(R.id.etTextMessage_topic);
        btnSend = findViewById(R.id.btnSend);

        apiInterface = RequestBuilder.buildRequest().create(ApiInterface.class);
        settings = getSharedPreferences(MainActivity.USER_PREFERENCES, Context.MODE_PRIVATE);

        userId = settings.getInt(MainActivity.USER_PREFERENCES_ID, -1);
        topicId = getIntent().getIntExtra("idTopic", 0);
        if (topicId <= 0){
            Toast.makeText(this, "Данной темы не существует", Toast.LENGTH_LONG).show();

            returnToMainActivity();
        }

        updateListMessages();


    }

    private void returnToMainActivity(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private void fillMessages(int idTopic){
        Call<ArrayList<Messages>> getMessages = apiInterface.getMessagesOfTopic(idTopic);

        getMessages.enqueue(new Callback<ArrayList<Messages>>() {
            @Override
            public void onResponse(Call<ArrayList<Messages>> call, Response<ArrayList<Messages>> response) {
                if (response.isSuccessful()){
                    ArrayList<Messages> messages = response.body();

                    MessagesAdapter adapter = new MessagesAdapter(getApplicationContext(), messages, userId);

                    rvMessages.setHasFixedSize(true);
                    rvMessages.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    rvMessages.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Messages>> call, Throwable t) {

            }
        });
    }

    public void sendMessage(View view) {
        String text = etText.getText().toString();

        if (text != null && !text.equals("")){
            Messages message = new Messages(0, text, false, 0, userId, topicId);

            Call<Messages> sendMsmg = apiInterface.sendMessage(message);
            sendMsmg.enqueue(new Callback<Messages>() {
                @Override
                public void onResponse(Call<Messages> call, Response<Messages> response) {
                    if (response.isSuccessful()){
                        etText.setText(null);
                        updateListMessages();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),
                                "Ошибка отправки сообщения. Код ошибки " + response.code(),
                                Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Messages> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void updateListMessages(){
        Call<TopicDiscussions> getTopicById = apiInterface.getTopicsById(topicId);
        getTopicById.enqueue(new Callback<TopicDiscussions>() {
            @Override
            public void onResponse(Call<TopicDiscussions> call, Response<TopicDiscussions> response) {
                if (response.isSuccessful()){
                    TopicDiscussions topic = response.body();

                    tvNameTopic.setText(topic.getName());
                    tvDescription.setText(topic.getDescription());

                    fillMessages(topic.getIdTopic());
                }
                else{
                    Toast.makeText(getApplicationContext(), "Невозможно подключение к серверу. Ошибка " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<TopicDiscussions> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}