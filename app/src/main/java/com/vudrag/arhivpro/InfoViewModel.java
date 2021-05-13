package com.vudrag.arhivpro;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vudrag.arhivpro.ulan.SubjectDbpedia;
import com.vudrag.arhivpro.ulan.SubjectInfo;

import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class InfoViewModel extends ViewModel {

    public String subjectId;
    public String name = "";
    public String placeOfBirth = "";
    public String yearOfBirth = "";
    public String yearOfDeath = "";
    public String nationality = "";
    public String sex = "";
    public String role = "";
    public MutableLiveData<String> thumbnailUrl = new MutableLiveData<>();
    Api api;
    public MutableLiveData<SubjectInfo> mSubjectInfo = new MutableLiveData<>();

    public InfoViewModel(String subjectId) {
        this.subjectId = subjectId;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://vocabsservices.getty.edu/ULANService.asmx/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(SimpleXmlConverterFactory.createNonStrict(new Persister(new AnnotationStrategy())))
                .build();
        api = retrofit.create(Api.class);
    }

    public void search() {
        Call<SubjectInfo> call = api.getSubjectInfo(subjectId);

        call.enqueue(new Callback<SubjectInfo>() {
            @Override
            public void onResponse(Call<SubjectInfo> call, Response<SubjectInfo> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                SubjectInfo subjectInfo = response.body();
                if (subjectInfo != null) {
                    mSubjectInfo.setValue(subjectInfo);
                    setData(subjectInfo);
                    String name = subjectInfo.Subject.Terms.Preferred_Term.Term_Text;
                    String[] lName = name.split(", ");
                    if(lName.length > 1) {
                        name = lName[1] + "_" + lName[0];
                        name = name.replace(" ", "_");
                    }
                    String url = "https://dbpedia.org/data/" + name + ".rdf";
                    getThumbnail(url);
                }
            }

            @Override
            public void onFailure(Call<SubjectInfo> call, Throwable t) {
                Log.d("TAG", "onFailure: ________FAILED\n" + t.getMessage());
            }
        });
    }

    public void getThumbnail(String url) {
        Call<String> call = api.getThumbnail(url);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                String thumbnail = "";
                String xml = response.body();
                if (xml != null) {
                    String[] elements = xml.split(">");
                    for (String element: elements) {
                        if(element.contains("thumbnail")){
                            thumbnail = element;
                            break;
                        }
                    }
                }
                if(thumbnail != "") {
                    String[] element = thumbnail.split("\"");
                    thumbnailUrl.setValue(element[1]);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("TAG", "onFailure: ________FAILED\n" + t.getMessage());
            }
        });
    }

    private void setData(SubjectInfo subjectInfo){
        if(subjectInfo.Subject.Terms != null){
            if(subjectInfo.Subject.Terms.Preferred_Term != null){
                if(subjectInfo.Subject.Terms.Preferred_Term.Term_Text != null){
                    name = subjectInfo.Subject.Terms.Preferred_Term.Term_Text;
                }
            }
        }
        if(subjectInfo.Subject.Biographies != null){
            if(subjectInfo.Subject.Biographies.Preferred_Biography != null) {
                if (subjectInfo.Subject.Biographies.Preferred_Biography.Birth_Date != null) {
                    yearOfBirth = subjectInfo.Subject.Biographies.Preferred_Biography.Birth_Date;
                }
            }
        }
        if(subjectInfo.Subject.Biographies != null) {
            if (subjectInfo.Subject.Biographies.Preferred_Biography != null) {
                if (subjectInfo.Subject.Biographies.Preferred_Biography.Birth_Place != null) {
                    placeOfBirth = subjectInfo.Subject.Biographies.Preferred_Biography.Birth_Place;
                }
            }
        }
            if(subjectInfo.Subject.Biographies != null) {
                if (subjectInfo.Subject.Biographies.Preferred_Biography != null) {
                    if (subjectInfo.Subject.Biographies.Preferred_Biography.Death_Date != null) {
                        yearOfDeath = subjectInfo.Subject.Biographies.Preferred_Biography.Death_Date;
                    }
                }
            }
                if(subjectInfo.Subject.Biographies != null) {
                    if (subjectInfo.Subject.Biographies.Preferred_Biography != null) {
                        if (subjectInfo.Subject.Biographies.Preferred_Biography.Sex != null) {
                            sex = subjectInfo.Subject.Biographies.Preferred_Biography.Sex;
                        }
                    }
                }
        if(subjectInfo.Subject.Nationalities != null){
            if(subjectInfo.Subject.Nationalities.Preferred_Nationality != null){
                if(subjectInfo.Subject.Nationalities.Preferred_Nationality.Nationality_Code != null){
                    nationality = subjectInfo.Subject.Nationalities.Preferred_Nationality.Nationality_Code;
                    String[] x = nationality.split("/");
                    nationality = x[1];
                }
            }
        }
        if(subjectInfo.Subject.Roles != null){
            if(subjectInfo.Subject.Roles.Preferred_Role != null){
                if(subjectInfo.Subject.Roles.Preferred_Role.Role_ID != null){
                    role = subjectInfo.Subject.Roles.Preferred_Role.Role_ID;
                    String[] x = role.split("/");
                    role = x[1];
                }
            }
        }
    }
}
