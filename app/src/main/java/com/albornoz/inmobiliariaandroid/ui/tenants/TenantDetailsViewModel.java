package com.albornoz.inmobiliariaandroid.ui.tenants;

import android.os.Bundle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.albornoz.inmobiliariaandroid.modelo.Inmueble;
import com.albornoz.inmobiliariaandroid.modelo.Inquilino;
import com.albornoz.inmobiliariaandroid.request.ApiClient;

public class TenantDetailsViewModel extends ViewModel {

    private ApiClient api;
    private MutableLiveData<Inquilino> iMutable;

    public TenantDetailsViewModel() {
        this.api = ApiClient.getApi();
    }

    public void setInquilino(Bundle b) {
        iMutable.setValue(api.obtenerInquilino((Inmueble)b.getSerializable("realEstate")));
    }

    public LiveData<Inquilino> getInquilinoMutable() {
        if (iMutable == null) {
            iMutable = new MutableLiveData<>();
        }
        return iMutable;
    }
}