package com.example.popularmovies.adapter;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.view.MovieDetailsActivity;
import com.example.popularmovies.R;
import com.example.popularmovies.model.Movie;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.MovieViewHolder> {

    private static String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w185/";

    private Context mContext;
    private List<Movie> mData;

    public MovieRecyclerViewAdapter(Context mContext, List<Movie> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item,parent,false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

        if (mData.get(position).getImagePath().isEmpty()){
            String imageURL = BASE_IMAGE_URL + mData.get(position).getPoster_path();
            Picasso.get().load(imageURL).into(holder.iv_moviePoster);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String json = gson.toJson(mData.get(position));

                Intent intent = new Intent(mContext, MovieDetailsActivity.class);
                intent.putExtra("MovieDetails", json);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder{

        ImageView iv_moviePoster;
        CardView cardView;

        public MovieViewHolder (View itemView){
            super(itemView);

            iv_moviePoster = itemView.findViewById(R.id.iv_movie_poster);
            cardView = itemView.findViewById(R.id.item_layout);

        }

    }

    private String saveToInternalStorage(Bitmap bitmapImage, String child){
        ContextWrapper cw = new ContextWrapper(mContext);

        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File mypath=new File(directory,child);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }


}
