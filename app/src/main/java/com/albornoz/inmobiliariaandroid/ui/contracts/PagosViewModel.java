package com.albornoz.inmobiliariaandroid.ui.contracts;

import android.app.Application;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.albornoz.inmobiliariaandroid.modelo.Contrato;
import com.albornoz.inmobiliariaandroid.modelo.Pago;
import com.albornoz.inmobiliariaandroid.request.ApiClientRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagosViewModel extends AndroidViewModel {
    private ApiClientRetrofit.InmobiliariaService service;
    private MutableLiveData<List<Pago>> pagosMutable;

    public PagosViewModel(@NonNull Application application) {
        super(application);
        service = ApiClientRetrofit.getInmobiliariaService(getApplication());
    }

    public LiveData<List<Pago>> getPagosMutable() {
        if (pagosMutable == null) {
            pagosMutable = new MutableLiveData<>();
        }
        return pagosMutable;
    }

    public void setPagosMutable(Bundle b) {
        Contrato contrato = (Contrato) b.getSerializable("contrato");
        service.getPagosDeContrato(contrato.getId()).enqueue(new Callback<List<Pago>>() {
            @Override
            public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {
                pagosMutable.setValue(response.body());
            }
            @Override
            public void onFailure(Call<List<Pago>> call, Throwable t) {
                pagosMutable.setValue(null);
                Toast.makeText(getApplication(), "Error al obtener los pagos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}