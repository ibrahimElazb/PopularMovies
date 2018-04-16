package com.learn2develope.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.learn2develope.popularmovies.NetworkUtils.RetrofitNetworkUtils;
import com.learn2develope.popularmovies.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ibrahim Elazb on 1/26/2018.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder>{

    Context context;
    List searchResult;
    String category;

    public SearchAdapter(Context context, List searchSearch,String category){
        this.context=context;
        this.searchResult=searchSearch;
        this.category=category;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_item_movie,parent,false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SearchViewHolder holder, int position) {

        if (category.equals(RetrofitNetworkUtils.MOVIES_CATEGORY)){
            com.learn2develope.popularmovies.model.search.movies.Result result=(com.learn2develope.popularmovies.model.search.movies.Result)searchResult.get(position);
            String imageUrl="https://image.tmdb.org/t/p/w500/"+result.posterPath;
            Picasso.with(holder.listItemView.getContext()).load(imageUrl)
                    .into(holder.image, new Callback() {
                        @Override
                        public void onSuccess() {
                            holder.loadingProgressBar.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onError() {
                            holder.loadingProgressBar.setVisibility(View.INVISIBLE);
                            holder.image.setImageResource(R.drawable.ic_no_image);
                        }
                    });
            holder.titleTextView.setText(result.title);
            holder.itemView.setTag(result.id);
        }else if (category.equals(RetrofitNetworkUtils.TV_CATEGORY)){
            com.learn2develope.popularmovies.model.search.tvShow.Result result=(com.learn2develope.popularmovies.model.search.tvShow.Result)searchResult.get(position);
            String imageUrl="https://image.tmdb.org/t/p/w500/"+result.posterPath;
            Picasso.with(holder.listItemView.getContext()).load(imageUrl)
                    .into(holder.image, new Callback() {
                        @Override
                        public void onSuccess() {
                            holder.loadingProgressBar.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onError() {
                            holder.loadingProgressBar.setVisibility(View.INVISIBLE);
                            holder.image.setImageResource(R.drawable.ic_no_image);
                        }
                    });
            holder.titleTextView.setText(result.name);
            holder.itemView.setTag(result.id);
        }else if (category.equals(RetrofitNetworkUtils.ACTORS_CATEGORY)){
            com.learn2develope.popularmovies.model.search.actor.Result result=
                    (com.learn2develope.popularmovies.model.search.actor.Result)searchResult.get(position);
            String imageUrl="https://image.tmdb.org/t/p/w500/"+result.profilePath;
            Picasso.with(holder.listItemView.getContext()).load(imageUrl)
                    .into(holder.image, new Callback() {
                        @Override
                        public void onSuccess() {
                            holder.loadingProgressBar.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onError() {
                            holder.image.setVisibility(View.INVISIBLE);
                            holder.image.setImageResource(R.drawable.ic_no_image);
                        }
                    });
            holder.titleTextView.setText(result.name);
            holder.itemView.setTag(result.id);
        }
    }

    @Override
    public int getItemCount() {
        return searchResult.size();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView titleTextView;
        ProgressBar loadingProgressBar;
        View listItemView;
        SearchViewHolder(View view){
            super(view);
            listItemView=view;
            image=(ImageView)view.findViewById(R.id.iv_movie_cast_image);
            titleTextView =(TextView)view.findViewById(R.id.tv_cast_title);
            loadingProgressBar=(ProgressBar)view.findViewById(R.id.pb_image_loading);
        }
    }
}
