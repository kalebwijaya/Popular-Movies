package com.example.popularmovies.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.popularmovies.model.Movie;
import com.example.popularmovies.repositories.MovieRepository;

import java.util.List;

public class MovieViewModel extends ViewModel {

    private MutableLiveData<List<Movie>> mData;
    private MovieRepository mRepo;

    public void init(){
        if (mData != null){
            return;
        }
        mRepo = MovieRepository.getInstance();
        mData = mRepo.getPopularMovies();

    }

    public MutableLiveData<List<Movie>> getPopularMovies() {
        return mData;
    }
}