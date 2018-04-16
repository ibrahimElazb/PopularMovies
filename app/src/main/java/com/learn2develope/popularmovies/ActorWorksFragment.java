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

import com.learn2develope.popularmovies.adapters.ActorWorksAdapter;
import com.learn2develope.popularmovies.databinding.FragmentActorWorksBinding;
import com.learn2develope.popularmovies.model.actors.actorWorks.Cast;
import com.learn2develope.popularmovies.NetworkUtils.RetrofitNetworkUtils;

import java.util.List;

public class ActorWorksFragment extends Fragment implements RetrofitNetworkUtils.onLoadingHandler{

   // private OnFragmentInteractionListener mListener;
    int mActorId;
    FragmentActorWorksBinding fragmentActorWorksBinding;

    public static ActorWorksFragment newInstance(int actorId) {
        ActorWorksFragment fragment = new ActorWorksFragment();
        Bundle args = new Bundle();
        args.putInt(ActorDetailedActivity.ARG_ACTOR_ID,actorId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mActorId=getArguments().getInt(ActorDetailedActivity.ARG_ACTOR_ID,0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentActorWorksBinding= DataBindingUtil
                .inflate(getActivity().getLayoutInflater(),R.layout.fragment_actor_works,container,false);
        RecyclerView.LayoutManager gridlayout=new GridLayoutManager(getActivity(),2);
        fragmentActorWorksBinding.rvActorWorks.setLayoutManager(gridlayout);
        View fragmentView=fragmentActorWorksBinding.getRoot();
        return fragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RetrofitNetworkUtils.getActorWorks(this,mActorId);
    }

    @Override
    public void onLoadingStart() {
        fragmentActorWorksBinding.loadingIndicator.getRoot().setVisibility(View.VISIBLE);
        fragmentActorWorksBinding.rvActorWorks.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoadCompletedSuccessfully(List results) {
        List<Cast> actorWorksList=results;
        ActorWorksAdapter tvCastAdapter = new ActorWorksAdapter(actorWorksList);
        fragmentActorWorksBinding.rvActorWorks.setAdapter(tvCastAdapter);
        fragmentActorWorksBinding.loadingIndicator.getRoot().setVisibility(View.INVISIBLE);
        fragmentActorWorksBinding.rvActorWorks.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadFailed(String errorMessage) {
        fragmentActorWorksBinding.loadingIndicator.getRoot().setVisibility(View.INVISIBLE);
        fragmentActorWorksBinding.tvShowError.setVisibility(View.VISIBLE);
        fragmentActorWorksBinding.tvShowError.setText(errorMessage);
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
    }*/
}
