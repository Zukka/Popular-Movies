package com.example.android.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by zukka on 20/03/18.
 */

public class Film implements Parcelable{

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Film createFromParcel(Parcel in) {
            return new Film(in);
        }

        public Film[] newArray(int size) {
            return new Film[size];
        }
    };

    private int Id;
    private String Title;
    private String PosterURL;
    private String OverView;
    private String VoteAverage;
    private String ReleaseDate;

    public Film(int Id, String Title, String PosterURL, String OverView, String VoteAverage, String ReleaseDate)
    {
        this.Id = Id;
        this.Title = Title;
        this.PosterURL = PosterURL;
        this.OverView = OverView;
        this.VoteAverage = VoteAverage;
        this.ReleaseDate = ReleaseDate;
    }

    public int getId() { return Id; }
    public String getTitle() { return Title; }
    public String getPosterURL() { return PosterURL; }
    public String getOverView() { return OverView; }
    public String getVoteAverage() { return VoteAverage; }
    public String getReleaseDate() { return ReleaseDate; }

    public void setId(int id) { Id = id; }
    public void setTitle(String title) { Title = title; }
    public void setPosterURL(String posterURL) { PosterURL = posterURL; }
    public void setOverView(String overView) { OverView = overView; }
    public void setVoteAverage(String voteAverage) { VoteAverage = voteAverage; }
    public void setReleaseDate(String releaseDate) { ReleaseDate = releaseDate; }

    public Film(Parcel in){
        this.Id = in.readInt();
        this.Title = in.readString();
        this.OverView = in.readString();
        this.PosterURL = in.readString();
        this.ReleaseDate = in.readString();
        this.VoteAverage = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.Id);
        dest.writeString(this.Title);
        dest.writeString(this.OverView);
        dest.writeString(this.PosterURL);
        dest.writeString(this.ReleaseDate);
        dest.writeString(this.VoteAverage);
    }
}
