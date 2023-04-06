package com.example.forum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.forum.API.ApiInterface;
import com.example.forum.API.RequestBuilder;
import com.example.forum.Models.Admins;
import com.example.forum.Models.Users;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration extends AppCompatActivity {

    private EditText etLogin, etPassword, etRepeatPassword, etFIO;
    private Button btnReg;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etLogin = findViewById(R.id.etLogin_reg);
        etPassword = findViewById(R.id.etPasswordReg);
        etRepeatPassword = findViewById(R.id.etRepeatPassword);
        btnReg = findViewById(R.id.btnReg);
        etFIO = findViewById(R.id.etFIO_reg);

        apiInterface = RequestBuilder.buildRequest().create(ApiInterface.class);
    }

    public void registration(View view) {
        String login = etLogin.getText().toString();
        String password = etPassword.getText().toString();
        String repeatPassword = etRepeatPassword.getText().toString();
        String FIO = etFIO.getText().toString();
        String dateTime = getDateNow();

        if (login.length() >= 6){
            if (password.length() >= 8){
                if (password.equals(repeatPassword)){
                    Users newUser = new Users(0, login, password, FIO, null, dateTime, false);

                    Call<Users> addUser = apiInterface.addUser(newUser);

                    addUser.enqueue(new Callback<Users>() {
                        @Override
                        public void onResponse(Call<Users> call, Response<Users> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Вы успешно зарегестрировались", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(getApplicationContext(), Autorization.class);

                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Неверно введены данные " + dateTime, Toast.LENGTH_LONG).show();
                                //clearPasswords();
                            }
                        }

                        @Override
                        public void onFailure(Call<Users> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                            clearPasswords();
                        }
                    });
                }
            }
        }
    }

    private void clearPasswords(){
        etPassword.setText(null);
        etRepeatPassword.setText(null);
    }

    public static String getDateNow(){
        Date nowDate = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        return formatForDateNow.format(nowDate);
    }
}