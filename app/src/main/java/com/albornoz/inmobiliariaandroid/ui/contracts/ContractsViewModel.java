package com.albornoz.inmobiliariaandroid.ui.contracts;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.albornoz.inmobiliariaandroid.modelo.Inmueble;
import com.albornoz.inmobiliariaandroid.request.ApiClient;
import com.albornoz.inmobiliariaandroid.request.ApiClientRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContractsViewModel extends AndroidViewModel {
    private ApiClientRetrofit.InmobiliariaService service;
    private MutableLiveData<List<Inmueble>> inmueblesMutable;

    public ContractsViewModel(@NonNull Application application) {
        super(application);
        service = ApiClientRetrofit.getInmobiliariaService(getApplication());
    }


    public LiveData<List<Inmueble>> getRealEstatesMutable() {
        if (inmueblesMutable == null) {
            inmueblesMutable = new MutableLiveData<>();
        }
        return inmueblesMutable;
    }

    public void setInmueblesMutable() {
        service.getInmueblesWithContractsByPropietario().enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if (response.isSuccessful()) {
                    inmueblesMutable.setValue(response.body());
                } else {
                    inmueblesMutable.setValue(null);
                    Toast.makeText(getApplication(), "Error al obtener inmuebles", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable throwable) {

            }
        });
    }
}