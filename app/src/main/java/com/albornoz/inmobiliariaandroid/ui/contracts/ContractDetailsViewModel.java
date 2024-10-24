package com.albornoz.inmobiliariaandroid.ui.contracts;

import android.app.Application;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.albornoz.inmobiliariaandroid.modelo.Contrato;
import com.albornoz.inmobiliariaandroid.modelo.Inmueble;
import com.albornoz.inmobiliariaandroid.request.ApiClientRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContractDetailsViewModel extends AndroidViewModel {
    private ApiClientRetrofit.InmobiliariaService service;
    private MutableLiveData<Contrato> cMutable;

    public ContractDetailsViewModel(@NonNull Application application) {
        super(application);
        service = ApiClientRetrofit.getInmobiliariaService(getApplication());
    }

    public void setContrato(Bundle b) {
        //cMutable.setValue(api.obtenerContratoVigente((Inmueble)b.getSerializable("realEstate")));
        Inmueble i = (Inmueble) b.getSerializable("realEstate");
        service.getContratoActualDeInmueble(i.getId()).enqueue(new Callback<Contrato>() {
            @Override
            public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                if (response.isSuccessful()) {
                    cMutable.setValue(response.body());
                } else {
                    Toast.makeText(getApplication(), "No se pudo obtener el contrato", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Contrato> call, Throwable throwable) {
                Toast.makeText(getApplication(), "Error en el servidor", Toast.LENGTH_LONG).show();
            }
        });
    }

    public LiveData<Contrato> getContratoMutable() {
        if (cMutable == null) {
            cMutable = new MutableLiveData<>();
        }
        return cMutable;
    }

    /*public void openPagos(View root) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("contrato", cMutable.getValue());
        Navigation.findNavController(root).navigate(R.id.pagosFragment, bundle);
    }*/
}