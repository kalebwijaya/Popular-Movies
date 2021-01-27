package com.example.popularmovies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popularmovies.R;
import com.example.popularmovies.model.AuthorDetails;
import com.example.popularmovies.model.ReviewResult;
import com.example.popularmovies.model.Reviews;
import com.squareup.picasso.Picasso;

public class ReviewListViewAdapter extends BaseAdapter {

    private Reviews mData;
    private Context mContext;
    private LayoutInflater inflater;

    public ReviewListViewAdapter(Reviews mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData.getResults().size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null){
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_movie_review, null);
        }

        TextView tvAuthor = convertView.findViewById(R.id.tv_review_author);
        TextView tvReview = convertView.findViewById(R.id.tv_review);
        TextView tvRating = convertView.findViewById(R.id.tv_review_rating);

        AuthorDetails authorDetails = mData.getResults().get(position).getAuthorDetails();

        tvAuthor.setText("Author : "+authorDetails.getUsername());

        if (authorDetails.getRating() != null)
            tvRating.setText(authorDetails.getRating()+"/10");
        else
            tvRating.setVisibility(View.GONE);

        tvReview.setText(mData.getResults().get(position).getContent());

        return convertView;
    }
}
