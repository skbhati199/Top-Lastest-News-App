package com.computerprogramming.toplatestnews.api;

import com.computerprogramming.toplatestnews.model.NewsModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Skbhati on 04-08-2017.
 */

public interface WebAPI {

    @GET("v1/articles?source=the-next-web&sortBy=latest&apiKey=c0d3ba5d281c43f198cbe7ade4c7be3e")
    Call<NewsModel> getArticles();
}
