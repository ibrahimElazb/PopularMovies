package com.learn2develope.popularmovies;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.learn2develope.popularmovies.NetworkUtils.NetworkConnectivity;
import com.learn2develope.popularmovies.NetworkUtils.RetrofitNetworkUtils;
import com.learn2develope.popularmovies.adapters.SearchAdapter;
import com.learn2develope.popularmovies.databinding.FragmentSearchResultBinding;

import java.util.List;

public class SearchResultFragment extends Fragment implements RetrofitNetworkUtils.onLoadingHandler {

    FragmentSearchResultBinding fragmentSearchResultBinding;
    SearchAdapter searchAdapter;

    int pageNumber;
    String category;
    String query;

    public static SearchResultFragment newInstance(String query,String category,int pageNumber) {
        SearchResultFragment fragment = new SearchResultFragment();
        Bundle args = new Bundle();
        args.putInt(RetrofitNetworkUtils.PAGE_NUMBER, pageNumber);
        args.putString(RetrofitNetworkUtils.LIST_MAIN_CATEGORY,category);
        args.putString("query",query);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        pageNumber = bundle.getInt(RetrofitNetworkUtils.PAGE_NUMBER);
        category=bundle.getString(RetrofitNetworkUtils.LIST_MAIN_CATEGORY);
        query=bundle.getString("query");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentSearchResultBinding=DataBindingUtil.inflate(inflater, R.layout.fragment_search_result, container, false);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        fragmentSearchResultBinding.rvSearchList.setLayoutManager(layoutManager);
        View fragmentView = fragmentSearchResultBinding.getRoot();
        return fragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (NetworkConnectivity.isNetworkConnectivityAvailable(getActivity())) {
            if (category.equals(RetrofitNetworkUtils.MOVIES_CATEGORY))
           RetrofitNetworkUtils.getMovieSearchResult(this,query,pageNumber);
            else if (category.equals(RetrofitNetworkUtils.TV_CATEGORY))
                RetrofitNetworkUtils.getTvShowSearchResult(this,query,pageNumber);
            else if (category.equals(RetrofitNetworkUtils.ACTORS_CATEGORY))
                RetrofitNetworkUtils.getActorSearchResult(this,query,pageNumber);
        }
    }

    @Override
    public void onLoadingStart() {
        fragmentSearchResultBinding.pbLoadingIndication.getRoot().setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadCompletedSuccessfully(List results) {
            searchAdapter=new SearchAdapter(getActivity(),results,category);
        fragmentSearchResultBinding.pbLoadingIndication.getRoot().setVisibility(View.INVISIBLE);
        fragmentSearchResultBinding.rvSearchList.setVisibility(View.VISIBLE);
        fragmentSearchResultBinding.rvSearchList.setAdapter(searchAdapter);
    }

    @Override
    public void onLoadFailed(String errorMessage) {
        fragmentSearchResultBinding.pbLoadingIndication.getRoot().setVisibility(View.INVISIBLE);
        fragmentSearchResultBinding.tvShowError.setText(errorMessage);
        fragmentSearchResultBinding.tvShowError.setVisibility(View.VISIBLE);
    }
}
