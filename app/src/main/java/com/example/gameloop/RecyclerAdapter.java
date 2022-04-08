package com.example.gameloop;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gameloop.models.Game;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private List<Game> gameList;

    public RecyclerAdapter( List<Game> gameList) {
        this.gameList = gameList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView gameName;

        public MyViewHolder(final View view) {
            super(view);
            gameName = view.findViewById(R.id.gameName);
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
        String name = gameList.get(position).getName();
        holder.gameName.setText(name);
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }
}
