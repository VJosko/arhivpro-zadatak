package com.vudrag.arhivpro;

import com.vudrag.arhivpro.ulan.SubjectDbpedia;
import com.vudrag.arhivpro.ulan.SubjectInfo;
import com.vudrag.arhivpro.ulan.Vocabulary;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface Api {

    @FormUrlEncoded
    @POST("ULANGetTermMatch")
    Call<Vocabulary> getSubjects(@Field("name") String name, @Field("roleid") String roleid, @Field("nationid") String nationid);

    @FormUrlEncoded
    @POST("ULANGetSubject")
    Call<SubjectInfo> getSubjectInfo(@Field("subjectID") String subjectId);

    @GET()
    Call<String> getThumbnail(@Url String url);
}
