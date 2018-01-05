package com.learn2develope.popularmovies.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.learn2develope.popularmovies.R;
import com.learn2develope.popularmovies.model.movies.movieReviews.MovieReviews;
import com.learn2develope.popularmovies.model.movies.movieReviews.Result;

import java.util.List;

/**
 * Created by Ibrahim Elazb on 1/1/2018.
 */

public class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewsAdapter.MovieReviewsViewHolder> {

    Context context;
    List<Result> reviewsList;

    public MovieReviewsAdapter(Context context, List<Result> reviewsList) {
        this.context = context;
        this.reviewsList = reviewsList;
    }

    @Override
    public MovieReviewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView movieReviewTextView=new TextView(context);
        return new MovieReviewsViewHolder(movieReviewTextView);
    }

    @Override
    public void onBindViewHolder(MovieReviewsViewHolder holder, int position) {
        GradientDrawable gd = new GradientDrawable();
        if(position%2==0)
            gd.setColor(0xFF00FF00); // Changes this drawbale to use a single color instead of a gradient
        gd.setCornerRadius(10);
        gd.setStroke(5, 0xFF000000);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10,10,10,10);
        holder.movieReviewTextView
                .setText(reviewsList.get(position).content+"\n\n  "+reviewsList.get(position).author);
        holder.movieReviewTextView.setBackground(gd);
        holder.movieReviewTextView.setPadding(50,50,50,50);
        holder.movieReviewTextView.setLayoutParams(params);

    }

    @Override
    public int getItemCount() {
        return reviewsList.size();
    }


    public class MovieReviewsViewHolder extends RecyclerView.ViewHolder {
        TextView movieReviewTextView;

        MovieReviewsViewHolder(View view) {
            super(view);
            movieReviewTextView=(TextView) view;
        }
    }
}
