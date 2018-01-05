package com.learn2develope.popularmovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.learn2develope.popularmovies.R;
import com.learn2develope.popularmovies.model.movies.movieVideos.Result;

import java.util.List;

/**
 * Created by Ibrahim Elazb on 1/1/2018.
 */

public class MovieVideosAdapter extends RecyclerView.Adapter<MovieVideosAdapter.MovieVideosViewHolder>{


    List<Result> videoList;
    Context context;

    public MovieVideosAdapter(Context context, List<Result> videoList){
        this.videoList = videoList;
        this.context=context;
    }

    @Override
    public MovieVideosAdapter.MovieVideosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_item_movie,parent,false);
        return new MovieVideosAdapter.MovieVideosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MovieVideosAdapter.MovieVideosViewHolder holder, int position) {
        holder.TitleTextView.setText(videoList.get(position).name);
        String imageUrl="http://img.youtube.com/vi/"+ videoList.get(position).key+"/0.jpg";
        Glide.with(context.getApplicationContext())
                .load(imageUrl).
                error(R.drawable.ic_no_image).
                fitCenter().
                listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        holder.imageLoadingProgressBar.setVisibility(View.INVISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        holder.imageLoadingProgressBar.setVisibility(View.INVISIBLE);
                        return false;
                    }
                }).
                into(holder.ImageView);
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }


    public class MovieVideosViewHolder extends RecyclerView.ViewHolder {
        TextView TitleTextView;
        ImageView ImageView;
        ProgressBar imageLoadingProgressBar;

        public MovieVideosViewHolder(View view) {
            super(view);
            TitleTextView = (TextView) view.findViewById(R.id.tv_cast_title);
            ImageView = (ImageView) view.findViewById(R.id.iv_movie_cast_image);
            imageLoadingProgressBar = (ProgressBar) view.findViewById(R.id.pb_image_loading);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://www.youtube.com/watch?v=" + videoList.get(getAdapterPosition()).key));
                    context.startActivity(intent);
                }
            });
        }
    }
}
