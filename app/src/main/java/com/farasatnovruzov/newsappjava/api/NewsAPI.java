package com.farasatnovruzov.newsappjava.api;


import com.farasatnovruzov.newsappjava.model.NewsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsAPI {

    @GET("v2/top-headlines")
    Call<NewsResponse> getBreakingNews(
            @Query("country")
            String countryCode,
            @Query("category")
            String category,
            @Query("page")
            int pageNumber,
            @Query("apiKey")
            String apiKey
    );


    @GET("v2/everything")
    Call<NewsResponse> searchForNews(
            @Query("q")
            String searchQuery,
            @Query("page")
            int pageNumber,
            @Query("apiKey")
            String apiKey
    );
}
