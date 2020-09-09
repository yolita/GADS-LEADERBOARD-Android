package com.example.gadsleaderboard.Services;
import com.example.gadsleaderboard.DataModel.FormModel;
import com.example.gadsleaderboard.DataModel.LearnerModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface GadsApi {
    @GET("api/skilliq")
    Call<List<LearnerModel>> getSkillIQ();

    @GET("api/hours")
    Call<List<LearnerModel>> getHrs();

    @FormUrlEncoded
    @POST("https://docs.google.com/forms/d/e/1FAIpQLScNlm83i8cM8iEbzwE29voahc0dxclTJ2sb-wKr_hLPfKRxgQ/formResponse")
    Call<Void> save(
            @Field("entry.2005620554") String firstname,
            @Field("entry.310596729")  String lastname,
            @Field("entry.1045781291") String email,
            @Field("entry.1166974658") String link
            );
}
