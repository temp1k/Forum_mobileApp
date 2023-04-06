package com.example.forum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forum.API.ApiInterface;
import com.example.forum.API.RequestBuilder;
import com.example.forum.Models.Users;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Autorization extends AppCompatActivity {

    private EditText etLogin, etPassword;
    private Button btnEntry;
    private ApiInterface apiInterface;
    TextView linkReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autorization);

        etLogin = findViewById(R.id.etLogin_autorization);
        etPassword = findViewById(R.id.etPassword_autorization);
        btnEntry = findViewById(R.id.btnEntry);
        linkReg = findViewById(R.id.linkRegistration);

        apiInterface = RequestBuilder.buildRequest().create(ApiInterface.class);

        linkReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Registration.class);

                startActivity(intent);
            }
        });
    }

    public void autorize(View view) {
        String login = etLogin.getText().toString();
        String password = etPassword.getText().toString();

        Call<Users> getUser = apiInterface.getAutorizationUser(login, password);

        getUser.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if (response.isSuccessful()) {
                    Users user = response.body();
                    if (!user.isBlock()){
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("idUser", user.getIdUser());
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Данный аккаунт заблокирован.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Неверно введен логин или пароль", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}