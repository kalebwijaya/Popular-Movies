package com.example.popularmovies.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;

import com.example.popularmovies.R;
import com.example.popularmovies.model.TrailerVideo;

public class TrailerListViewAdapter extends BaseAdapter {

    private TrailerVideo mData;
    private Context mContext;
    private LayoutInflater inflater;

    public TrailerListViewAdapter(TrailerVideo mData, Context mContext) {
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
            convertView = inflater.inflate(R.layout.item_movie_trailer, null);
        }

        TextView tvTrailer = convertView.findViewById(R.id.tv_trailer);
        ImageView ivPlay = convertView.findViewById(R.id.iv_play);

        ivPlay.setImageResource(R.drawable.ic_play);
        ImageViewCompat.setImageTintList(ivPlay, ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.black)));
        tvTrailer.setText("Trailer " + (position+1));

        return convertView;
    }

}
