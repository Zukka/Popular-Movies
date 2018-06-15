package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.utils.PopularMoviesConstants;

import java.util.List;

public class TrailerRecycleViewAdapter extends RecyclerView.Adapter<TrailerRecycleViewAdapter.TrailerViewHolder> {

    private List<Trailers> mFilmTrailer;
    private Context mContext;

    public TrailerRecycleViewAdapter(Context mContext) { this.mContext = mContext; }


    public class TrailerViewHolder extends  RecyclerView.ViewHolder {
        TextView mTrailerHeader;

        TrailerViewHolder(View itemView) {
            super(itemView);
            mTrailerHeader = itemView.findViewById(R.id.trailer_header);
        }
    }

    @Override
    public TrailerRecycleViewAdapter.TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer_card, parent, false);

        TrailerRecycleViewAdapter.TrailerViewHolder vhTrailer = new TrailerRecycleViewAdapter.TrailerViewHolder(v);
        return vhTrailer;
    }

    @Override
    public void onBindViewHolder(TrailerRecycleViewAdapter.TrailerViewHolder holder, int position) {
        final Trailers trailer = mFilmTrailer.get(position);
        String header = mContext.getString(R.string.trailer) + " " + position;
        holder.mTrailerHeader.setText(header);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(PopularMoviesConstants.YOU_TUBE_BASE_URL + trailer.Key));
                mContext.startActivity(browserIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (null == mFilmTrailer) return 0;
        return mFilmTrailer.size();
    }

    public void setFilmData(List<Trailers> filmTrailers) {
        mFilmTrailer = filmTrailers;
        notifyDataSetChanged();
    }
}
