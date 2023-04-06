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
import com.example.forum.ProfileActivity;
import com.example.forum.R;
import com.example.forum.TopicActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder> {
    private Context context;
    private ArrayList<Friends> friends;
    private ApiInterface apiInterface;

    public FriendsAdapter(Context context, ArrayList<Friends> friends) {
        this.context = context;
        this.friends = friends;
        this.apiInterface = RequestBuilder.buildRequest().create(ApiInterface.class);
    }

    @NonNull
    @Override
    public FriendsAdapter.FriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.friend_item, parent, false);
        return new FriendsAdapter.FriendsViewHolder(view);
    }


    public void onBindViewHolder(@NonNull FriendsAdapter.FriendsViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Call<Users> getUser = apiInterface.getUserById(friends.get(position).getFriendId());

        getUser.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                Users user = new Users(0, "Неизвестно", null, "Неизвестно", null, null, false);
                if (response.isSuccessful()){
                    user = response.body();
                }
                holder.tvLogin.setText(user.getLogin());
                holder.tvFio.setText("("+user.getFio()+")");
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProfileActivity.class);

                intent.putExtra("idUser", friends.get(position).getFriendId());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public final static class FriendsViewHolder extends RecyclerView.ViewHolder {
        TextView tvLogin, tvFio;

        public FriendsViewHolder(@NonNull View itemView) {
            super(itemView);

            tvLogin = itemView.findViewById(R.id.tvLoginFriend_yourprofile);
            tvFio = itemView.findViewById(R.id.tvFioFriend_yourprofile);
        }
    }
}
