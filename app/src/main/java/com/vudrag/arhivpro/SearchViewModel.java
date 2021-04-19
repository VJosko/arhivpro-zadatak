package com.vudrag.arhivpro;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vudrag.arhivpro.ulan.Vocabulary;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class SearchViewModel extends ViewModel {

    private Context context;
    public MutableLiveData<List<Vocabulary.Subject>> lSubjects = new MutableLiveData<>();
    public String input = "";
    public MutableLiveData<Vocabulary> lVocabulary = new MutableLiveData<>();
    public String terms;
    Api api;

    public SearchViewModel() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://vocabsservices.getty.edu/ULANService.asmx/")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void search() {
        Call<Vocabulary> call = api.getSubjects(input, "", "");

        call.enqueue(new Callback<Vocabulary>() {
            @Override
            public void onResponse(Call<Vocabulary> call, Response<Vocabulary> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                Vocabulary vocabulary = response.body();
                if (Integer.parseInt(vocabulary.getCount()) > 0) {
                    lVocabulary.setValue(vocabulary);
                    ArrayList<Vocabulary.Subject> subjects = save(vocabulary.getSubject().get(0));
                    if(subjects.size() != 0){
                    }
                }
            }

            @Override
            public void onFailure(Call<Vocabulary> call, Throwable t) {
            }
        });
    }

    private ArrayList<Vocabulary.Subject> save(Vocabulary.Subject subject) {

        saveInput();

        ArrayList<Vocabulary.Subject> subjects = new ArrayList<>();

        try {
            FileInputStream fis = context.openFileInput("Subjects");
            ObjectInputStream is = new ObjectInputStream(fis);
            subjects = (ArrayList<Vocabulary.Subject>) is.readObject();
            is.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        subjects.add(subject);

        try {
            FileOutputStream fos = context.openFileOutput("Subjects", Context.MODE_PRIVATE);
            try {
                ObjectOutputStream os = new ObjectOutputStream(fos);
                os.writeObject(subjects);
                os.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return subjects;
    }

    public void loadSubjects(){
        List<Vocabulary.Subject> subjects = null;
        try {
            FileInputStream fis = context.openFileInput("Subjects");
            ObjectInputStream is = new ObjectInputStream(fis);
            subjects = (List<Vocabulary.Subject>) is.readObject();
            is.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        lSubjects.setValue(subjects);
    }

    private void saveInput(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("prefs",Context.MODE_PRIVATE);
        terms = sharedPreferences.getString("terms","");
        if(terms != ""){
            terms += "\n";
        }
        terms += input;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("terms", terms);
        editor.apply();
        Log.d("TAG", "saveInput: ______" + terms);
    }

    public void loadTerms(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("prefs",Context.MODE_PRIVATE);
        terms = sharedPreferences.getString("terms","");
    }
}
