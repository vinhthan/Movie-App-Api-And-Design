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
    private ItemOnClickListenerPopular itemOnclickListenerPopular;

    /*public PopularMovieAdapter(List<PopularMovies.Results> list, Context context) {
        this.list = list;
        this.context = context;
    }*/

    public PopularMovieAdapter(List<PopularMovies.Results> list, Context context, ItemOnClickListenerPopular itemOnclickListenerPopular) {
        this.list = list;
        this.context = context;
        this.itemOnclickListenerPopular = itemOnclickListenerPopular;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PopularMovies.Results movie = list.get(position);

        holder.txvMovieTitlePopular.setText(movie.getTitle());
        holder.txvDatePopular.setText(movie.getReleaseDate());
        Glide.with(context).load(movie.getBackdropPath()).into(holder.imgMoviePopular);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txvMovieTitlePopular, txvDatePopular;
        private ImageView imgMoviePopular;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txvMovieTitlePopular = itemView.findViewById(R.id.txvMovieTitlePopular);
            txvDatePopular = itemView.findViewById(R.id.txvDatePopular);
            imgMoviePopular = itemView.findViewById(R.id.imgMoviePopular);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemOnclickListenerPopular.onClickListenerPopularMovies(getPosition());
                }
            });


        }
    }
}
