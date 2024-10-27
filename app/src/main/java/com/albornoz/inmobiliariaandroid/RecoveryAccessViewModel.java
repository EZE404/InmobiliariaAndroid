package com.albornoz.inmobiliariaandroid;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.albornoz.inmobiliariaandroid.request.ApiClientRetrofit;

public class RecoveryAccessViewModel extends AndroidViewModel {
    private ApiClientRetrofit.InmobiliariaService service;

    public RecoveryAccessViewModel(@NonNull Application application) {
        super(application);
        service = ApiClientRetrofit.getInmobiliariaService(getApplication());
    }

    public void changePassword(String token) {
        Toast.makeText(getApplication(), "Token: " + token, Toast.LENGTH_LONG).show();
    }
}
