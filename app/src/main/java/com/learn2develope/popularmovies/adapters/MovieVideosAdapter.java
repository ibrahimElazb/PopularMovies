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
import com.learn2develope.popularmovies.R;
import com.learn2develope.popularmovies.model.movies.movieVideos.Result;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ibrahim Elazb on 1/1/2018.
 */

public class MovieVideosAdapter extends RecyclerView.Adapter<MovieVideosAdapter.MovieVideosViewHolder>{


    List<Result> videoList;

    public MovieVideosAdapter(List<Result> videoList){
        this.videoList = videoList;
    }

    @Override
    public MovieVideosAdapter.MovieVideosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_movie,parent,false);
        return new MovieVideosAdapter.MovieVideosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MovieVideosAdapter.MovieVideosViewHolder holder, int position) {
        holder.TitleTextView.setText(videoList.get(position).name);
        String imageUrl="http://img.youtube.com/vi/"+ videoList.get(position).key+"/0.jpg";
        Picasso.with(holder.listItemView.getContext()).load(imageUrl)
                .into(holder.ImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.imageLoadingProgressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError() {
                        holder.imageLoadingProgressBar.setVisibility(View.INVISIBLE);
                        holder.ImageView.setImageResource(R.drawable.ic_no_image);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }


    public class MovieVideosViewHolder extends RecyclerView.ViewHolder {
        TextView TitleTextView;
        ImageView ImageView;
        ProgressBar imageLoadingProgressBar;
        View listItemView;

        public MovieVideosViewHolder(View view) {
            super(view);
            listItemView=view;
            TitleTextView = (TextView) view.findViewById(R.id.tv_cast_title);
            ImageView = (ImageView) view.findViewById(R.id.iv_movie_cast_image);
            imageLoadingProgressBar = (ProgressBar) view.findViewById(R.id.pb_image_loading);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://www.youtube.com/watch?v=" + videoList.get(getAdapterPosition()).key));
                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}
