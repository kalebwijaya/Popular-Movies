package com.example.popularmovies.view;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.popularmovies.adapter.MovieRecyclerViewAdapter;
import com.example.popularmovies.model.Movie;
import com.example.popularmovies.viewModel.FavoriteViewModel;
import com.example.popularmovies.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class FavoriteFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener{

    private static final String TAG = "Favorite Fragment";
    private static final String MOVIE_STATE = "favorites";

    private View view;
    private List<Movie> dataset;
    private RecyclerView rv_moviePoster;
    private MovieRecyclerViewAdapter mAdapter;
    private FavoriteViewModel mViewModel;

    private static SharedPreferences sharedPreferences;

    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sharedPreferences = getContext().getSharedPreferences("Shared Preferences", Context.MODE_PRIVATE);

        mViewModel = new ViewModelProvider(requireActivity()).get(FavoriteViewModel.class);
        mViewModel.init();

        dataset = mViewModel.getFavoriteMovies().getValue();

        mAdapter = new MovieRecyclerViewAdapter(getContext(), dataset);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.favorite_fragment, container, false);
        rv_moviePoster = view.findViewById(R.id.rv_favorites_movie);
        rv_moviePoster.setLayoutManager(new GridLayoutManager(getActivity(),2));
        rv_moviePoster.setAdapter(mAdapter);

        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        return view;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        getDataFromShared();
        mAdapter.notifyDataSetChanged();
    }

    private void getDataFromShared(){
        Gson gson = new Gson();
        String json = sharedPreferences.getString("favorite list",null);
        Type type = new TypeToken<List<Movie>>() {}.getType();
        dataset.clear();
        if(gson.fromJson(json,type)!=null)
            dataset.addAll(gson.fromJson(json,type));
    }

}