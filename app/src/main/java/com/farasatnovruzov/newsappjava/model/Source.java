package com.farasatnovruzov.newsappjava.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Source implements Serializable {

    @SerializedName("id")
    public String id;
    @SerializedName("name")
    public String name;
}
