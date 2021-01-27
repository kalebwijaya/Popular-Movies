package com.example.popularmovies.view;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.popularmovies.R;
import com.example.popularmovies.adapter.MovieRecyclerViewAdapter;
import com.example.popularmovies.api.JsonHolder;
import com.example.popularmovies.model.Movie;
import com.example.popularmovies.model.MovieList;
import com.example.popularmovies.viewModel.MovieViewModel;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieFragment extends Fragment {

    private static String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String TAG = "Movie Fragment";
    private static final String MOVIE_STATE = "movies";

    private View view;
    private List<Movie> dataset;
    private RecyclerView rv_moviePoster;
    private MovieRecyclerViewAdapter mAdapter;
    private MovieViewModel mViewModel;
    private FloatingActionButton fabSortByPopularity,fabSortByRating;

    private Boolean byPopularity = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null){
            Gson gson = new Gson();
            String json = savedInstanceState.getString(MOVIE_STATE);

            Type type = new TypeToken<List<Movie>>() {}.getType();

            dataset = gson.fromJson(json, type);
            Log.d(TAG,"Data Get From Saved Instance");
        }else{
            mViewModel = new ViewModelProvider(requireActivity()).get(MovieViewModel.class);
            mViewModel.init();

            dataset = mViewModel.getPopularMovies().getValue();
        }

        mAdapter = new MovieRecyclerViewAdapter(getContext(), dataset);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.movie_fragment, container, false);
        rv_moviePoster = view.findViewById(R.id.rv_popular_movie);
        rv_moviePoster.setLayoutManager(new GridLayoutManager(getActivity(),2));
        rv_moviePoster.setAdapter(mAdapter);

        fabSortByPopularity = view.findViewById(R.id.fab_popluarity);
        fabSortByRating = view.findViewById(R.id.fab_rating);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel.getPopularMovies().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                saveMovieData(movies);
                dataset.clear();
                dataset.addAll(movies);
                mAdapter.notifyDataSetChanged();
                Log.d(TAG,"Data Changed : " + movies.size());
            }
        });

        fabSortByRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (byPopularity){
                    getRatingFromAPI();
                    byPopularity = false;
                }
            }
        });

        fabSortByPopularity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!byPopularity) {
                    getPopularFromAPI();
                    byPopularity = true;
                }
            }
        });

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Gson gson = new Gson();
        String json = gson.toJson(dataset);
        outState.putString(MOVIE_STATE, json);
        super.onSaveInstanceState(outState);
    }

    private void saveMovieData(List<Movie> dataset){
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("Shared Preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(dataset);
        editor.putString("movie list", json);
        editor.apply();
        editor.commit();
        Log.d(TAG,"Saved To Shared");
    }

    private void getRatingFromAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonHolder jsonHolder = retrofit.create(JsonHolder.class);

        Call<MovieList> call = jsonHolder.getTopRatedMovies();

        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                if(!response.isSuccessful()){
                    Log.d(TAG,"Code : " + response.code());
                    return;
                }
                dataset.clear();
                dataset.addAll(response.body().getResults());
                mAdapter.notifyDataSetChanged();
                Log.d(TAG, "Data Sort By Rating : ");
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                Log.d(TAG,t.getMessage());
            }
        });

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
                dataset.clear();
                dataset.addAll(response.body().getResults());
                mAdapter.notifyDataSetChanged();
                Log.d(TAG, "Data Sort By Popularity : ");
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                Log.d(TAG,t.getMessage());
            }
        });

    }

}