package com.example.android.popularmovies;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class FilmRecycleViewAdapter extends RecyclerView.Adapter<FilmRecycleViewAdapter.FilmsViewHolder> {

    private List<Film> mFilmData;

    public static class FilmsViewHolder extends RecyclerView.ViewHolder {

        public TextView filmTitle;
        FilmsViewHolder(View itemView) {
            super(itemView);

            filmTitle = itemView.findViewById(R.id.film_title);
        }
    }
    @Override
    public FilmsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);

        FilmsViewHolder vh = new FilmsViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(FilmsViewHolder holder, int position) {

        Film film = mFilmData.get(position);
        holder.filmTitle.setText(film.toString());
     //   DataProvider.Content tmp=mDataset.get(position);
     //   holder.title.setText(tmp.getTitle());
     //   holder.filmTitle.setText(mFilmData[position].getItem(0).toString());
    }

    @Override
    public int getItemCount() {
        if (null == mFilmData) return 0;
        System.out.println("mFilmData:" + mFilmData.size());
        return mFilmData.size();
    }

    public void setFilmData(Film[] filmData) {
        mFilmData = filmData;
        notifyDataSetChanged();
    }
}
