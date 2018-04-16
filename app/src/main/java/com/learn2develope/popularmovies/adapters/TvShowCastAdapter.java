package com.learn2develope.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.learn2develope.popularmovies.R;
import com.learn2develope.popularmovies.model.tv.tvCast.Cast;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ibrahim Elazb on 1/24/2018.
 */

public class TvShowCastAdapter extends RecyclerView.Adapter<TvShowCastAdapter.TvCastViewHolder> {


    List<Cast> castList;

    public TvShowCastAdapter(List<Cast> castList){
        this.castList=castList;
    }

    @Override
    public TvShowCastAdapter.TvCastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_movie,parent,false);
        return new TvShowCastAdapter.TvCastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TvShowCastAdapter.TvCastViewHolder holder, int position) {
        holder.castTitleTextView.setText(castList.get(position).name);
        String imageUrl="https://image.tmdb.org/t/p/w500/"+castList.get(position).profilePath;
        Picasso.with(holder.listItemView.getContext()).load(imageUrl)
                .into(holder.castImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.imageLoadingProgressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError() {
                        holder.imageLoadingProgressBar.setVisibility(View.INVISIBLE);
                        holder.castImageView.setImageResource(R.drawable.ic_no_image);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return castList.size();
    }



    public class TvCastViewHolder extends RecyclerView.ViewHolder{


        TextView castTitleTextView;
        ImageView castImageView;
        ProgressBar imageLoadingProgressBar;
        View listItemView;
        public TvCastViewHolder(View view){
            super(view);
            listItemView=view;
            castTitleTextView=(TextView)view.findViewById(R.id.tv_cast_title);
            castImageView=(ImageView)view.findViewById(R.id.iv_movie_cast_image);
            imageLoadingProgressBar=(ProgressBar)view.findViewById(R.id.pb_image_loading);
        }
    }
}
