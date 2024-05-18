package com.example.kidsgame.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Locale;

public class LanguageViewModel extends ViewModel {

    private final MutableLiveData<Locale> selectedLanguage = new MutableLiveData<>(Locale.getDefault());

    public void setLanguage(Locale locale) {
        selectedLanguage.setValue(locale);
    }

    public LiveData<Locale> getLanguage() {
        return selectedLanguage;
    }
}
