package com.example.stepappv4.ui.Day;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DayViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public DayViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is day fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}