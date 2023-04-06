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
import com.example.forum.MainActivity;
import com.example.forum.Models.Friends;
import com.example.forum.Models.Users;
import com.example.forum.ProfileActivity;
import com.example.forum.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder>{
    private Context context;
    private ArrayList<Users> users;
    private ApiInterface apiInterface;
    private int id;

    public UsersAdapter(Context context, ArrayList<Users> users, int id) {
        this.context = context;
        this.users = users;
        this.id = id;
        apiInterface = RequestBuilder.buildRequest().create(ApiInterface.class);
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);
        return new UsersViewHolder(view);
    }


    public void onBindViewHolder(@NonNull UsersViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tvLogin.setText(users.get(position).getLogin());
        holder.tvFIO.setText(users.get(position).getFio());

        Call<Friends> isFriend = apiInterface.checkFriends(id, users.get(position).getIdUser());

        isFriend.enqueue(new Callback<Friends>() {
            @Override
            public void onResponse(Call<Friends> call, Response<Friends> response) {
                if (response.isSuccessful()){
                    holder.tvIsFriend.setText("Друг");
                }
                else{
                    holder.tvIsFriend.setText(null);
                }
            }

            @Override
            public void onFailure(Call<Friends> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProfileActivity.class);

                intent.putExtra("idUser", users.get(position).getIdUser());

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public final static class UsersViewHolder extends RecyclerView.ViewHolder {

        TextView tvLogin, tvFIO, tvIsFriend;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);

            tvLogin = itemView.findViewById(R.id.tvLoginUser_friends);
            tvFIO = itemView.findViewById(R.id.tvFIO_friends);
            tvIsFriend = itemView.findViewById(R.id.tvIsFriend_friends);
        }
    }
}


