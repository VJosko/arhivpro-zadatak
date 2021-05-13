package com.vudrag.arhivpro;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class InfoViewModelFactory implements ViewModelProvider.Factory {

    private String subjectId;

    public InfoViewModelFactory(String subjectId){
        this.subjectId = subjectId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(InfoViewModel.class)){
            return (T) new InfoViewModel(subjectId);
        }
        throw new IllegalArgumentException("Uknown ViewModel class");
    }
}
