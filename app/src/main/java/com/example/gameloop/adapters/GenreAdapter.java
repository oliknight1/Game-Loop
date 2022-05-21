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
import com.example.gameloop.fragments.ListingFragment;
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
            genreName = view.findViewById(R.id.cardName);
            genreImage = view.findViewById(R.id.cardImg);
            cardView = view.findViewById(R.id.cardView);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        cardView.setAlpha(0f);
        Genre genre = genreList.get(position);
        String name = genre.getName();
        holder.genreName.setText(name);

        String imgSrc = genre.getBackgroundImage();
        Glide.with(holder.itemView).load(imgSrc).into(holder.genreImage);

        cardView.animate().alpha(1f).setDuration(500);
        cardView.setOnClickListener(view -> {
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
}
