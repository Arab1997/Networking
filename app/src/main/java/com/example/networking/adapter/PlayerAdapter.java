package com.example.networking.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.networking.R;
import com.example.networking.model.Player;

import java.lang.reflect.Member;
import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Player> players;

    public PlayerAdapter(Context context, List<Player> players) {
        this.context = context;
        this.players = players;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ustom_layout, parent, false);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PlayerViewHolder){
        Player player = players.get(position);

            ImageView tennis_image = ((PlayerViewHolder) holder).tennis_image;
            TextView tennis_name = ((PlayerViewHolder) holder).tennis_name;
            TextView tennis_location = ((PlayerViewHolder) holder).tennis_location;

            tennis_name.setText(player.getName());
            tennis_location.setText(player.getCountry() + ", " + player.getCity());
            Glide.with(context).load(player.getImgURL()).into(tennis_image);
        }
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public   class PlayerViewHolder extends RecyclerView.ViewHolder {
        public View view;
        private ImageView tennis_image;
        private TextView tennis_name, tennis_location;

        public PlayerViewHolder(View view) {
            super(view);
            view = view;
            tennis_image = view.findViewById(R.id.tennis_image);
            tennis_name = view.findViewById(R.id.tennis_name);
            tennis_location = view.findViewById(R.id.tennis_location);
        }


    }
}
