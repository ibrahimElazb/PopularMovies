package com.learn2develope.popularmovies;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.learn2develope.popularmovies.adapters.TvShowCastAdapter;
import com.learn2develope.popularmovies.databinding.FragmentTvShowCastBinding;
import com.learn2develope.popularmovies.model.tv.tvCast.Cast;
import com.learn2develope.popularmovies.NetworkUtils.RetrofitNetworkUtils;

import java.util.List;

public class TvShowCastFragment extends Fragment implements RetrofitNetworkUtils.onLoadingHandler {

   // private OnFragmentInteractionListener mListener;
    int mTvShowId;
    FragmentTvShowCastBinding fragmentTvCastBinding;

    public static TvShowCastFragment newInstance(int tvShowId) {
        TvShowCastFragment fragment = new TvShowCastFragment();
        Bundle args = new Bundle();
        args.putInt(TvShowDetailedActivity.TV_SHOW_ID, tvShowId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTvShowId=getArguments().getInt(TvShowDetailedActivity.TV_SHOW_ID,0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentTvCastBinding= DataBindingUtil
                .inflate(getActivity().getLayoutInflater(),R.layout.fragment_tv_show_cast,container,false);
        RecyclerView.LayoutManager gridlayout=new GridLayoutManager(getActivity(),2);
        fragmentTvCastBinding.rvTvShowCast.setLayoutManager(gridlayout);
        View fragmentView=fragmentTvCastBinding.getRoot();
        return fragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RetrofitNetworkUtils.getTvCast(this,mTvShowId);
    }

    @Override
    public void onLoadingStart() {
        fragmentTvCastBinding.rvTvShowCast.setVisibility(View.INVISIBLE);
        fragmentTvCastBinding.loadingIndicator.getRoot().setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadCompletedSuccessfully(List results) {
        List<Cast> tvCast=results;
        if (tvCast.size()!=0) {
            TvShowCastAdapter tvShowCastAdapter = new TvShowCastAdapter(tvCast);
            fragmentTvCastBinding.rvTvShowCast.setAdapter(tvShowCastAdapter);
            fragmentTvCastBinding.rvTvShowCast.setVisibility(View.VISIBLE);
            fragmentTvCastBinding.loadingIndicator.getRoot().setVisibility(View.INVISIBLE);
        }else {
            onLoadFailed("No Cast Here");
        }
    }

    @Override
    public void onLoadFailed(String errorMessage) {
        fragmentTvCastBinding.tvShowError.setText(errorMessage);
        fragmentTvCastBinding.tvShowError.setVisibility(View.VISIBLE);
        fragmentTvCastBinding.loadingIndicator.getRoot().setVisibility(View.INVISIBLE);
    }

    /*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    */
}
