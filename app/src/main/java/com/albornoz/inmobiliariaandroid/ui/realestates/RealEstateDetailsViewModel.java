package com.albornoz.inmobiliariaandroid.ui.realestates;

import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.albornoz.inmobiliariaandroid.modelo.Inmueble;
import com.albornoz.inmobiliariaandroid.request.ApiClient;

public class RealEstateDetailsViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private ApiClient api;
    private MutableLiveData<Inmueble> iMutable;

    public RealEstateDetailsViewModel() {
        this.api = ApiClient.getApi();
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

    public void setDisponible(boolean b) {
        Log.d("curabichera", "setDisponible: "+b);
        Inmueble i = iMutable.getValue();
        i.setEstado(b);
        api.actualizarInmueble(i);
        iMutable.setValue(i);
    }
}