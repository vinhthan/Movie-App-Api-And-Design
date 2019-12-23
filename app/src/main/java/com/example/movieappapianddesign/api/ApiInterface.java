package com.example.movieappapianddesign.api;

import com.example.movieappapianddesign.model.PopularMovies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("movie/popular")
    Call<PopularMovies> getMovies(@Query("api_key") String api_key);
}
