package com.pi.appfilme.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.pi.appfilme.model.filme.BuscaEBreve.ResultFilme;

import java.util.List;

public class ViewModelTop extends AndroidViewModel {
    private MutableLiveData<List<ResultFilme>> mutableLiveDataTop = new MutableLiveData<>();
    public ViewModelTop(@NonNull Application application) {
        super(application);
    }
}
