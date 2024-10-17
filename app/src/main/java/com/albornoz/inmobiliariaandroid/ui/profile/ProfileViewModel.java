package com.albornoz.inmobiliariaandroid.ui.profile;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.albornoz.inmobiliariaandroid.modelo.Propietario;
import com.albornoz.inmobiliariaandroid.request.ApiClient;
import com.albornoz.inmobiliariaandroid.request.ApiClientRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewModel extends AndroidViewModel {
    private ApiClientRetrofit.InmobiliariaService api;
    private MutableLiveData<Integer> buttonEditVisibility;
    private MutableLiveData<Integer> buttonSaveVisibility;
    private MutableLiveData<Integer> buttonDateVisibility;
    private MutableLiveData<Propietario> pMutableLiveData;
    private MutableLiveData<Boolean> editEnabled;
    private MutableLiveData<String> msgMutableLiveData;
    private MutableLiveData<Integer> msgVisibility;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        api = ApiClientRetrofit.getInmobiliariaService(application.getApplicationContext());
    }

    public void setCurrentUser() {
        // ESTO NO VA MÁS PORQUE HAY QUE USAR RETROFIT
        //pMutableLiveData.setValue(api.obtenerUsuarioActual());
        api.getPropietario().enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful()) {
                    pMutableLiveData.setValue(response.body());
                } else {
                    msgMutableLiveData.setValue("Error al obtener el propietario");
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                msgMutableLiveData.setValue("Error de conexión");
            }
        });
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
    public LiveData<Integer> getButtonDateVisibility() {
        if (buttonDateVisibility == null) {
            buttonDateVisibility = new MutableLiveData<>();
        }
        return buttonDateVisibility;
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
        this.buttonDateVisibility.setValue(View.VISIBLE);
        this.editEnabled.setValue(true);
        this.msgVisibility.setValue(View.INVISIBLE);
    }

    public void saveChanges(Propietario p) {
        //api.actualizarPerfil(p);
        api.actualizarPropietario(p).enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful()) {
                    pMutableLiveData.setValue(p);
                    msgMutableLiveData.setValue("Datos guardados.");
                } else {
                    Log.d("ProfileViewModel", "Error al guardar los datos: " + call.request().body());
                    msgMutableLiveData.setValue("Error al guardar los datos");
                    Log.d("ProfileViewModel", "Error al guardar los datos: " + response.code() + " - " + response.message());
                }
                buttonSaveVisibility.setValue(View.INVISIBLE);
                buttonEditVisibility.setValue(View.VISIBLE);
                buttonDateVisibility.setValue(View.INVISIBLE);
                editEnabled.setValue(false);
                msgVisibility.setValue(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                msgMutableLiveData.setValue("Error de conexión");
            }
        });
    }
}