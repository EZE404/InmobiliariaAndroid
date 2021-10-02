package com.albornoz.inmobiliariaandroid.ui.contracts;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.albornoz.inmobiliariaandroid.R;
import com.albornoz.inmobiliariaandroid.modelo.Contrato;
import com.albornoz.inmobiliariaandroid.modelo.Inmueble;
import com.albornoz.inmobiliariaandroid.request.ApiClient;

public class ContractDetailsViewModel extends ViewModel {
    private ApiClient api;
    private MutableLiveData<Contrato> cMutable;

    public ContractDetailsViewModel() {
        this.api = ApiClient.getApi();
    }

    public void setContrato(Bundle b) {
        cMutable.setValue(api.obtenerContratoVigente((Inmueble)b.getSerializable("realEstate")));
    }

    public LiveData<Contrato> getContratoMutable() {
        if (cMutable == null) {
            cMutable = new MutableLiveData<>();
        }
        return cMutable;
    }

    public void openPagos(View root) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("contrato", cMutable.getValue());
        Navigation.findNavController(root).navigate(R.id.pagosFragment, bundle);
    }
}