package com.example.forum.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forum.API.ApiInterface;
import com.example.forum.API.RequestBuilder;
import com.example.forum.Models.Friends;
import com.example.forum.Models.Messages;
import com.example.forum.Models.Users;
import com.example.forum.R;
import com.example.forum.TopicActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder> {
    private Context context;
    private ArrayList<Messages> messages;
    private ApiInterface apiInterface;
    private int idUser;

    public MessagesAdapter(Context context, ArrayList<Messages> messages, int idUser) {
        this.context = context;
        this.messages = messages;
        this.apiInterface = RequestBuilder.buildRequest().create(ApiInterface.class);
        this.idUser = idUser;
    }

    @NonNull
    @Override
    public MessagesAdapter.MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_item, parent, false);
        return new MessagesAdapter.MessagesViewHolder(view);
    }


    public void onBindViewHolder(@NonNull MessagesAdapter.MessagesViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tvText.setText(messages.get(position).getTextMessage());

        Call<Users> getUser = apiInterface.getUserById(messages.get(position).getUserId());
        getUser.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if (response.isSuccessful()){
                    Users user = response.body();
                    holder.tvLogin.setText(user.getLogin());
                    if (user.getIdUser() == idUser){
                        holder.tvIsYou.setText("(Вы)");
                        Resources resources = holder.itemView.getResources();
                        int color = resources.getColor(R.color.you_message,  null);
                        holder.cvMessage.setCardBackgroundColor(color);
                    }
                }
                else{
                    holder.tvLogin.setText("Неизвестно");
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public final static class MessagesViewHolder extends RecyclerView.ViewHolder {
        TextView tvLogin, tvIsYou, tvText;
        CardView cvMessage;

        public MessagesViewHolder(@NonNull View itemView) {
            super(itemView);

            tvLogin = itemView.findViewById(R.id.tvLogin_Message);
            tvIsYou = itemView.findViewById(R.id.tvWhoseMessage);
            tvText = itemView.findViewById(R.id.tvTextMessage);
            cvMessage = itemView.findViewById(R.id.cvMessage);
        }
    }
}
