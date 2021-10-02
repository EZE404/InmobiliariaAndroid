package com.albornoz.inmobiliariaandroid.ui.contracts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.albornoz.inmobiliariaandroid.modelo.Inmueble;
import com.albornoz.inmobiliariaandroid.request.ApiClient;

import java.util.List;

public class ContractsViewModel extends ViewModel {
    private ApiClient api;
    private MutableLiveData<List<Inmueble>> inmueblesMutable;

    public ContractsViewModel() {
        this.api = ApiClient.getApi();
    }

    public LiveData<List<Inmueble>> getRealEstatesMutable() {
        if (inmueblesMutable == null) {
            inmueblesMutable = new MutableLiveData<>();
        }
        return inmueblesMutable;
    }

    public void setInmueblesMutable() {
        this.inmueblesMutable.setValue(api.obtenerPropiedadesAlquiladas());
    }
}