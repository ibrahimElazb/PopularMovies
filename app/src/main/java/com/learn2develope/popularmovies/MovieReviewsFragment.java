package com.learn2develope.popularmovies;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.learn2develope.popularmovies.adapters.MovieReviewsAdapter;
import com.learn2develope.popularmovies.databinding.FragmentMovieReviewsBinding;
import com.learn2develope.popularmovies.model.movies.movieReviews.Result;
import com.learn2develope.popularmovies.retrofitUtils.RetrofitNetworkUtils;

import java.util.List;

public class MovieReviewsFragment extends Fragment implements RetrofitNetworkUtils.onLoadingHandler {

    int mMovieId;

    FragmentMovieReviewsBinding movieReviewsBinding;

    public static MovieReviewsFragment newInstance(int movieId) {
        MovieReviewsFragment fragment = new MovieReviewsFragment();
        Bundle args = new Bundle();
        args.putInt(DetailsActivity.ARG_MOVIE_ID, movieId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMovieId = getArguments().getInt(DetailsActivity.ARG_MOVIE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        movieReviewsBinding= DataBindingUtil
                .inflate(getActivity().getLayoutInflater(),R.layout.fragment_movie_reviews,container,false);
        RecyclerView.LayoutManager linearLayout=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        movieReviewsBinding.rvMovieReview.setLayoutManager(linearLayout);
        return movieReviewsBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RetrofitNetworkUtils.getMovieReviews(this,mMovieId);
    }

    @Override
    public void onLoadingStart() {
        movieReviewsBinding.loadingIndicator.getRoot().setVisibility(View.VISIBLE);
        movieReviewsBinding.rvMovieReview.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoadCompletedSuccessfully(List results) {
        if (results.size()!=0){
            List<Result> reviewsList=results;
            movieReviewsBinding.loadingIndicator.getRoot().setVisibility(View.INVISIBLE);
            movieReviewsBinding.rvMovieReview.setVisibility(View.VISIBLE);
            MovieReviewsAdapter reviewsAdapter=new MovieReviewsAdapter(getActivity(),reviewsList);
            movieReviewsBinding.rvMovieReview.setAdapter(reviewsAdapter);
        }else {
            movieReviewsBinding.tvShowError.setText("No Reviews fro this movie");
            movieReviewsBinding.loadingIndicator.getRoot().setVisibility(View.INVISIBLE);
            movieReviewsBinding.tvShowError.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoadFailed(String errorMessage) {
        movieReviewsBinding.loadingIndicator.getRoot().setVisibility(View.INVISIBLE);
        movieReviewsBinding.tvShowError.setVisibility(View.VISIBLE);
        movieReviewsBinding.tvShowError.setText(errorMessage);
    }
}
