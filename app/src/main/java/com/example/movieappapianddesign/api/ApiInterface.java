package com.example.movieappapianddesign.api;

import com.example.movieappapianddesign.model.PopularMovies;
import com.example.movieappapianddesign.model.UpcomingMovies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    //popular
    @GET("movie/popular")
    Call<PopularMovies> getMoviesPopular(@Query("api_key") String api_key);

    //upcoming
    @GET("movie/upcoming")
    Call<UpcomingMovies> getMoviesUpComing(@Query("api_key") String api_key);

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