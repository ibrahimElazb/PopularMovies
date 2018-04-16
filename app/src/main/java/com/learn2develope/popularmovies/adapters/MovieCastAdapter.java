package com.learn2develope.popularmovies.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.learn2develope.popularmovies.R;
import com.learn2develope.popularmovies.model.movies.movieCast.Cast;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ibrahim Elazb on 1/1/2018.
 */

public class MovieCastAdapter extends RecyclerView.Adapter<MovieCastAdapter.MovieCastViewHolder> {

    List<Cast> castList;

    public MovieCastAdapter( List<Cast> castList){
        this.castList=castList;
    }

    @Override
    public MovieCastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_movie,parent,false);
        return new MovieCastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MovieCastViewHolder holder, int position) {
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



    public class MovieCastViewHolder extends RecyclerView.ViewHolder{


        TextView castTitleTextView;
        ImageView castImageView;
        ProgressBar imageLoadingProgressBar;
        View listItemView;
        public MovieCastViewHolder(View view){
            super(view);
            listItemView=view;
            castTitleTextView=(TextView)view.findViewById(R.id.tv_cast_title);
            castImageView=(ImageView)view.findViewById(R.id.iv_movie_cast_image);
            imageLoadingProgressBar=(ProgressBar)view.findViewById(R.id.pb_image_loading);
        }
    }
}
