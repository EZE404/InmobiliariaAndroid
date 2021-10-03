package com.albornoz.inmobiliariaandroid.ui.profile;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.albornoz.inmobiliariaandroid.modelo.Propietario;
import com.albornoz.inmobiliariaandroid.request.ApiClient;

public class ProfileViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private ApiClient api;
    private MutableLiveData<Integer> buttonEditVisibility;
    private MutableLiveData<Integer> buttonSaveVisibility;
    private MutableLiveData<Propietario> pMutableLiveData;
    private MutableLiveData<Boolean> editEnabled;
    private MutableLiveData<String> msgMutableLiveData;
    private MutableLiveData<Integer> msgVisibility;

    public ProfileViewModel() {
        this.api = ApiClient.getApi();
    }

    public void setCurrentUser() {
        pMutableLiveData.setValue(api.obtenerUsuarioActual());
    }

    public LiveData<Propietario> getCurrentUser() {
        if (pMutableLiveData == null) {
            pMutableLiveData = new MutableLiveData<>();
        }
        return pMutableLiveData;
    }


    public LiveData<Integer> getButtonEditVisibility() {
        if (buttonEditVisibility == null) {
            buttonEditVisibility = new MutableLiveData<>();
        }
        return buttonEditVisibility;
    }

    public LiveData<Integer> getButtonSaveVisibility() {
        if (buttonSaveVisibility == null) {
            buttonSaveVisibility = new MutableLiveData<>();
        }
        return buttonSaveVisibility;
    }

    public LiveData<Boolean> getEditEnabled() {
        if (editEnabled == null) {
            editEnabled = new MutableLiveData<>();
        }
        return editEnabled;
    }

    public LiveData<String> getMsgMutableLiveData() {
        if (msgMutableLiveData == null) {
            msgMutableLiveData = new MutableLiveData<>();
        }
        return msgMutableLiveData;
    }

    public LiveData<Integer> getMsgVisibility() {
        if (msgVisibility == null) {
            msgVisibility = new MutableLiveData<>();
        }
        return msgVisibility;
    }

    public void enableEdit() {
        this.buttonEditVisibility.setValue(View.INVISIBLE);
        this.buttonSaveVisibility.setValue(View.VISIBLE);
        this.editEnabled.setValue(true);
        this.msgVisibility.setValue(View.INVISIBLE);
    }

    public void saveChanges(Propietario p) {
        api.actualizarPerfil(p);
        this.pMutableLiveData.setValue(p);
        this.buttonSaveVisibility.setValue(View.INVISIBLE);
        this.buttonEditVisibility.setValue(View.VISIBLE);
        this.editEnabled.setValue(false);
        this.msgMutableLiveData.setValue("Datos guardados.");
        this.msgVisibility.setValue(View.VISIBLE);
    }
}