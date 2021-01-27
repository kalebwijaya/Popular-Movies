package com.example.popularmovies.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.R;
import com.example.popularmovies.api.JsonHolder;
import com.example.popularmovies.model.Movie;
import com.example.popularmovies.model.Reviews;
import com.example.popularmovies.model.TrailerResult;
import com.example.popularmovies.model.TrailerVideo;
import com.example.popularmovies.view.MovieDetailsActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetailsRecyclerViewAdapter extends RecyclerView.Adapter {

    private static String TAG = "Movie Detail Adapter";
    private static String BASE_URL = "https://api.themoviedb.org/3/";
    private static String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w342/";
    private Movie movie;
    private Context mContext;
    private TrailerListViewAdapter trailerAdapter;
    private TrailerVideo trailerVideo;
    private ReviewListViewAdapter reviewAdapter;
    private Reviews reviews;
    private List<Movie> dataset = new ArrayList<>();

    public MovieDetailsRecyclerViewAdapter(Movie movie, Context mContext) {
        this.movie = movie;
        this.mContext = mContext;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View view;

        if (viewType == 0 ){
            view = mInflater.inflate(R.layout.item_movie_details,parent,false);
            return new DetailsViewHolder(view);
        }else{
            view = mInflater.inflate(R.layout.item_movie_list,parent,false);
            return new RecyclerViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (position){
            case 0:
                DetailsViewHolder viewHolder = (DetailsViewHolder) holder;
                viewHolder.tvMovieTittle.setText(movie.getTitle());
                viewHolder.tvMovieYear.setText(movie.getRelease_date().substring(0,4));
                viewHolder.tvMovieDesc.setText(movie.getOverview());
                viewHolder.tvMovieRating.setText(movie.getVote_average()+"/10");
                viewHolder.tvMovieDuration.setText(movie.getRuntime()+"min");

                String imageURL = BASE_IMAGE_URL + movie.getPoster_path();
                Picasso.get().load(imageURL).into(viewHolder.ivMoviePoster);

                favoriteChecker();

                viewHolder.btnFavorite.setTextSize(10);


                if (movie.getFavorite())
                    viewHolder.btnFavorite.setText("REMOVE FROM FAVORITE");
                else
                    viewHolder.btnFavorite.setText("ADD TO FAVORITE");

                viewHolder.btnFavorite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!movie.getFavorite()){
                            addToFavorite();
                            viewHolder.btnFavorite.setText("REMOVE FROM FAVORITE");
                        }else{
                            removeFromFavorite();
                            viewHolder.btnFavorite.setText("ADD TO FAVORITE");
                        }
                    }
                });

                break;
            case 1:
                RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
                setMovieTrailer(recyclerViewHolder.listView, recyclerViewHolder.titteRV);
                break;
            case 2:
                RecyclerViewHolder reviewViewHolder = (RecyclerViewHolder) holder;
                setMovieReview(reviewViewHolder.listView, reviewViewHolder.titteRV);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }


    public class DetailsViewHolder extends RecyclerView.ViewHolder{

        TextView tvMovieTittle, tvMovieYear, tvMovieDuration, tvMovieRating, tvMovieDesc;
        ImageView ivMoviePoster;
        Button btnFavorite;

        public DetailsViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMovieTittle = itemView.findViewById(R.id.tv_movie_tittle);
            tvMovieYear = itemView.findViewById(R.id.tv_movie_year);
            tvMovieDuration = itemView.findViewById(R.id.tv_movie_duration);
            tvMovieRating = itemView.findViewById(R.id.tv_movie_rating);
            tvMovieDesc = itemView.findViewById(R.id.tv_movie_desc);
            ivMoviePoster = itemView.findViewById(R.id.iv_movie_detail_poster);
            btnFavorite = itemView.findViewById(R.id.btn_favorite);
        }
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{

        ListView listView;
        TextView titteRV;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            listView = itemView.findViewById(R.id.lv_movie_list_view);
            titteRV = itemView.findViewById(R.id.tv_rv_tittle);

        }
    }

    private void setMovieTrailer(ListView listView, TextView rvTitle){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonHolder jsonHolder = retrofit.create(JsonHolder.class);

        Call<TrailerVideo> call = jsonHolder.getMovieTrailer(""+movie.getId());

        call.enqueue(new Callback<TrailerVideo>() {
            @Override
            public void onResponse(Call<TrailerVideo> call, Response<TrailerVideo> response) {
                if(!response.isSuccessful()){
                    Log.d(TAG,"Code : " + response.code());
                    listView.setVisibility(View.GONE);
                    rvTitle.setVisibility(View.GONE);
                    return;
                }

                trailerVideo = response.body();
                trailerAdapter = new TrailerListViewAdapter(trailerVideo, mContext);
                listView.setAdapter(trailerAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        TrailerResult result = trailerVideo.getResults().get(position);
                        Log.d(TAG, result.getKey());
                        watchYoutubeVideo(mContext, result.getKey());
                    }
                });

                ViewGroup.LayoutParams lp = listView.getLayoutParams();
                final float scale = mContext.getResources().getDisplayMetrics().density;
                int pixels = (int) (90 * scale + 0.5f);
                lp.height = pixels * trailerVideo.getResults().size();
                listView.setLayoutParams(lp);

                rvTitle.setText("Trailers : ");
            }

            @Override
            public void onFailure(Call<TrailerVideo> call, Throwable t) {
                listView.setVisibility(View.GONE);
                rvTitle.setVisibility(View.GONE);
                Log.d(TAG,t.getMessage());
            }
        });
    }

    private void setMovieReview(ListView listView, TextView rvTitle){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonHolder jsonHolder = retrofit.create(JsonHolder.class);

        Call<Reviews> call = jsonHolder.getMovieReview(""+movie.getId());

        call.enqueue(new Callback<Reviews>() {
            @Override
            public void onResponse(Call<Reviews> call, Response<Reviews> response) {
                if(!response.isSuccessful()){
                    Log.d(TAG,"Code : " + response.code());
                    listView.setVisibility(View.GONE);
                    rvTitle.setVisibility(View.GONE);
                    return;
                }
                reviews = response.body();
                if(reviews.getTotalResults() != 0){
                    reviewAdapter = new ReviewListViewAdapter(reviews, mContext);
                    listView.setAdapter(reviewAdapter);

                    ViewGroup.LayoutParams lp = listView.getLayoutParams();
                    final float scale = mContext.getResources().getDisplayMetrics().density;
                    int pixels = (int) (147 * scale + 0.5f);
                    lp.height = pixels * reviews.getResults().size();
                    listView.setLayoutParams(lp);

                    rvTitle.setText("Reviews :");
                }else {
                    listView.setVisibility(View.GONE);
                    rvTitle.setVisibility(View.GONE);
                    Log.d(TAG,"No Review For This Movie");
                }
            }

            @Override
            public void onFailure(Call<Reviews> call, Throwable t) {
                listView.setVisibility(View.GONE);
                rvTitle.setVisibility(View.GONE);
                Log.d(TAG,t.getMessage());
            }
        });

    }

    private void addToFavorite(){
        movie.setFavorite(true);
        dataset.add(movie);
        saveMovieData();
    }

    private void saveMovieData(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("Shared Preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(dataset);
        editor.putString("favorite list", json);
        editor.apply();
        editor.commit();
        Log.d(TAG,"Saved To Shared");
    }

    private void favoriteChecker(){
        Gson gson = new Gson();
        String json = MovieDetailsActivity.getSharedPreferences().getString("favorite list",null);
        Type type = new TypeToken<List<Movie>>() {}.getType();

        dataset.clear();
        if(gson.fromJson(json,type) != null)
            dataset.addAll(gson.fromJson(json,type));


        for (Movie temp : dataset){
            if(temp.getId().equals(movie.getId())){
                movie.setFavorite(true);
                return;
            }
        }

    }

    private void removeFromFavorite(){
        movie.setFavorite(false);
        for (Movie temp : dataset){
            if (temp.getId().equals(movie.getId())){
                dataset.remove(temp);
                saveMovieData();
                break;
            }
        }
    }

    private void watchYoutubeVideo(Context context, String id){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }

}
