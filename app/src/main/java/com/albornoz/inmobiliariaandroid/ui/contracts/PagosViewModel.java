package com.albornoz.inmobiliariaandroid.ui.contracts;

import android.os.Bundle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.albornoz.inmobiliariaandroid.modelo.Contrato;
import com.albornoz.inmobiliariaandroid.modelo.Inmueble;
import com.albornoz.inmobiliariaandroid.modelo.Pago;
import com.albornoz.inmobiliariaandroid.request.ApiClient;

import java.util.List;

public class PagosViewModel extends ViewModel {
    private ApiClient api;
    private MutableLiveData<List<Pago>> pagosMutable;

    public PagosViewModel() {
        this.api = ApiClient.getApi();
    }

    public LiveData<List<Pago>> getPagosMutable() {
        if (pagosMutable == null) {
            pagosMutable = new MutableLiveData<>();
        }
        return pagosMutable;
    }

    public void setPagosMutable(Bundle b) {
        this.pagosMutable.setValue(api.obtenerPagos((Contrato)b.getSerializable("contrato")));
    }
}