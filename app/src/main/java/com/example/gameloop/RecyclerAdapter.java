package com.example.gameloop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gameloop.fragments.GameFragment;
import com.example.gameloop.models.Game;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private List<Game> gameList;

    public RecyclerAdapter( List<Game> gameList) {
        this.gameList = gameList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView gameName;
        private ImageView gameImg;
        private CardView cardView;

        public MyViewHolder(final View view) {
            super(view);
            gameName = view.findViewById(R.id.gameName);
            gameImg = view.findViewById(R.id.gameImg);
            cardView = view.findViewById(R.id.card_view);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_card,parent,false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Game game = gameList.get(position);
        String name = game.getName();
        holder.gameName.setText(name);

        String imgSrc = game.getBackgroundImage();
        Glide.with(holder.itemView).load(imgSrc).into(holder.gameImg);

        holder.cardView.setOnClickListener(view -> {
            GameFragment fragment = GameFragment.newInstance(game.getId());
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,fragment).commit();
        });
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    public void setGameList(List<Game> list) {
        this.gameList = list;
        notifyDataSetChanged();
    }

    public void addAll(List<Game> newList) {
        int lastIndex = gameList.size() - 1;
        gameList.addAll(newList);
        notifyItemRangeInserted(lastIndex, newList.size());
    }
}
