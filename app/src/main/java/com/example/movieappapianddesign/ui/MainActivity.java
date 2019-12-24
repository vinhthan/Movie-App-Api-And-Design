package com.example.movieappapianddesign.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.example.movieappapianddesign.R;
import com.example.movieappapianddesign.adapter.ItemOnClickListenerPopular;
import com.example.movieappapianddesign.adapter.ItemOnClickListenerUpcoming;
import com.example.movieappapianddesign.adapter.PopularMovieAdapter;
import com.example.movieappapianddesign.adapter.SlidePageAdapter;
import com.example.movieappapianddesign.adapter.UpcomingMovieAdapter;
import com.example.movieappapianddesign.api.ApiClient;
import com.example.movieappapianddesign.api.ApiInterface;
import com.example.movieappapianddesign.model.Constants;
import com.example.movieappapianddesign.model.PopularMovies;
import com.example.movieappapianddesign.model.Slide;
import com.example.movieappapianddesign.model.UpcomingMovies;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ItemOnClickListenerPopular, ItemOnClickListenerUpcoming {
    private RecyclerView recyclerView, recyclerViewUpComing;
    private List<PopularMovies.Results> mListPopular;
    private List<UpcomingMovies.Results> mListUpComing;
    private PopularMovieAdapter popularMovieAdapter;
    private UpcomingMovieAdapter upComingMovieAdapter;

    private ApiInterface apiInterface;
    private SlidePageAdapter slidePageAdapter;
    private ViewPager slidePage;
    private List<Slide> listSlides;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);

        getMoviesPopular();

        getMoviesUpComing();

        //slide
        //initSlideImage();

    }


    //slide
/*    private void initSlideImage() {
        slidePage = findViewById(R.id.slidePage);
        //list slide
        listSlides = new ArrayList<>();

        listSlides.add(new Slide(R.drawable.slide2, "Slide title \nmore text here"));
        listSlides.add(new Slide(R.drawable.slide3, "Slide title \nmore text here"));
        listSlides.add(new Slide(R.drawable.slide2, "Slide title \nmore text here"));
        listSlides.add(new Slide(R.drawable.slide3, "Slide title \nmore text here"));

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
    }*/


    private void getMoviesPopular() {
        recyclerView = findViewById(R.id.recyclerView);
        mListPopular = new ArrayList<>();
        popularMovieAdapter = new PopularMovieAdapter(mListPopular, this, this::onClickListenerPopularMovies);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(popularMovieAdapter);

        Call<PopularMovies> call = apiInterface.getMoviesPopular(Constants.KEY_API);
        call.enqueue(new Callback<PopularMovies>() {
            @Override
            public void onResponse(Call<PopularMovies> call, Response<PopularMovies> response) {
                if (response.isSuccessful()){
                    if (!mListPopular.isEmpty()){
                        mListPopular.clear();
                    }
                    //add list
                    mListPopular.addAll(response.body().getResults());
                    popularMovieAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<PopularMovies> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getMoviesUpComing() {
        recyclerViewUpComing = findViewById(R.id.recyclerViewUpComing);
        mListUpComing = new ArrayList<>();
        upComingMovieAdapter = new UpcomingMovieAdapter(mListUpComing, this, this::onClickListenerUpcoming);
        recyclerViewUpComing.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewUpComing.setHasFixedSize(true);
        recyclerViewUpComing.setAdapter(upComingMovieAdapter);

        Call<UpcomingMovies> callUpComingMovies = apiInterface.getMoviesUpComing(Constants.KEY_API);
        callUpComingMovies.enqueue(new Callback<UpcomingMovies>() {
            @Override
            public void onResponse(Call<UpcomingMovies> call, Response<UpcomingMovies> response) {
                if (response.isSuccessful()){
                    if (!mListUpComing.isEmpty()){
                        mListUpComing.clear();
                    }
                    //add list
                    mListUpComing.addAll(response.body().getResults());
                    upComingMovieAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<UpcomingMovies> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onClickListenerPopularMovies(int position) {
        PopularMovies.Results moviePopular = mListPopular.get(position);
        //Toast.makeText(this, "Position: "+ position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, MoviesDetailPopularActivity.class);
        intent.putExtra("titlePopular", moviePopular.getTitle());
        intent.putExtra("imagePosterPopular", moviePopular.getBackdropPath());
        intent.putExtra("smallPosterPopular", moviePopular.getPosterPath());
        intent.putExtra("overviewPopular", moviePopular.getOverview());

        startActivity(intent);
    }


    @Override
    public void onClickListenerUpcoming(int positionUp) {
        UpcomingMovies.Results movieUpcoming = mListUpComing.get(positionUp);
        //Toast.makeText(this, "Position: "+ position, Toast.LENGTH_SHORT).show();
        Intent intentUp = new Intent(MainActivity.this, MoviesDetailUpcomingActivity.class);
        intentUp.putExtra("titleUpcoming", movieUpcoming.getTitle());
        intentUp.putExtra("imagePosterUpcoming", movieUpcoming.getBackdropPath());
        intentUp.putExtra("smallPosterUpcoming", movieUpcoming.getPosterPath());
        intentUp.putExtra("overviewUpcoming", movieUpcoming.getOverview());

        startActivity(intentUp);
    }
}
//api key:
//034bbd1b233d6726e0c7dc7f338657f9
//
//URL:
//Popular movie
//https://api.themoviedb.org/3/movie/popular?api_key=034bbd1b233d6726e0c7dc7f338657f9
//Upcoming movie
//https://api.themoviedb.org/3/movie/upcoming?api_key=034bbd1b233d6726e0c7dc7f338657f9
//poster
//https://image.tmdb.org/t/p/w500/kqjL17yufvn9OVLyXYpvtyrFfak.jpg