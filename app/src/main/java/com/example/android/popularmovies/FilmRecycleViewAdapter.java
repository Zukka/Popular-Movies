package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class FilmRecycleViewAdapter extends RecyclerView.Adapter<FilmRecycleViewAdapter.FilmsViewHolder> {

    private List<Film> mFilmData;
    private Context mContext;

    public FilmRecycleViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public class FilmsViewHolder extends RecyclerView.ViewHolder {
        ImageView filmImage;
        FilmsViewHolder(View itemView) {
            super(itemView);
            filmImage = itemView.findViewById(R.id.film_photo);
        }
    }
    @Override
    public FilmsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.film_card, parent, false);

        FilmsViewHolder vh = new FilmsViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(FilmsViewHolder holder, int position) {
        final Film film = mFilmData.get(position);
        Picasso.with( mContext ).load( film.PosterURL).into( holder.filmImage );

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentShowDetails = new Intent(mContext, DetailsActivity.class);
                intentShowDetails.putExtra("FilmId", film.Id);
                intentShowDetails.putExtra("FilmName", film.Title);
                intentShowDetails.putExtra("FilmPosterURL", film.PosterURL);
                intentShowDetails.putExtra("FilmOverView", film.OverView);
                intentShowDetails.putExtra("FilmVoteAverage", film.VoteAverage);
                intentShowDetails.putExtra("FilmReleaseDate", film.ReleaseDate);
                mContext.startActivity(intentShowDetails);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (null == mFilmData) return 0;
        return mFilmData.size();
    }

    public void setFilmData(List<Film> filmData) {
        mFilmData = filmData;
        notifyDataSetChanged();
    }
}
