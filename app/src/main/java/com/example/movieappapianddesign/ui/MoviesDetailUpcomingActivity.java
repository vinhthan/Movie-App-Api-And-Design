package com.example.movieappapianddesign.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movieappapianddesign.R;
import com.example.movieappapianddesign.adapter.ItemOnClickListenerUpcoming;

public class MoviesDetailUpcomingActivity extends AppCompatActivity {

    private ImageView imgPosterUpcoming, imgSmallPosterUpcoming;
    private TextView txvTitleUpcoming1, txvOverviewUpcoming;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_detail_upcoming);

        imgPosterUpcoming = findViewById(R.id.imgPosterUpcoming);
        imgSmallPosterUpcoming = findViewById(R.id.imgSmallPosterUpcoming);
        txvTitleUpcoming1 = findViewById(R.id.txvTitleUpcoming1);
        txvOverviewUpcoming = findViewById(R.id.txvOverviewUpcoming);

        getIntentFromMainWithUpcoming();

    }

        private void getIntentFromMainWithUpcoming() {
        Intent intent = getIntent();
        String txvDesUp = intent.getStringExtra("titleUpcoming");
        txvTitleUpcoming1.setText(txvDesUp);
        String imgPosterUp = intent.getStringExtra("imagePosterUpcoming");
        Glide.with(this).load(imgPosterUp).into(imgPosterUpcoming);//chính this nó đã là context rồi, nên k dùng context trong TH này

        String smallPosterUp = intent.getStringExtra("smallPosterUpcoming");
        Glide.with(this).load(smallPosterUp).into(imgSmallPosterUpcoming);

        String overviewUp = intent.getStringExtra("overviewUpcoming");
        txvOverviewUpcoming.setText(overviewUp);

    }
}
