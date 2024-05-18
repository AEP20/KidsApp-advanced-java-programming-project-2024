package com.example.kidsgame.api;

import com.example.kidsgame.model.LoginRequest;
import com.example.kidsgame.model.LoginResponse;
import com.example.kidsgame.model.RegisterResponse;
import com.example.kidsgame.model.FourChoiceQuestionResponse;
import com.example.kidsgame.model.FourChoiceQuestion;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AuthService {
    @POST("/api/auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("/api/auth/register")
    Call<RegisterResponse> register(@Body LoginRequest loginRequest);

    @GET("/api/quiz/questions")
    Call<FourChoiceQuestionResponse<FourChoiceQuestion>> getQuestions(@Query("type") String type, @Query("language") String language);
}
