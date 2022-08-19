package com.farasatnovruzov.newsappjava.model;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.io.Serializable;


@Parcel
public class Source implements Parcelable,Serializable {

    @SerializedName("id")
    public String id;
    @SerializedName("name")
    public String name;

    protected Source(android.os.Parcel in) {
        id = in.readString();
        name = in.readString();
    }

    public static final Creator<Source> CREATOR = new Creator<Source>() {
        @Override
        public Source createFromParcel(android.os.Parcel in) {
            return new Source(in);
        }

        @Override
        public Source[] newArray(int size) {
            return new Source[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
    }

    public Source(){

    }
}
