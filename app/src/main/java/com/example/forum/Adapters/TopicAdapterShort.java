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
import androidx.recyclerview.widget.RecyclerView;

import com.example.forum.API.ApiInterface;
import com.example.forum.API.RequestBuilder;
import com.example.forum.Models.Friends;
import com.example.forum.Models.TopicDiscussions;
import com.example.forum.Models.Users;
import com.example.forum.R;
import com.example.forum.TopicActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopicAdapterShort extends RecyclerView.Adapter<TopicAdapterShort.TopicsViewHolder> {

    private Context context;
    private ArrayList<TopicDiscussions> topics;

    public TopicAdapterShort(Context context, ArrayList<TopicDiscussions> topics) {
        this.context = context;
        this.topics = topics;
    }

    @NonNull
    @Override
    public TopicAdapterShort.TopicsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.topic_item_short, parent, false);
        return new TopicAdapterShort.TopicsViewHolder(view);
    }


    public void onBindViewHolder(@NonNull TopicAdapterShort.TopicsViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tvNameTopic.setText(topics.get(position).getName());

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
        TextView tvNameTopic;

        public TopicsViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNameTopic = itemView.findViewById(R.id.tvNameTopic_main);
        }
    }
}
