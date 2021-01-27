package com.example.popularmovies.api;

import com.example.popularmovies.model.Movie;
import com.example.popularmovies.model.MovieList;
import com.example.popularmovies.model.Reviews;
import com.example.popularmovies.model.TrailerVideo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JsonHolder {

    final static String API_KEY = "";

    @GET("movie/popular?api_key="+API_KEY)
    Call<MovieList> getPopularMovies();

    @GET("movie/top_rated?api_key="+API_KEY)
    Call<MovieList> getTopRatedMovies();

    @GET("movie/{movieID}/videos?api_key="+API_KEY)
    Call<TrailerVideo> getMovieTrailer(@Path(value = "movieID", encoded = true) String movieID);

    @GET("movie/{movieID}?api_key="+API_KEY)
    Call<Movie> getMovieDetails(@Path(value = "movieID", encoded = true) String movieID);

    @GET("movie/{movieID}/reviews?api_key="+API_KEY)
    Call<Reviews> getMovieReview(@Path(value = "movieID", encoded = true) String movieID);

}
