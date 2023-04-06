package com.example.forum.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import com.example.forum.Models.TopicDiscussions;
import com.example.forum.Models.Users;
import com.example.forum.R;
import com.example.forum.TopicActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicsViewHolder> {

    private Context context;
    private ArrayList<TopicDiscussions> topics;
    private ApiInterface apiInterface;

    public TopicAdapter(Context context, ArrayList<TopicDiscussions> topics) {
        this.context = context;
        this.topics = topics;
        this.apiInterface = RequestBuilder.buildRequest().create(ApiInterface.class);
    }

    @NonNull
    @Override
    public TopicAdapter.TopicsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.topic_item, parent, false);
        return new TopicAdapter.TopicsViewHolder(view);
    }


    public void onBindViewHolder(@NonNull TopicAdapter.TopicsViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tvNameTopic.setText(topics.get(position).getName());
        holder.tvDescription.setText(topics.get(position).getDescription());

        int idUser = topics.get(position).getUserId();

        Call<Users> getUser = apiInterface.getUserById(idUser);

        getUser.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if (response.isSuccessful()){
                    holder.tvLoginUser.setText(response.body().getLogin());
                }
                else{
                    holder.tvLoginUser.setText("Неизвестно");
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast.makeText(context.getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TopicActivity.class);

                intent.putExtra("idTopic", topics.get(position).getIdTopic());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

    public final static class TopicsViewHolder extends RecyclerView.ViewHolder {
        TextView tvNameTopic, tvLoginUser, tvDescription;

        public TopicsViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNameTopic = itemView.findViewById(R.id.tvNameTopic_topics);
            tvLoginUser = itemView.findViewById(R.id.tvLoginUser_topics);
            tvDescription = itemView.findViewById(R.id.tvDescriptionTopic_topics);
        }
    }
}
