package com.example.popularmovies.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.popularmovies.api.JsonHolder;
import com.example.popularmovies.model.Movie;
import com.example.popularmovies.model.MovieList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieRepository {

    private static MovieRepository instance;
    private static String TAG = "Movie Repo";
    private static String BASE_URL = "https://api.themoviedb.org/3/";
    private MutableLiveData<List<Movie>> data = new MutableLiveData<>();
    private List<Movie> dataset = new ArrayList<>();


    public static MovieRepository getInstance(){
        if(instance == null){
            instance = new MovieRepository();
        }
        return instance;
    }

    public MutableLiveData<List<Movie>> getPopularMovies(){
        getPopularFromAPI();

        data.setValue(dataset);
        return data;
    }

    private void getPopularFromAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonHolder jsonHolder = retrofit.create(JsonHolder.class);

        Call<MovieList> call = jsonHolder.getPopularMovies();

        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                if(!response.isSuccessful()){
                    Log.d(TAG,"Code : " + response.code());
                    return;
                }
                dataset = response.body().getResults();
                data.setValue(dataset);
                Log.d(TAG,"Data Get : "+data.getValue().size());
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                Log.d(TAG,t.getMessage());
            }
        });

    }
}
