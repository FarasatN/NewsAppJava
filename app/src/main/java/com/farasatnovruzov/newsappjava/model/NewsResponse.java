package com.farasatnovruzov.newsappjava.model;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.io.Serializable;
import java.util.List;


@Parcel
public class NewsResponse implements Parcelable,Serializable {

    @SerializedName("status")
    public String status;
    @SerializedName("totalResults")
    public int totalResults;
    @SerializedName("articles")
    public List<Articles> articles;


    protected NewsResponse(android.os.Parcel in) {
        status = in.readString();
        totalResults = in.readInt();
        articles = in.createTypedArrayList(Articles.CREATOR);
    }

    public static final Creator<NewsResponse> CREATOR = new Creator<NewsResponse>() {
        @Override
        public NewsResponse createFromParcel(android.os.Parcel in) {
            return new NewsResponse(in);
        }

        @Override
        public NewsResponse[] newArray(int size) {
            return new NewsResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(status);
        parcel.writeInt(totalResults);
        parcel.writeTypedList(articles);
    }

    public NewsResponse(){

    }
}
