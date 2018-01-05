package com.learn2develope.popularmovies;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.learn2develope.popularmovies.databinding.FragmentMovieDetailedBinding;
import com.learn2develope.popularmovies.model.movies.movieDetails.Genre;
import com.learn2develope.popularmovies.model.movies.movieDetails.MovieDetails;
import com.learn2develope.popularmovies.retrofitUtils.RetrofitNetworkUtils;

import java.util.List;

public class MovieDetailedFragment extends Fragment implements RetrofitNetworkUtils.onLoadingHandler {

    //private OnFragmentInteractionListener mListener;

    FragmentMovieDetailedBinding fragmentMovieDetailedBinding;
    int mMovieId;



    public static MovieDetailedFragment newInstance(int movieId) {
        MovieDetailedFragment fragment = new MovieDetailedFragment();
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
        fragmentMovieDetailedBinding= DataBindingUtil
                .inflate(getActivity().getLayoutInflater(),R.layout.fragment_movie_detailed, container, false);
        View fragmentView=fragmentMovieDetailedBinding.getRoot();
        return fragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new RetrofitNetworkUtils().getMovieDetails(this,mMovieId);
    }

    // TODO: Rename method, update argument and hook method into UI event
 /*  public void onButtonPressed(Uri uri) {
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

    @Override
    public void onLoadingStart() {
        fragmentMovieDetailedBinding.svMainContainer.setVisibility(View.INVISIBLE);
        fragmentMovieDetailedBinding.loadingIndicator.getRoot().setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadCompletedSuccessfully(List results) {
        final MovieDetails movieDetails=(MovieDetails)results.get(0);
        String imageUrl="https://image.tmdb.org/t/p/w500/"+movieDetails.backdropPath;
        Glide.with(getActivity().getApplicationContext())
                .load(imageUrl).
                error(R.drawable.ic_no_image).
                fitCenter().
                listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        fragmentMovieDetailedBinding.imageLoadingProgressBar.setVisibility(View.INVISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        fragmentMovieDetailedBinding.imageLoadingProgressBar.setVisibility(View.INVISIBLE);
                        return false;
                    }
                }).
                into(fragmentMovieDetailedBinding.ivMoviePoster);
        fragmentMovieDetailedBinding.tvMovieTitle.setText(movieDetails.title);
        fragmentMovieDetailedBinding.tvVoteRate.setText(movieDetails.voteAverage+"");
        fragmentMovieDetailedBinding.ratingBar.setRating((float) movieDetails.voteAverage/2);
        StringBuilder genres=new StringBuilder();
        for (Genre genre : movieDetails.genres){
            genres.append(genre.name+"  ");
        }
        fragmentMovieDetailedBinding.tvGenres.setText(genres.toString());
        fragmentMovieDetailedBinding.tvOverview.setText(movieDetails.overview);
        fragmentMovieDetailedBinding.tvRevenue.setText(movieDetails.revenue+" $");
        fragmentMovieDetailedBinding.tvBudget.setText(movieDetails.budget+" $");

        fragmentMovieDetailedBinding.tbFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b==true)
                    Toast.makeText(getActivity(),"checked",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getActivity(),"unchecked",Toast.LENGTH_SHORT).show();
            }
        });
        if (movieDetails.homepage!=null) {
            fragmentMovieDetailedBinding.btnHomepage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(movieDetails.homepage));
                    if (intent.resolveActivity(getActivity().getPackageManager()) != null)
                        startActivity(intent);
                }
            });
        }
        fragmentMovieDetailedBinding.btnImdb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String imdbUrl="http://www.imdb.com/title/"+movieDetails.imdbId;
                Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(imdbUrl));
                startActivity(intent);
            }
        });

        fragmentMovieDetailedBinding.svMainContainer.setVisibility(View.VISIBLE);
        fragmentMovieDetailedBinding.loadingIndicator.getRoot().setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoadFailed(String errorMessage) {
        fragmentMovieDetailedBinding.loadingIndicator.getRoot().setVisibility(View.INVISIBLE);
        fragmentMovieDetailedBinding.tvShowError.setText(errorMessage);
    }

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
