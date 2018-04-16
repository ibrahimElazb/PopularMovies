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
import com.learn2develope.popularmovies.databinding.FragmentTvShowDetailedBinding;
import com.learn2develope.popularmovies.model.tv.tvDetails.Genre;
import com.learn2develope.popularmovies.model.tv.tvDetails.TvDetails;
import com.learn2develope.popularmovies.NetworkUtils.RetrofitNetworkUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TvShowInfoDetailsFragment extends Fragment implements RetrofitNetworkUtils.onLoadingHandler {

    //private OnFragmentInteractionListener mListener;

    FragmentTvShowDetailedBinding fragmentBinding;
    int mTvShowId;

    public static TvShowInfoDetailsFragment newInstance(int tvShowId) {
        TvShowInfoDetailsFragment fragment = new TvShowInfoDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(TvShowDetailedActivity.TV_SHOW_ID,tvShowId);
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
        fragmentBinding = DataBindingUtil.inflate(getActivity().getLayoutInflater(),R.layout.fragment_tv_show_detailed,container,false);
        View fragmentView= fragmentBinding.getRoot();
        return fragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RetrofitNetworkUtils.getTvDetails(this,mTvShowId);
    }

    @Override
    public void onLoadingStart() {
        fragmentBinding.svMainContainer.setVisibility(View.INVISIBLE);
        fragmentBinding.loadingIndicator.getRoot().setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadCompletedSuccessfully(List results) {
        final TvDetails tvDetails=(TvDetails)results.get(0);
        String imageUrl="https://image.tmdb.org/t/p/w500/"+tvDetails.backdropPath;
        Picasso.with(getActivity()).load(imageUrl)
                .into(fragmentBinding.ivPoster, new Callback() {
                    @Override
                    public void onSuccess() {
                        fragmentBinding.imageLoadingProgressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError() {
                        fragmentBinding.imageLoadingProgressBar.setVisibility(View.INVISIBLE);
                        fragmentBinding.ivPoster.setImageResource(R.drawable.ic_no_image);
                    }
                });

        fragmentBinding.tvMovieTitle.setText(tvDetails.name);
        fragmentBinding.tvVoteRate.setText(tvDetails.voteAverage+"");
        fragmentBinding.ratingBar.setRating((float) tvDetails.voteAverage/2);
        StringBuilder genres=new StringBuilder();
        for (Genre genre : tvDetails.genres){
            genres.append(genre.name+"  ");
        }
        fragmentBinding.tvGenres.setText(genres.toString());
        fragmentBinding.tvOverview.setText(tvDetails.overview);

        if (MovieDatabaseOperations.isFavorite(getActivity(), mTvShowId,FavoritesActivity.FAVORITE_TV_TYPE))
            fragmentBinding.tbFavorite.setChecked(true);
        else
            fragmentBinding.tbFavorite.setChecked(false);

        fragmentBinding.tbFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                {
                    DatabaseModel model = new DatabaseModel();
                    if (b == true) {
                        model.setMovieId(mTvShowId);
                        model.setMovieName(tvDetails.name);
                        model.setType(FavoritesActivity.FAVORITE_TV_TYPE);
                        MovieDatabaseOperations.insertNewFavoriteItem(getActivity(), model);
                    }
                    else
                        MovieDatabaseOperations.deleteFavoriteData(getActivity(),mTvShowId);
                }
            }
        });
        if (tvDetails.homepage!=null) {
            fragmentBinding.btnHomepage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(tvDetails.homepage));
                    if (intent.resolveActivity(getActivity().getPackageManager()) != null)
                        startActivity(intent);
                }
            });
        }else fragmentBinding.btnHomepage.setVisibility(View.INVISIBLE);
        fragmentBinding.tvNumberSeasons.setText(tvDetails.numberOfSeasons+"");
        fragmentBinding.tvNumberEpisods.setText(tvDetails.numberOfEpisodes+"");

        fragmentBinding.svMainContainer.setVisibility(View.VISIBLE);
        fragmentBinding.loadingIndicator.getRoot().setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoadFailed(String errorMessage) {
        fragmentBinding.loadingIndicator.getRoot().setVisibility(View.INVISIBLE);
        fragmentBinding.tvShowError.setText(errorMessage);
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
        void onFragmentInteraction(Uri uri);
    }

    */
}
