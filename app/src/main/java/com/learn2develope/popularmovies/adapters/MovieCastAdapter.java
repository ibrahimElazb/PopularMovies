package com.learn2develope.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.learn2develope.popularmovies.R;
import com.learn2develope.popularmovies.model.movies.movieCast.Cast;

import java.util.List;

/**
 * Created by Ibrahim Elazb on 1/1/2018.
 */

public class MovieCastAdapter extends RecyclerView.Adapter<MovieCastAdapter.MovieCastViewHolder> {

    List<Cast> castList;
    Context context;

    public MovieCastAdapter(Context context, List<Cast> castList){
        this.castList=castList;
        this.context=context;
    }

    @Override
    public MovieCastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_item_movie,parent,false);
        return new MovieCastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MovieCastViewHolder holder, int position) {
        holder.castTitleTextView.setText(castList.get(position).name);
        String imageUrl="https://image.tmdb.org/t/p/w500/"+castList.get(position).profilePath;
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
                into(holder.castImageView);
    }

    @Override
    public int getItemCount() {
        return castList.size();
    }



    public class MovieCastViewHolder extends RecyclerView.ViewHolder{


        TextView castTitleTextView;
        ImageView castImageView;
        ProgressBar imageLoadingProgressBar;
        public MovieCastViewHolder(View view){
            super(view);
            castTitleTextView=(TextView)view.findViewById(R.id.tv_cast_title);
            castImageView=(ImageView)view.findViewById(R.id.iv_movie_cast_image);
            imageLoadingProgressBar=(ProgressBar)view.findViewById(R.id.pb_image_loading);
        }
    }
}
