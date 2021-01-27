package com.example.popularmovies.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.popularmovies.R;
import com.example.popularmovies.adapter.MovieDetailsRecyclerViewAdapter;
import com.example.popularmovies.api.JsonHolder;
import com.example.popularmovies.model.Movie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetailsActivity extends AppCompatActivity {

    private static String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String TAG = "Movie Details";
    private Movie movie;
    private RecyclerView rvMovieDetails;
    private MovieDetailsRecyclerViewAdapter recyclerViewAdapter;

    private static SharedPreferences sharedPreferences;

    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences = this.getSharedPreferences("Shared Preferences", Context.MODE_PRIVATE);

        rvMovieDetails = findViewById(R.id.rv_movie_details);
        setMovieData();

    }

    private void setMovieData() {
        Gson gson = new Gson();
        Type type = new TypeToken<Movie>() {}.getType();
        String data = getIntent().getStringExtra("MovieDetails");
        movie = gson.fromJson(data, type);

        recyclerViewAdapter = new MovieDetailsRecyclerViewAdapter(movie,this);
        rvMovieDetails.setLayoutManager(new LinearLayoutManager(this));
        rvMovieDetails.setAdapter(recyclerViewAdapter);

        if(movie.getRuntime() == null){
            getMoviesDetails(""+movie.getId());
        }

    }

    private void getMoviesDetails(String movieID) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonHolder jsonHolder = retrofit.create(JsonHolder.class);

        Call<Movie> call = jsonHolder.getMovieDetails(movieID);

        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if(!response.isSuccessful()){
                    Log.d(TAG,"Code : " + response.code());
                    return;
                }
                movie.setRuntime(response.body().getRuntime());
                recyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.d(TAG,t.getMessage());
            }
        });

    }

}