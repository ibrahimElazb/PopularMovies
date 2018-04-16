package com.learn2develope.popularmovies;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.learn2develope.popularmovies.adapters.MovieVideosAdapter;
import com.learn2develope.popularmovies.databinding.FragmentMovieVideosBinding;
import com.learn2develope.popularmovies.model.movies.movieVideos.Result;
import com.learn2develope.popularmovies.NetworkUtils.RetrofitNetworkUtils;

import java.util.List;

public class MovieVideosFragment extends Fragment implements RetrofitNetworkUtils.onLoadingHandler {


    int mMovieId;
    FragmentMovieVideosBinding movieVideosBinding;

    public static MovieVideosFragment newInstance(int movieId) {
        MovieVideosFragment fragment = new MovieVideosFragment();
        Bundle args = new Bundle();
        args.putInt(MoviesDetailedActivity.ARG_MOVIE_ID, movieId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMovieId = getArguments().getInt(MoviesDetailedActivity.ARG_MOVIE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        movieVideosBinding= DataBindingUtil
                .inflate(getActivity().getLayoutInflater(),R.layout.fragment_movie_videos,container,false);
        GridLayoutManager gridLayout=new GridLayoutManager(getActivity(),2);
        movieVideosBinding.rvMovieVideo.setLayoutManager(gridLayout);
        return movieVideosBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RetrofitNetworkUtils.getMovieVideos(this,mMovieId);
    }

    @Override
    public void onLoadingStart() {
        movieVideosBinding.loadingIndicator.getRoot().setVisibility(View.VISIBLE);
        movieVideosBinding.rvMovieVideo.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoadCompletedSuccessfully(List results) {
        if (results.size()!=0){
            List<Result> videoList=results;
            movieVideosBinding.loadingIndicator.getRoot().setVisibility(View.INVISIBLE);
            movieVideosBinding.rvMovieVideo.setVisibility(View.VISIBLE);
            MovieVideosAdapter videosAdapter=new MovieVideosAdapter(videoList);
            movieVideosBinding.rvMovieVideo.setAdapter(videosAdapter);
        }else {
            movieVideosBinding.tvShowError.setText("No Videos for this movie");
            movieVideosBinding.loadingIndicator.getRoot().setVisibility(View.INVISIBLE);
            movieVideosBinding.tvShowError.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoadFailed(String errorMessage) {
        movieVideosBinding.loadingIndicator.getRoot().setVisibility(View.INVISIBLE);
        movieVideosBinding.tvShowError.setVisibility(View.VISIBLE);
        movieVideosBinding.tvShowError.setText(errorMessage);
    }
}
