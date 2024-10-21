package com.albornoz.inmobiliariaandroid.ui.realestates;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.albornoz.inmobiliariaandroid.modelo.Inmueble;
import com.albornoz.inmobiliariaandroid.request.ApiClient;
import com.albornoz.inmobiliariaandroid.request.ApiClientRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RealEstateDetailsViewModel extends AndroidViewModel {
    private ApiClientRetrofit.InmobiliariaService service;
    private MutableLiveData<Inmueble> iMutable;
    private MutableLiveData<Boolean> disponibleCheckEnabledMutable;
    private boolean firstLoad = true;

    public RealEstateDetailsViewModel(@NonNull Application application) {
        super(application);
        this.service = ApiClientRetrofit.getInmobiliariaService(getApplication());
    }


    public void setInmueble(Bundle b) {
        iMutable.setValue((Inmueble)b.getSerializable("realEstate"));
    }

    public LiveData<Inmueble> getInmuebleMutable() {
        if (iMutable == null) {
            iMutable = new MutableLiveData<>();
        }
        return iMutable;
    }

    public LiveData<Boolean> getDisponibleCheckEnabledMutable() {
        if (disponibleCheckEnabledMutable == null) {
            disponibleCheckEnabledMutable = new MutableLiveData<>();
        }
        return disponibleCheckEnabledMutable;
    }

    public void setDisponible(boolean b) {
        // mientras se atiende la petición, el checkbox se desactiva
        disponibleCheckEnabledMutable.setValue(false);
        Inmueble i = iMutable.getValue();
        //este condicional solo sucede la primera vez, y es para que no se ejecute el cambio de estado en el servidor
        //cuando se abre la pantalla por primera vez y se carga el checkbox de manera programática.
        if (firstLoad) {
            firstLoad = false;
            disponibleCheckEnabledMutable.setValue(true);
            return;
        }
        i.setDisponible(b);
        service.actualizarDisponibilidadInmueble(i).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplication(), "Disponibilidad actualizada", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplication(), "Error en la petición", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                Toast.makeText(getApplication(), "Error en el servidor", Toast.LENGTH_LONG).show();
            }
        });
        disponibleCheckEnabledMutable.setValue(true);
    }
}