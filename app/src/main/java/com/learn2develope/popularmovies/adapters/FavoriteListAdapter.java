package com.learn2develope.popularmovies.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.learn2develope.popularmovies.FavoritesActivity;
import com.learn2develope.popularmovies.NetworkUtils.RetrofitNetworkUtils;
import com.learn2develope.popularmovies.R;
import com.learn2develope.popularmovies.database.DatabaseModel;

import java.util.List;

/**
 * Created by Ibrahim Elazb on 1/26/2018.
 */

//adapt the list of favorite movies
public class FavoriteListAdapter extends RecyclerView.Adapter<FavoriteListAdapter.FavoritesViewHolder> {

    List favorites;

    public FavoriteListAdapter(List favorites) {
        this.favorites = favorites;
    }

    @Override
    public FavoritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_list_item, parent, false);
        return new FavoritesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoritesViewHolder holder, int position) {
        Object item = favorites.get(position);
        DatabaseModel model = (DatabaseModel) item;
        holder.nameTextView.setText(model.getMovieName());
        if (model.getType().equals(FavoritesActivity.FAVORITE_MOVIE_TYPE))
            holder.categoryTextView.setText(RetrofitNetworkUtils.MOVIES_CATEGORY);
        if (model.getType().equals(FavoritesActivity.FAVORITE_TV_TYPE))
            holder.categoryTextView.setText(RetrofitNetworkUtils.TV_CATEGORY);
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    public class FavoritesViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        TextView categoryTextView;
        View listItemView;

        FavoritesViewHolder(View view) {
            super(view);
            listItemView=view;
            nameTextView = view.findViewById(R.id.favorite_item_name);
            categoryTextView = view.findViewById(R.id.favorite_item_category);
        }
    }
}
