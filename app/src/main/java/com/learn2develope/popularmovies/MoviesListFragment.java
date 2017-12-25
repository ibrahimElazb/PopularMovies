package com.learn2develope.popularmovies;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.learn2develope.popularmovies.databinding.FragmentMoviesListBinding;
import com.learn2develope.popularmovies.retrofitUtils.RetrofitNetworkUtils;

import java.util.List;

/**
 * Created by Ibrahim Elazb on 12/24/2017.
 */

public class MoviesListFragment extends Fragment implements RetrofitNetworkUtils.onDownloadCompleteHandler {

    public static final int MOVIES_CATEGORY=0;
    public static final int TV_CATEGORY=1;
    public static final int ACTORS_CATEGORY=2;

    MoviesListAdapter moviesAdapter;

    static Context mContext;

    FragmentMoviesListBinding fragmentListBinding;

    public static MoviesListFragment newInstance(Context context,Bundle bundle) {

        Bundle args = bundle;
        mContext=context;
        MoviesListFragment fragment = new MoviesListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        fragmentListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies_list, container, false);
        Bundle bundle = getArguments();

        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(mContext,2);
        fragmentListBinding.rvMoviesList.setLayoutManager(layoutManager);

        showLoadingIndication();
        RetrofitNetworkUtils.startDownloadingDataFromIntenet(this,bundle);
        View fragmentView = fragmentListBinding.getRoot();
        return fragmentView;
    }

    public void showLoadingIndication(){
        fragmentListBinding.pbLoadingIndication.setVisibility(View.VISIBLE);
    }

    public void showErrorIndication(String message){
        fragmentListBinding.pbLoadingIndication.setVisibility(View.INVISIBLE);
        fragmentListBinding.tvShowError.setText(message);
        fragmentListBinding.tvShowError.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDownloadCompletedSuccessfully(List results, int numberOfPages) {
        int category=getArguments().getInt(RetrofitNetworkUtils.LIST_MAIN_CATEGORY);
        moviesAdapter=new MoviesListAdapter(mContext,results,category);
        fragmentListBinding.rvMoviesList.setAdapter(moviesAdapter);
        ((onChangingNumberOfPages)mContext).numberOfPagesChanged(numberOfPages);
        fragmentListBinding.pbLoadingIndication.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onDownloadFailed(String errorMessage) {
        showErrorIndication(errorMessage);
    }


    interface onChangingNumberOfPages{
        void numberOfPagesChanged(int numberOfPages);
    }
}
