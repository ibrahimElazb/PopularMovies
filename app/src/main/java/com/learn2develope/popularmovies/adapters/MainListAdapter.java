package com.learn2develope.popularmovies.adapters;

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

import com.learn2develope.popularmovies.MainActivity;
import com.learn2develope.popularmovies.R;
import com.learn2develope.popularmovies.database.DatabaseModel;
import com.learn2develope.popularmovies.NetworkUtils.RetrofitNetworkUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ibrahim Elazb on 12/23/2017.
 */

//used for adapting the list of movies,tv shows and actors in the main activity

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.MovieViewHolder> {

    List mResultList;
    String mCategory;
    onListItemClickListener listener;

    public MainListAdapter(onListItemClickListener listener,List resultList, String category){
        this.listener=listener;
        mResultList=resultList;
        mCategory=category;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItemView=LayoutInflater
                .from(parent.getContext()).inflate(R.layout.list_item_main,parent,false);
        return new MovieViewHolder(listItemView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        if(mCategory.equals(RetrofitNetworkUtils.MOVIES_CATEGORY)){//if you show a list of movies
            bindingMoviesList(holder,position);
        }else if (mCategory.equals(RetrofitNetworkUtils.TV_CATEGORY)){//if you show a list of tv shows
            bindingTvList(holder,position);
        }else if(mCategory.equals(RetrofitNetworkUtils.ACTORS_CATEGORY)){//if you show a list of actors(people)
            bindingActorsList(holder,position);
        }else{//if you are offline and there is no internet connection it reads from database
            DatabaseModel model=(DatabaseModel)mResultList.get(position);
            holder.movieTitleTextView.setText(model.getMovieName());
            holder.itemView.setTag(model.getMovieId());
            holder.imageLoadingProgressbar.setVisibility(View.INVISIBLE);
            holder.moviePosterImageView.setImageResource(R.drawable.ic_no_image);
        }
    }

    public void bindingMoviesList(final MovieViewHolder holder, int position){//if you are showing a list of movies
        com.learn2develope.popularmovies.model.movies.Result movieResult=(com.learn2develope.popularmovies.model.movies.Result)mResultList.get(position);
        String movieTitle=movieResult.title;
        String moviePosterPath=movieResult.posterPath;
       holder.movieTitleTextView.setText(movieTitle);
       String imageUrl="https://image.tmdb.org/t/p/w500/"+moviePosterPath;
        holder.imageLoadingProgressbar.setVisibility(View.VISIBLE);
        Picasso.with(holder.listItemView.getContext()).load(imageUrl)
                .into(holder.moviePosterImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.imageLoadingProgressbar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError() {
                        holder.imageLoadingProgressbar.setVisibility(View.INVISIBLE);
                        holder.moviePosterImageView.setImageResource(R.drawable.ic_no_image);
                    }
                });
        holder.itemView.setTag(movieResult.id);
    }

    public void bindingTvList(final MovieViewHolder holder, int position){//if you are showing a list of tv shows
        com.learn2develope.popularmovies.model.tv.Result tvResult=(com.learn2develope.popularmovies.model.tv.Result)mResultList.get(position);
        String tvTitle=tvResult.name;
        String tvShowPosterPath=tvResult.posterPath;
        holder.movieTitleTextView.setText(tvTitle);
        String imageUrl="https://image.tmdb.org/t/p/w500/"+tvShowPosterPath;

        Picasso.with(holder.listItemView.getContext()).load(imageUrl)
                .into(holder.moviePosterImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.imageLoadingProgressbar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError() {
                        holder.imageLoadingProgressbar.setVisibility(View.INVISIBLE);
                        holder.moviePosterImageView.setImageResource(R.drawable.ic_no_image);
                    }
                });
        holder.itemView.setTag(tvResult.id);
        holder.imageLoadingProgressbar.setVisibility(View.INVISIBLE);
        holder.moviePosterImageView.setVisibility(View.VISIBLE);
    }

    public void bindingActorsList(final MovieViewHolder holder, int position){//if you are showing a list of actors(people)
        com.learn2develope.popularmovies.model.actors.Result actorResult=(com.learn2develope.popularmovies.model.actors.Result)mResultList.get(position);
        String actorName=actorResult.name;
        String actorProfilePath=actorResult.profilePath;
        holder.movieTitleTextView.setText(actorName);
        String imageUrl="https://image.tmdb.org/t/p/w500/"+actorProfilePath;
        Picasso.with(holder.listItemView.getContext()).load(imageUrl)
                .into(holder.moviePosterImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.imageLoadingProgressbar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError() {
                        holder.imageLoadingProgressbar.setVisibility(View.INVISIBLE);
                        holder.moviePosterImageView.setImageResource(R.drawable.ic_no_image);
                    }
                });
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
        View listItemView;
        public MovieViewHolder(View listItemView){
            super(listItemView);
            moviePosterImageView=(ImageView)listItemView.findViewById(R.id.iv_movie_poster);
            movieTitleTextView=(TextView)listItemView.findViewById(R.id.tv_movie_title);
            imageLoadingProgressbar=(ProgressBar)listItemView.findViewById(R.id.pb_image_loading);
            popUpMenuImageButton=(ImageButton)listItemView.findViewById(R.id.ib_popup_menu);
            this.listItemView=listItemView;

            popUpMenuImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {//when you ckick the popUp menu (to show a specific type of information)
                    showPopUpMenu(view);
                }
            });

            listItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {//when you clisk directly on a list item (to show all information)
                            listener.onClickListener(Integer.parseInt(itemView.getTag().toString()), MainActivity.SHOW_ALL_INFORMATION);
                }
            });

        }

        public void showPopUpMenu(View view) {
            PopupMenu popupMenu=new PopupMenu(view.getContext(),view);

            //each category (movie, tv show , actor) has its popUp menu
            if (mCategory.equals(RetrofitNetworkUtils.MOVIES_CATEGORY))
                popupMenu.inflate(R.menu.movie_pop_up_menu);
            else if (mCategory.equals(RetrofitNetworkUtils.TV_CATEGORY))
                popupMenu.inflate(R.menu.tv_pop_up_menu);
            else if (mCategory.equals(RetrofitNetworkUtils.ACTORS_CATEGORY))
                popupMenu.inflate(R.menu.actors_pop_up_menu);

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()){//what type of a specific information you want to show
                        case R.id.show_details_menu_item:
                            listener.onClickListener(Integer.parseInt(itemView.getTag().toString()), MainActivity.SHOW_DETAILS);
                            break;
                        case R.id.show_reviews_menu_item:
                            listener.onClickListener(Integer.parseInt(itemView.getTag().toString()),MainActivity.SHOW_REVIEWS);
                            break;
                        case R.id.show_cast_menu_item:
                            listener.onClickListener(Integer.parseInt(itemView.getTag().toString()),MainActivity.SHOW_CAST);
                            break;
                        case R.id.show_videos_menu_item:
                            listener.onClickListener(Integer.parseInt(itemView.getTag().toString()),MainActivity.SHOW_VIDEOS);
                            break;
                        case R.id.show_works_menu_item:
                            listener.onClickListener(Integer.parseInt(itemView.getTag().toString()),MainActivity.SHOW_WORKS);
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
