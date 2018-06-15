package com.example.android.popularmovies;

import android.content.Context;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ReviewRecycleViewAdapter extends RecyclerView.Adapter<ReviewRecycleViewAdapter.ReviewViewHolder> {

    private List<Review> mFilmReview;
    private Context mContext;

    public ReviewRecycleViewAdapter(Context mContext) { this.mContext = mContext; }

    public class ReviewViewHolder extends  RecyclerView.ViewHolder {
        TextView authorReview;
        TextView contentReview;

        ReviewViewHolder(View itemView) {
            super(itemView);
            authorReview = itemView.findViewById(R.id.author_review);
            contentReview = itemView.findViewById(R.id.content_review);
        }
    }

    @Override
    public ReviewRecycleViewAdapter.ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_card, parent, false);

        ReviewRecycleViewAdapter.ReviewViewHolder vhReview = new ReviewRecycleViewAdapter.ReviewViewHolder(v);
        return vhReview;
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        final Review review = mFilmReview.get(position);
        holder.authorReview.setText();
    }

    @Override
    public int getItemCount() {
        if (null == mFilmReview) return 0;
        return mFilmReview.size();
    }

    public void setFilmData(List<Review> filmReview) {
        mFilmReview = filmReview;
        notifyDataSetChanged();
    }
}
