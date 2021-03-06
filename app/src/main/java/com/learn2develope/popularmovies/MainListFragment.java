package com.learn2develope.popularmovies;

import android.support.v4.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.learn2develope.popularmovies.adapters.MainListAdapter;
import com.learn2develope.popularmovies.database.MovieDatabaseOperations;
import com.learn2develope.popularmovies.databinding.FragmentMainListBinding;
import com.learn2develope.popularmovies.NetworkUtils.NetworkConnectivity;
import com.learn2develope.popularmovies.NetworkUtils.RetrofitNetworkUtils;

import java.util.List;

/**
 * Created by Ibrahim Elazb on 12/24/2017.
 */

public class MainListFragment extends Fragment implements RetrofitNetworkUtils.onLoadingHandler {

    MainListAdapter moviesAdapter;
    // static Context mContext;
    FragmentMainListBinding fragmentListBinding;

    String category;
    String subCategory;
    int pageNumber;


    public static MainListFragment newInstance(String category, String subCategory, int pageNumber) {

        MainListFragment fragment = new MainListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(RetrofitNetworkUtils.LIST_MAIN_CATEGORY, category);
        bundle.putString(RetrofitNetworkUtils.LIST_SUBCATEGORY, subCategory);
        bundle.putInt(RetrofitNetworkUtils.PAGE_NUMBER, pageNumber);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Bundle bundle = getArguments();
        category = bundle.getString(RetrofitNetworkUtils.LIST_MAIN_CATEGORY);
        subCategory = bundle.getString(RetrofitNetworkUtils.LIST_SUBCATEGORY);
        pageNumber = bundle.getInt(RetrofitNetworkUtils.PAGE_NUMBER);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        fragmentListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_list, container, false);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        fragmentListBinding.rvMoviesList.setLayoutManager(layoutManager);
        View fragmentView = fragmentListBinding.getRoot();
        return fragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (NetworkConnectivity.isNetworkConnectivityAvailable(getActivity())) {
            if (category.equals(RetrofitNetworkUtils.MOVIES_CATEGORY)) {
                new RetrofitNetworkUtils().getMoviesListInfo(this, subCategory, pageNumber);
            } else if (category.equals(RetrofitNetworkUtils.TV_CATEGORY)) {
                new RetrofitNetworkUtils().getTvListInfo(this, subCategory, pageNumber);
            } else if (category.equals(RetrofitNetworkUtils.ACTORS_CATEGORY)) {
                new RetrofitNetworkUtils().getActorsListInfo(this, subCategory, pageNumber);
            }

        } else {
            List results = MovieDatabaseOperations.retrieveData(getActivity().getApplicationContext(), category, subCategory);
            moviesAdapter = new MainListAdapter((MainActivity)getActivity(), results, "offline");
            fragmentListBinding.pbLoadingIndication.getRoot().setVisibility(View.INVISIBLE);
            fragmentListBinding.rvMoviesList.setVisibility(View.VISIBLE);
            fragmentListBinding.rvMoviesList.setAdapter(moviesAdapter);
        }
    }

    @Override
    public void onLoadingStart() {
        fragmentListBinding.pbLoadingIndication.getRoot().setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadCompletedSuccessfully(List results) {
       if (pageNumber == 1) {//save the first page in database
            MovieDatabaseOperations.deletePreviousCollectionData(getActivity(), category, subCategory);
            MovieDatabaseOperations.insertNewDataCollection(getActivity(), results, category, subCategory);
        }
        moviesAdapter = new MainListAdapter((MainActivity)getActivity(), results, getArguments().getString(RetrofitNetworkUtils.LIST_MAIN_CATEGORY));
        fragmentListBinding.pbLoadingIndication.getRoot().setVisibility(View.INVISIBLE);
        fragmentListBinding.rvMoviesList.setVisibility(View.VISIBLE);
        fragmentListBinding.rvMoviesList.setAdapter(moviesAdapter);
    }

    @Override
    public void onLoadFailed(String errorMessage) {
        fragmentListBinding.pbLoadingIndication.getRoot().setVisibility(View.INVISIBLE);
        fragmentListBinding.tvShowError.setText(errorMessage);
        fragmentListBinding.tvShowError.setVisibility(View.VISIBLE);
    }
}
