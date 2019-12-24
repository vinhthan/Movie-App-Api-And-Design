package com.example.movieappapianddesign.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieappapianddesign.R;
import com.example.movieappapianddesign.model.UpcomingMovies;

import java.util.List;

public class UpcomingMovieAdapter extends RecyclerView.Adapter<UpcomingMovieAdapter.ViewHolder> {
    private List<UpcomingMovies.Results> listUpComing;
    private Context context;

    public UpcomingMovieAdapter(List<UpcomingMovies.Results> listUpComing, Context context) {
        this.listUpComing = listUpComing;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_upcoming, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UpcomingMovies.Results upComingMovies = listUpComing.get(position);

        holder.txvTitleUpComing.setText(upComingMovies.getTitle());
        holder.txvDateUpComing.setText(upComingMovies.getReleaseDate());
        Glide.with(context).load(upComingMovies.getBackdropPath()).into(holder.imgMovieUpComing);
    }

    @Override
    public int getItemCount() {
        return listUpComing.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgMovieUpComing;
        private TextView txvTitleUpComing, txvDateUpComing;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgMovieUpComing = itemView.findViewById(R.id.imgMovieUpComing);
            txvTitleUpComing = itemView.findViewById(R.id.txvTitleUpComing);
            txvDateUpComing = itemView.findViewById(R.id.txvDateUpComing);
        }
    }
}
