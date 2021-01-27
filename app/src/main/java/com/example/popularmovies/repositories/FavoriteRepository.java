package com.example.popularmovies.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.popularmovies.model.Movie;
import com.example.popularmovies.view.FavoriteFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FavoriteRepository {

    private static FavoriteRepository instance;
    private static String TAG = "Favorite Repo";
    private MutableLiveData<List<Movie>> data = new MutableLiveData<>();
    private List<Movie> dataset = new ArrayList<>();

    public static FavoriteRepository getInstance(){
        if(instance == null){
            instance = new FavoriteRepository();
        }
        return instance;
    }

    public MutableLiveData<List<Movie>> getFavoriteMovies() {
        getDataFromShared();
        Log.d(TAG,"Data From Shared : "+data.getValue().size());
        return data;
    }

    private void getDataFromShared(){
        Gson gson = new Gson();
        String json = FavoriteFragment.getSharedPreferences().getString("favorite list",null);
        Type type = new TypeToken<List<Movie>>() {}.getType();
        dataset.clear();
        if(gson.fromJson(json,type) != null)
            dataset.addAll(gson.fromJson(json,type));

        data.setValue(dataset);
    }

}
