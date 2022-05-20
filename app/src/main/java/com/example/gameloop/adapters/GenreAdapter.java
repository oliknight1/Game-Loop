package com.example.gameloop.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gameloop.R;
import com.example.gameloop.fragments.GameFragment;
import com.example.gameloop.fragments.ListingFragment;
import com.example.gameloop.models.Game;
import com.example.gameloop.models.Genre;
import com.example.gameloop.models.PageType;

import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.MyViewHolder> {
    private List<Genre> genreList;

    public GenreAdapter(List<Genre> genreList) {
        this.genreList = genreList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView genreName;
        private ImageView genreImage;
        private CardView cardView;

        public MyViewHolder(final View view) {
            super(view);
            genreName = view.findViewById(R.id.genreName);
            genreImage = view.findViewById(R.id.genreImg);
            cardView = view.findViewById(R.id.genreCardView);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.genre_card,parent,false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Genre genre = genreList.get(position);
        String name = genre.getName();
        holder.genreName.setText(name);

        String imgSrc = genre.getBackgroundImage();
        Glide.with(holder.itemView).load(imgSrc).into(holder.genreImage);

        holder.cardView.setOnClickListener(view -> {
            ListingFragment fragment = ListingFragment.newInstance(PageType.GENRE, genre.getId(),genre.getName());
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            activity.getSupportFragmentManager().beginTransaction().addToBackStack(genre.getName()).replace(R.id.frame_layout,fragment).commit();
        });
    }

    @Override
    public int getItemCount() {
        return genreList.size();
    }

    public void setGenreList(List<Genre> list) {
        this.genreList = list;
        notifyDataSetChanged();
    }

    public void addAll(List<Genre> newList) {
        int lastIndex = genreList.size() - 1;
        genreList.addAll(newList);
        notifyItemRangeInserted(lastIndex, newList.size());
    }
}
