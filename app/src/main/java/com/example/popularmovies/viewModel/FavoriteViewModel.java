package com.example.popularmovies.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.popularmovies.model.Movie;
import com.example.popularmovies.repositories.FavoriteRepository;

import java.util.List;

public class FavoriteViewModel extends ViewModel {

    private MutableLiveData<List<Movie>> mData;
    private FavoriteRepository mRepo;

    public void init(){
        if (mData != null){
            return;
        }
        mRepo = FavoriteRepository.getInstance();
        mData = mRepo.getFavoriteMovies();

    }

    public MutableLiveData<List<Movie>> getFavoriteMovies() {
        return mData;
    }
}