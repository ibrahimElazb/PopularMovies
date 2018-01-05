package com.learn2develope.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.learn2develope.popularmovies.MainActivity;
import com.learn2develope.popularmovies.R;
import com.learn2develope.popularmovies.retrofitUtils.RetrofitNetworkUtils;

import java.util.List;

/**
 * Created by Ibrahim Elazb on 12/23/2017.
 */

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.MovieViewHolder> {

    List mResultList;
    Context mContext;
    String mCategory;

    public MainListAdapter(Context context, List resultList, String category){
        mContext=context;
        mResultList=resultList;
        mCategory=category;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItemView=LayoutInflater
                .from(mContext).inflate(R.layout.list_item_main,parent,false);
        return new MovieViewHolder(listItemView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        if(mCategory.equals(RetrofitNetworkUtils.MOVIES_CATEGORY)){
            bindingMoviesList(holder,position);
        }else if (mCategory.equals(RetrofitNetworkUtils.TV_CATEGORY)){
            bindingTvList(holder,position);
        }else if(mCategory.equals(RetrofitNetworkUtils.ACTORS_CATEGORY)){
            bindingActorsList(holder,position);
        }else throw new IllegalArgumentException("invalid argument");
    }

    public void bindingMoviesList(final MovieViewHolder holder, int position){
       // Log.d(MainListAdapter.class.getName(),"this is bindingMoviesList type ");
        com.learn2develope.popularmovies.model.movies.Result movieResult=(com.learn2develope.popularmovies.model.movies.Result)mResultList.get(position);
        String movieTitle=movieResult.title;
        String moviePosterPath=movieResult.posterPath;
       holder.movieTitleTextView.setText(movieTitle);
       String imageUrl="https://image.tmdb.org/t/p/w500/"+moviePosterPath;
        holder.imageLoadingProgressbar.setVisibility(View.VISIBLE);
        Glide.with(mContext.getApplicationContext()).
                load(imageUrl).
                error(R.drawable.ic_no_image).
                fitCenter().
                listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        holder.imageLoadingProgressbar.setVisibility(View.INVISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        holder.imageLoadingProgressbar.setVisibility(View.INVISIBLE);
                        return false;
                    }
                }).
                into(holder.moviePosterImageView);
        holder.itemView.setTag(movieResult.id);
    }

    public void bindingTvList(final MovieViewHolder holder, int position){
        com.learn2develope.popularmovies.model.tv.Result tvResult=(com.learn2develope.popularmovies.model.tv.Result)mResultList.get(position);
        String tvTitle=tvResult.name;
        String tvShowPosterPath=tvResult.posterPath;
        holder.movieTitleTextView.setText(tvTitle);
        String imageUrl="https://image.tmdb.org/t/p/w500/"+tvShowPosterPath;
        Glide.with(mContext.getApplicationContext()).
                load(imageUrl).
                error(R.drawable.ic_no_image).
                fitCenter().
                listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        holder.imageLoadingProgressbar.setVisibility(View.INVISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        holder.imageLoadingProgressbar.setVisibility(View.INVISIBLE);
                        return false;
                    }
                }).
                into(holder.moviePosterImageView);
        holder.itemView.setTag(tvResult.id);
        holder.imageLoadingProgressbar.setVisibility(View.INVISIBLE);
        holder.moviePosterImageView.setVisibility(View.VISIBLE);
    }

    public void bindingActorsList(final MovieViewHolder holder, int position){
        com.learn2develope.popularmovies.model.actors.Result actorResult=(com.learn2develope.popularmovies.model.actors.Result)mResultList.get(position);
        String actorName=actorResult.name;
        String actorProfilePath=actorResult.profilePath;
        holder.movieTitleTextView.setText(actorName);
        String imageUrl="https://image.tmdb.org/t/p/w500/"+actorProfilePath;
        Glide.with(mContext.getApplicationContext()).
                load(imageUrl).
                error(R.drawable.ic_no_image).
                fitCenter().
                listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        holder.imageLoadingProgressbar.setVisibility(View.INVISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        holder.imageLoadingProgressbar.setVisibility(View.INVISIBLE);
                        return false;
                    }
                }).
                into(holder.moviePosterImageView);
        holder.itemView.setTag(actorResult.id);
        holder.imageLoadingProgressbar.setVisibility(View.INVISIBLE);
        holder.moviePosterImageView.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return mResultList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder{

        ImageView moviePosterImageView;
        TextView movieTitleTextView;
        ProgressBar imageLoadingProgressbar;
        ImageButton popUpMenuImageButton;
        public MovieViewHolder(View listItemView){
            super(listItemView);
            moviePosterImageView=(ImageView)listItemView.findViewById(R.id.iv_movie_poster);
            movieTitleTextView=(TextView)listItemView.findViewById(R.id.tv_movie_title);
            imageLoadingProgressbar=(ProgressBar)listItemView.findViewById(R.id.pb_image_loading);
            popUpMenuImageButton=(ImageButton)listItemView.findViewById(R.id.ib_popup_menu);
            popUpMenuImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showMenu(view);
                }
            });
            listItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((onListItemClickListener)MainListAdapter.this.mContext)
                            .onClickListener(Integer.parseInt(itemView.getTag().toString()), MainActivity.SHOW_ALL_INFORMATION);
                }
            });

        }

        public void showMenu(View view) {
            PopupMenu popupMenu=new PopupMenu(mContext,view);

            if (mCategory.equals(RetrofitNetworkUtils.MOVIES_CATEGORY))
                popupMenu.inflate(R.menu.movie_pop_up_menu);
            else if (mCategory.equals(RetrofitNetworkUtils.TV_CATEGORY))
                popupMenu.inflate(R.menu.tv_pop_up_menu);
            else if (mCategory.equals(RetrofitNetworkUtils.ACTORS_CATEGORY))
                popupMenu.inflate(R.menu.actors_pop_up_menu);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.show_details_menu_item:
                            ((onListItemClickListener)MainListAdapter.this.mContext).onClickListener(Integer.parseInt(itemView.getTag().toString()), MainActivity.SHOW_DETAILS);
                            break;
                        case R.id.show_reviews_menu_item:
                            ((onListItemClickListener)MainListAdapter.this.mContext).onClickListener(Integer.parseInt(itemView.getTag().toString()),MainActivity.SHOW_REVIEWS);
                            break;
                        case R.id.show_cast_menu_item:
                            ((onListItemClickListener)MainListAdapter.this.mContext).onClickListener(Integer.parseInt(itemView.getTag().toString()),MainActivity.SHOW_CAST);
                            break;
                        case R.id.show_videos_menu_item:
                            ((onListItemClickListener)MainListAdapter.this.mContext).onClickListener(Integer.parseInt(itemView.getTag().toString()),MainActivity.SHOW_VIDEOS);
                            break;
                        case R.id.show_works_menu_item:
                            Toast.makeText(mContext,"show works "+getAdapterPosition(),Toast.LENGTH_SHORT).show();
                            break;
                    }
                    return true;
                }
            });
            popupMenu.show();
        }
    }

    public interface onListItemClickListener{
        void onClickListener(int itemId,String detailsType);
    }
}
