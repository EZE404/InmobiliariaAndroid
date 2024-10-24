package com.albornoz.inmobiliariaandroid.ui.tenants;

import android.app.Application;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.albornoz.inmobiliariaandroid.modelo.Inmueble;
import com.albornoz.inmobiliariaandroid.modelo.Inquilino;
import com.albornoz.inmobiliariaandroid.request.ApiClientRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TenantDetailsViewModel extends AndroidViewModel {

    private ApiClientRetrofit.InmobiliariaService service;
    private MutableLiveData<Inquilino> iMutable;

    public TenantDetailsViewModel(@NonNull Application application) {
        super(application);
        this.service = ApiClientRetrofit.getInmobiliariaService(getApplication());
    }

    public void setInquilino(Bundle b) {
        service.getInquilinoDeInmueble(((Inmueble) b.getSerializable("realEstate")).getId()).enqueue(new Callback<Inquilino>() {
            @Override
            public void onResponse(Call<Inquilino> call, Response<Inquilino> response) {
                if (response.isSuccessful()) {
                    iMutable.setValue(response.body());
                } else {
                    Toast.makeText(getApplication(), "Error en la petición", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Inquilino> call, Throwable throwable) {
                Toast.makeText(getApplication(), "Error en el servidor", Toast.LENGTH_LONG).show();
            }
        });
    }

    public LiveData<Inquilino> getInquilinoMutable() {
        if (iMutable == null) {
            iMutable = new MutableLiveData<>();
        }
        return iMutable;
    }
}