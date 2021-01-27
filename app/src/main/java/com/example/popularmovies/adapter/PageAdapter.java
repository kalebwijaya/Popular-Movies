package com.example.popularmovies.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.popularmovies.view.FavoriteFragment;
import com.example.popularmovies.view.MovieFragment;

public class PageAdapter extends FragmentStatePagerAdapter {

    final int pageCount = 2;
    private String tabTitles[] = new String[]{"Movies","Favorite"};

    public PageAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT );
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new MovieFragment();
            case 1:
                return new FavoriteFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return pageCount;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
