package com.vudrag.arhivpro;

import com.vudrag.arhivpro.ulan.Vocabulary;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("ULANGetTermMatch")
    Call<Vocabulary> getSubjects(@Field("name") String name, @Field("roleid") String roleid, @Field("nationid") String nationid);
}
