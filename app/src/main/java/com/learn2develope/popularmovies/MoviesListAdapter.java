package com.learn2develope.popularmovies;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
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

import java.util.List;

/**
 * Created by Ibrahim Elazb on 12/23/2017.
 */

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.MovieViewHolder> {

    List mResultList;
    Context mContext;
    int mCategory;

    public MoviesListAdapter(Context context, List resultList,int category){
        mContext=context;
        mResultList=resultList;
        mCategory=category;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItemView=LayoutInflater
                .from(mContext).inflate(R.layout.list_item_movie,parent,false);
        return new MovieViewHolder(listItemView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        if(mCategory==0){
            bindingMoviesList(holder,position);
        }else if (mCategory==1){
            bindingTvList(holder,position);
        }else if(mCategory==2){
            bindingActorsList(holder,position);
        }else throw new IllegalArgumentException("invalid argument");
    }

    public void bindingMoviesList(final MovieViewHolder holder, int position){
        com.learn2develope.popularmovies.model.movies.Result movieResult=(com.learn2develope.popularmovies.model.movies.Result)mResultList.get(position);
        String movieTitle=movieResult.title;
        String moviePosterPath=movieResult.posterPath;
       holder.movieTitleTextView.setText(movieTitle);
       String imageUrl="https://image.tmdb.org/t/p/w500/"+moviePosterPath;
        holder.imageLoadingProgressbar.setVisibility(View.VISIBLE);
        Glide.with(mContext.getApplicationContext()).
                load(imageUrl).
                error(R.drawable.ic_no_image).
                fitCenter().
                listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        holder.imageLoadingProgressbar.setVisibility(View.INVISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        holder.imageLoadingProgressbar.setVisibility(View.INVISIBLE);
                        return false;
                    }
                }).
                into(holder.moviePosterImageView);
    }

    public void bindingTvList(MovieViewHolder holder, int position){
        com.learn2develope.popularmovies.model.tv.Result tvResult=(com.learn2develope.popularmovies.model.tv.Result)mResultList.get(position);
        String tvTitle=tvResult.name;
        String tvShowPosterPath=tvResult.posterPath;
        holder.movieTitleTextView.setText(tvTitle);
        String imageUrl="https://image.tmdb.org/t/p/w500/"+tvShowPosterPath;
        Glide.with(mContext).load(imageUrl).into(holder.moviePosterImageView);
        holder.imageLoadingProgressbar.setVisibility(View.INVISIBLE);
        holder.moviePosterImageView.setVisibility(View.VISIBLE);
    }

    public void bindingActorsList(MovieViewHolder holder, int position){
        com.learn2develope.popularmovies.model.actors.Result actorResult=(com.learn2develope.popularmovies.model.actors.Result)mResultList.get(position);
        String actorName=actorResult.name;
        String actorProfilePath=actorResult.profilePath;
        holder.movieTitleTextView.setText(actorName);
        String imageUrl="https://image.tmdb.org/t/p/w500/"+actorProfilePath;
        Glide.with(mContext).load(imageUrl).into(holder.moviePosterImageView);
        holder.imageLoadingProgressbar.setVisibility(View.INVISIBLE);
        holder.moviePosterImageView.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return mResultList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder{

        ImageView moviePosterImageView;
        TextView movieTitleTextView;
        ProgressBar imageLoadingProgressbar;
        public MovieViewHolder(View listItemView){
            super(listItemView);
            moviePosterImageView=(ImageView)listItemView.findViewById(R.id.iv_movie_poster);
            movieTitleTextView=(TextView)listItemView.findViewById(R.id.tv_movie_title);
            imageLoadingProgressbar=(ProgressBar)listItemView.findViewById(R.id.pb_image_loading);
        }
    }
}
