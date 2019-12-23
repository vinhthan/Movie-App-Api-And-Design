package com.example.movieappapianddesign.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieappapianddesign.R;
import com.example.movieappapianddesign.adapter.ItemOnclickListener;
import com.example.movieappapianddesign.adapter.PopularMovieAdapter;
import com.example.movieappapianddesign.adapter.SlidePageAdapter;
import com.example.movieappapianddesign.api.ApiClient;
import com.example.movieappapianddesign.api.ApiInterface;
import com.example.movieappapianddesign.model.Constants;
import com.example.movieappapianddesign.model.PopularMovies;
import com.example.movieappapianddesign.model.Slides;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ItemOnclickListener {
    private RecyclerView recyclerView;
    private List<PopularMovies.Results> mList;
    private PopularMovieAdapter popularMovieAdapter;

    private ApiInterface apiInterface;
    private SlidePageAdapter slidePageAdapter;
    private ViewPager slidePage;
    private List<Slides> listSlides;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);

        getMovies();

        initSlideImage();

    }

    private void initSlideImage() {
        slidePage = findViewById(R.id.slidePage);
        //list slide
        listSlides = new ArrayList<>();

        listSlides.add(new Slides(R.drawable.slide2, "Slide title \nmore text here"));
        listSlides.add(new Slides(R.drawable.slide3, "Slide title \nmore text here"));
        listSlides.add(new Slides(R.drawable.slide2, "Slide title \nmore text here"));
        listSlides.add(new Slides(R.drawable.slide3, "Slide title \nmore text here"));

        slidePageAdapter = new SlidePageAdapter(this, listSlides);
        slidePage.setAdapter(slidePageAdapter);

        //setup time
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SlideTime(),
                3000, 4000);//img dau 3s, img sau 4s

    }


    private class SlideTime extends TimerTask {
        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (slidePage.getCurrentItem() < listSlides.size() - 1){
                        slidePage.setCurrentItem(slidePage.getCurrentItem() + 1);
                    }else {
                        slidePage.setCurrentItem(0);
                    }
                }
            });
        }
    }

    private void getMovies() {
        recyclerView = findViewById(R.id.recyclerView);
        mList = new ArrayList<>();
        popularMovieAdapter = new PopularMovieAdapter(mList, this, this::onClickListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(popularMovieAdapter);

        Call<PopularMovies> call = apiInterface.getMovies(Constants.KEY_API);
        call.enqueue(new Callback<PopularMovies>() {
            @Override
            public void onResponse(Call<PopularMovies> call, Response<PopularMovies> response) {
                if (response.isSuccessful()){
                    if (!mList.isEmpty()){
                        mList.clear();
                    }
                    //add list
                    mList.addAll(response.body().getResults());
                    popularMovieAdapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onFailure(Call<PopularMovies> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onClickListener(int position) {
        PopularMovies.Results movie = mList.get(position);
        //Toast.makeText(this, "Position: "+ position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
        intent.putExtra("title", movie.getTitle());
        intent.putExtra("imagePoster", movie.getBackdropPath());
        intent.putExtra("smallPoster", movie.getPosterPath());
        intent.putExtra("overview", movie.getOverview());

        startActivity(intent);
    }




}
//api key:
//034bbd1b233d6726e0c7dc7f338657f9
//
//URL:
//https://api.themoviedb.org/3/movie/popular?api_key=034bbd1b233d6726e0c7dc7f338657f9
//
//poster
//https://image.tmdb.org/t/p/w500/kqjL17yufvn9OVLyXYpvtyrFfak.jpg