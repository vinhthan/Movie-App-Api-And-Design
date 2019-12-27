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
    private ItemOnClickListenerUpcoming onClickListenerUpcoming;

    public UpcomingMovieAdapter(List<UpcomingMovies.Results> listUpComing, Context context, ItemOnClickListenerUpcoming onClickListenerUpcoming) {
        this.listUpComing = listUpComing;
        this.context = context;
        this.onClickListenerUpcoming = onClickListenerUpcoming;
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

        holder.txvItemTitleUpComing.setText(upComingMovies.getTitle());
        holder.txvItemDateUpComing.setText(upComingMovies.getReleaseDate());
        Glide.with(context).load(upComingMovies.getBackdropPath()).into(holder.imgItemMovieUpComing);

    }

    @Override
    public int getItemCount() {
        return listUpComing.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgItemMovieUpComing, imgRight;
        private TextView txvItemTitleUpComing, txvItemDateUpComing;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgItemMovieUpComing = itemView.findViewById(R.id.imgItemMovieUpComing);
            imgRight = itemView.findViewById(R.id.imgRight);
            txvItemTitleUpComing = itemView.findViewById(R.id.txvItemTitleUpComing);
            txvItemDateUpComing = itemView.findViewById(R.id.txvItemDateUpComing);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListenerUpcoming.onClickListenerUpcoming(getPosition());
                }
            });

        }
    }
}
