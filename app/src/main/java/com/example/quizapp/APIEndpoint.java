package com.example.quizapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIEndpoint {

    @GET("type=multiple")
    Call<APIResponse> getQuestions(@Query("category") int category,
                                   @Query("amount") int amount);
}
