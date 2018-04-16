package com.learn2develope.popularmovies.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.learn2develope.popularmovies.ActorInfoDetailsFragment;
import com.learn2develope.popularmovies.ActorWorksFragment;

/**
 * Created by Ibrahim Elazb on 1/24/2018.
 */

//for adapting the pages (fragments) of actor (details - works)
public class ActorPagesAdapter extends FragmentStatePagerAdapter {

    int mActorId;

    public ActorPagesAdapter(FragmentManager fragmentManager, int actorId){
        super(fragmentManager);
        mActorId=actorId;
    }

    @Override
    public Fragment getItem(int position) {
        if (position==0){
            return ActorInfoDetailsFragment.newInstance(mActorId);
        }
        else if (position==1){
            return ActorWorksFragment.newInstance(mActorId);
        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position==0){
            return "Details";
        }
        else if (position==1){
            return "Works";
        }

        return super.getPageTitle(position);
    }
}
