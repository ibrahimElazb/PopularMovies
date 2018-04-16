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
import com.learn2develope.popularmovies.database.DatabaseModel;
import com.learn2develope.popularmovies.database.MovieDatabaseOperations;
import com.learn2develope.popularmovies.databinding.FragmentMovieDetailedBinding;
import com.learn2develope.popularmovies.model.movies.movieDetails.Genre;
import com.learn2develope.popularmovies.model.movies.movieDetails.MovieDetails;
import com.learn2develope.popularmovies.NetworkUtils.RetrofitNetworkUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieInfoDetailsFragment extends Fragment implements RetrofitNetworkUtils.onLoadingHandler {

    //private OnFragmentInteractionListener mListener;

    FragmentMovieDetailedBinding fragmentBinding;
    int mMovieId;


    public static MovieInfoDetailsFragment newInstance(int movieId) {
        MovieInfoDetailsFragment fragment = new MovieInfoDetailsFragment();
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
        fragmentBinding = DataBindingUtil
                .inflate(getActivity().getLayoutInflater(), R.layout.fragment_movie_detailed, container, false);
        View fragmentView = fragmentBinding.getRoot();
        return fragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RetrofitNetworkUtils.getMovieDetails(this, mMovieId);
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
        fragmentBinding.svMainContainer.setVisibility(View.INVISIBLE);
        fragmentBinding.loadingIndicator.getRoot().setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadCompletedSuccessfully(List results) {
        final MovieDetails movieDetails = (MovieDetails) results.get(0);
        String imageUrl = "https://image.tmdb.org/t/p/w500/" + movieDetails.backdropPath;
        Picasso.with(getActivity()).load(imageUrl)
                .into(fragmentBinding.ivMoviePoster, new Callback() {
                    @Override
                    public void onSuccess() {
                        fragmentBinding.imageLoadingProgressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError() {
                        fragmentBinding.imageLoadingProgressBar.setVisibility(View.INVISIBLE);
                        fragmentBinding.ivMoviePoster.setImageResource(R.drawable.ic_no_image);
                    }
                });
        fragmentBinding.tvMovieTitle.setText(movieDetails.title);
        fragmentBinding.tvVoteRate.setText(movieDetails.voteAverage + "");
        fragmentBinding.ratingBar.setRating((float) movieDetails.voteAverage / 2);
        StringBuilder genres = new StringBuilder();
        for (Genre genre : movieDetails.genres) {
            genres.append(genre.name + "  ");
        }
        fragmentBinding.tvGenres.setText(genres.toString());
        fragmentBinding.tvOverview.setText(movieDetails.overview);
        fragmentBinding.tvRevenue.setText(movieDetails.revenue + " $");
        fragmentBinding.tvBudget.setText(movieDetails.budget + " $");

        if (MovieDatabaseOperations.isFavorite(getActivity(), mMovieId,FavoritesActivity.FAVORITE_MOVIE_TYPE))
            fragmentBinding.tbFavorite.setChecked(true);
        else
            fragmentBinding.tbFavorite.setChecked(false);

        fragmentBinding.tbFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                DatabaseModel model = new DatabaseModel();
                if (b == true) {
                    model.setMovieId(mMovieId);
                    model.setMovieName(movieDetails.title);
                    model.setType(FavoritesActivity.FAVORITE_MOVIE_TYPE);
                    MovieDatabaseOperations.insertNewFavoriteItem(getActivity(), model);
                }
                else
                   MovieDatabaseOperations.deleteFavoriteData(getActivity(),mMovieId);
                }
            });
        if(movieDetails.homepage!=null)

            {
                fragmentBinding.btnHomepage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(movieDetails.homepage));
                        if (intent.resolveActivity(getActivity().getPackageManager()) != null)
                            startActivity(intent);
                    }
                });
            }else fragmentBinding.btnHomepage.setVisibility(View.INVISIBLE);
        fragmentBinding.btnImdb.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View view){
                String imdbUrl = "http://www.imdb.com/title/" + movieDetails.imdbId;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(imdbUrl));
                startActivity(intent);
            }
            });

        fragmentBinding.svMainContainer.setVisibility(View.VISIBLE);
        fragmentBinding.loadingIndicator.getRoot().

            setVisibility(View.INVISIBLE);
        }

        @Override
        public void onLoadFailed (String errorMessage){
            fragmentBinding.loadingIndicator.getRoot().setVisibility(View.INVISIBLE);
            fragmentBinding.tvShowError.setText(errorMessage);
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
