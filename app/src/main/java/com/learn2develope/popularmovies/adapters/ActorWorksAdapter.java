package com.learn2develope.popularmovies.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.learn2develope.popularmovies.R;
import com.learn2develope.popularmovies.model.actors.actorWorks.Cast;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by Ibrahim Elazb on 1/24/2018.
 */

//to show the list of works fro the actor
public class ActorWorksAdapter extends RecyclerView.Adapter<ActorWorksAdapter.ActorWorksViewHolder>{


    List<Cast> worksList;

    public ActorWorksAdapter(List<Cast> worksList){
        this.worksList=worksList;
    }

    @Override
    public ActorWorksAdapter.ActorWorksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_movie,parent,false);
        return new ActorWorksViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ActorWorksViewHolder holder, int position) {
        if (worksList.get(position).mediaType.equals("movie"))//if this work is movie
            holder.castTitleTextView.setText(worksList.get(position).title+"("+worksList.get(position).character+")");
        else
            holder.castTitleTextView.setText(worksList.get(position).name+"("+worksList.get(position).character+")");

        String imageUrl="https://image.tmdb.org/t/p/w500/"+worksList.get(position).posterPath;
        Picasso.with(holder.listItemView.getContext()).load(imageUrl)
                .into(holder.castImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.imageLoadingProgressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError() {
                        holder.imageLoadingProgressBar.setVisibility(View.INVISIBLE);
                        holder.castImageView.setImageResource(R.drawable.ic_no_image);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return worksList.size();
    }



    public class ActorWorksViewHolder extends RecyclerView.ViewHolder{


        TextView castTitleTextView;
        ImageView castImageView;
        ProgressBar imageLoadingProgressBar;
        View listItemView;
        public ActorWorksViewHolder(View view){
            super(view);
            listItemView=view;
            castTitleTextView=(TextView)view.findViewById(R.id.tv_cast_title);
            castImageView=(ImageView)view.findViewById(R.id.iv_movie_cast_image);
            imageLoadingProgressBar=(ProgressBar)view.findViewById(R.id.pb_image_loading);
        }
    }
}
