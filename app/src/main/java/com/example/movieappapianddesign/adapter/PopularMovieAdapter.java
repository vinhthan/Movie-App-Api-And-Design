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
import com.example.movieappapianddesign.model.PopularMovies;

import java.util.List;

public class PopularMovieAdapter extends RecyclerView.Adapter<PopularMovieAdapter.ViewHolder> {
    private List<PopularMovies.Results> list;
    private Context context;
    private ItemOnclickListener itemOnclickListener;

    /*public PopularMovieAdapter(List<PopularMovies.Results> list, Context context) {
        this.list = list;
        this.context = context;
    }*/

    public PopularMovieAdapter(List<PopularMovies.Results> list, Context context, ItemOnclickListener itemOnclickListener) {
        this.list = list;
        this.context = context;
        this.itemOnclickListener = itemOnclickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.iteam_movie, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PopularMovies.Results movie = list.get(position);

        holder.txvItemMovieTitle.setText(movie.getTitle());
        Glide.with(context).load(movie.getBackdropPath()).into(holder.imgItemMovie);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txvItemMovieTitle;
        private ImageView imgItemMovie;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txvItemMovieTitle = itemView.findViewById(R.id.txvItemMovieTitle);
            imgItemMovie = itemView.findViewById(R.id.imgItemMovie);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemOnclickListener.onClickListener(getPosition());
                }
            });


        }
    }
}
