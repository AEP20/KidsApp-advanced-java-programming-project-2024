package com.example.kidsgame.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {

    private final MutableLiveData<String> username = new MutableLiveData<>();

    private final MutableLiveData<Number> score = new MutableLiveData<>();

    public void setUsername(String username) {
        this.username.setValue(username);
    }

    public LiveData<String> getUsername() {
        return username;
    }

    public void setScore(Number score) {
        this.score.setValue(score);
    }

    public LiveData<Number> getScore() {
        return score;
    }

    public void clearData() {
        username.setValue(null);
    }
}
