package com.albornoz.inmobiliariaandroid;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.albornoz.inmobiliariaandroid.request.ApiClientRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecoveryAccessViewModel2 extends AndroidViewModel {
    private ApiClientRetrofit.InmobiliariaService service;
    private MutableLiveData<Boolean> volverHaciaLogin = new MutableLiveData<>();
    private MutableLiveData<Boolean> btSendTokenEnabled = new MutableLiveData<>();
    private MutableLiveData<Boolean> btChangePasswordEnabled = new MutableLiveData<>();

    public RecoveryAccessViewModel2(@NonNull Application application) {
        super(application);
        service = ApiClientRetrofit.getInmobiliariaService(getApplication());
        // Eliminar token guardado para que no lo reemplace el middleware
        ApiClientRetrofit.eliminarToken(getApplication());
    }

    public MutableLiveData<Boolean> getBtSendTokenEnabled() {
        return btSendTokenEnabled;
    }

    public MutableLiveData<Boolean> getBtChangePasswordEnabled() {
        return btChangePasswordEnabled;
    }

    public MutableLiveData<Boolean> getVolverHaciaLogin() {
        return volverHaciaLogin;
    }

    // CAMBIAR LA CONTRASEÑA DEL PROPIETARIO
    public void changePassword(String pass, String token) {
        if (pass.isEmpty()) {
            Toast.makeText(getApplication(), "Ingresa una contraseña", Toast.LENGTH_LONG).show();
            return;
        }
        if (token.isEmpty()) {
            Toast.makeText(getApplication(), "Ingresa tu token", Toast.LENGTH_LONG).show();
            return;
        }
        btChangePasswordEnabled.setValue(false);
        service.cambiarClaveConToken("Bearer " + token, pass).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplication(), "Contraseña cambiada", Toast.LENGTH_LONG).show();
                    btChangePasswordEnabled.setValue(true);
                    // hacer algo para que la activity vuelva al login
                    volverHaciaLogin.setValue(true);
                } else {
                    Toast.makeText(getApplication(), "Error al cambiar la contraseña", Toast.LENGTH_LONG).show();
                    btChangePasswordEnabled.setValue(true);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                Toast.makeText(getApplication(), "Error en el servidor", Toast.LENGTH_LONG).show();
                btChangePasswordEnabled.setValue(true);
            }
        });
    }

    // ENVIAR EL TOKEN TEMPORAL AL CORREO DEL PROPIETARIO
    public void sendToken(String email) {
        if (email.isEmpty()) {
            Toast.makeText(getApplication(), "Ingresa tu email", Toast.LENGTH_LONG).show();
            return;
        }
        btSendTokenEnabled.setValue(false);
        service.enviarTokenRecuperacion(email).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplication(), "Token enviado", Toast.LENGTH_LONG).show();
                    btSendTokenEnabled.setValue(true);
                } else {
                    Toast.makeText(getApplication(), "Error al enviar el token", Toast.LENGTH_LONG).show();
                    btSendTokenEnabled.setValue(true);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                Toast.makeText(getApplication(), "Error en el servidor", Toast.LENGTH_LONG).show();
                btSendTokenEnabled.setValue(true);
            }
        });
    }
}