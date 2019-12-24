package com.example.movieappapianddesign.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movieappapianddesign.R;

public class MoviesDetailActivity extends AppCompatActivity {
    private ImageView imgPosterDetail, imgSmallPoster;
    private TextView txvDetailMovieTitle, txvOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        imgPosterDetail = findViewById(R.id.imgPosterDetail);
        imgSmallPoster = findViewById(R.id.imgSmalPoster);
        txvOverview = findViewById(R.id.txvDescription);
        txvDetailMovieTitle = findViewById(R.id.txvDetailMovieTitle);


        getIntentFromMain();

    }

    private void getIntentFromMain() {
        Intent intent = getIntent();
        String txvDes = intent.getStringExtra("title");
        txvDetailMovieTitle.setText(txvDes);
        String imgPoster = intent.getStringExtra("imagePoster");
        Glide.with(this).load(imgPoster).into(imgPosterDetail);//chính this nó đã là context rồi, nên k dùng context trong TH này

        String smallPoster = intent.getStringExtra("smallPoster");
        Glide.with(this).load(smallPoster).into(imgSmallPoster);

        String overview = intent.getStringExtra("overview");
        txvOverview.setText(overview);


    }
}
