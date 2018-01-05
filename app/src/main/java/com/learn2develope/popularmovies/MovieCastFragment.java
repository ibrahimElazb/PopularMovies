package com.learn2develope.popularmovies;

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
import android.widget.Toast;

import com.learn2develope.popularmovies.adapters.MovieCastAdapter;
import com.learn2develope.popularmovies.databinding.FragmentMovieCastBinding;
import com.learn2develope.popularmovies.model.movies.movieCast.Cast;
import com.learn2develope.popularmovies.model.movies.movieCast.MovieCast;
import com.learn2develope.popularmovies.retrofitUtils.RetrofitNetworkUtils;

import java.util.List;

public class MovieCastFragment extends Fragment implements RetrofitNetworkUtils.onLoadingHandler{

    FragmentMovieCastBinding movieCastBinding;
    private int mMovie_id;

   // private OnFragmentInteractionListener mListener;

    public static MovieCastFragment newInstance(int movie_id) {
        MovieCastFragment fragment = new MovieCastFragment();
        Bundle args = new Bundle();
        args.putInt(DetailsActivity.ARG_MOVIE_ID, movie_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMovie_id=getArguments().getInt(DetailsActivity.ARG_MOVIE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        movieCastBinding= DataBindingUtil
                .inflate(getActivity().getLayoutInflater(),R.layout.fragment_movie_cast,container,false);
        RecyclerView.LayoutManager gridlayout=new GridLayoutManager(getActivity(),2);
        movieCastBinding.rvMovieCast.setLayoutManager(gridlayout);
        View fragmentView=movieCastBinding.getRoot();
        return fragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new RetrofitNetworkUtils().getMovieCast(this,mMovie_id);
    }

    @Override
    public void onLoadingStart() {
        movieCastBinding.rvMovieCast.setVisibility(View.INVISIBLE);
        movieCastBinding.loadingIndicator.getRoot().setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadCompletedSuccessfully(List results) {
        List<Cast> castList=results;
        MovieCastAdapter movieCastAdapter=new MovieCastAdapter(getActivity(),castList);
        movieCastBinding.rvMovieCast.setAdapter(movieCastAdapter);
        movieCastBinding.rvMovieCast.setVisibility(View.VISIBLE);
        movieCastBinding.loadingIndicator.getRoot().setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoadFailed(String errorMessage) {
        movieCastBinding.loadingIndicator.getRoot().setVisibility(View.INVISIBLE);
        movieCastBinding.tvShowError.setText(errorMessage);
    }

    /*    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

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
    }*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
*/
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
