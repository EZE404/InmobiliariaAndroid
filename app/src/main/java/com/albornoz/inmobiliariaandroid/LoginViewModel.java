package com.albornoz.inmobiliariaandroid;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.albornoz.inmobiliariaandroid.request.ApiClientRetrofit;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> btLoginEnabled = new MutableLiveData<>();
    private MutableLiveData<Integer> error_visibility;
    private MutableLiveData<String> error_text = new MutableLiveData<>();
    private Context context;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
        btLoginEnabled.setValue(true);
    }

    public MutableLiveData<Boolean> getBtLoginEnabled() {
        return btLoginEnabled;
    }

    public LiveData<Integer> getErrorVisibility() {
        if (error_visibility == null) {
            error_visibility = new MutableLiveData<>();
        }
        return error_visibility;
    }

    public void login(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            //Toast.makeText(context, "Por favor, complete todos los campos", Toast.LENGTH_LONG).show();
            error_text.setValue("Por favor, complete todos los campos");
            error_visibility.setValue(View.VISIBLE);
            return;
        }

        ApiClientRetrofit.InmobiliariaService service = ApiClientRetrofit.getInmobiliariaService(context);
        btLoginEnabled.setValue(false);
        Call<String> call = service.login(email, password);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Login exitoso
                    String token = response.body();
                    ApiClientRetrofit.guardarToken(context, token);
                    error_text.setValue("");
                    error_visibility.setValue(View.INVISIBLE);
                    Intent i = new Intent(context, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                } else {
                    // Discriminar el código de respuesta y mostrar el mensaje del servidor
                    String mensajeError = response.message();
                    switch (response.code()) {
                        case 400:
                            //Toast.makeText(context, "Solicitud incorrecta (400): " + mensajeError, Toast.LENGTH_LONG).show();
                            error_text.setValue("Datos de usuario incorrectos");
                            error_visibility.setValue(View.VISIBLE);
                            break;
                        case 401:
                            Toast.makeText(context, "No autorizado (401): " + mensajeError, Toast.LENGTH_LONG).show();
                            break;
                        case 403:
                            Toast.makeText(context, "Prohibido (403): " + mensajeError, Toast.LENGTH_LONG).show();
                            break;
                        case 404:
                            Toast.makeText(context, "Recurso no encontrado (404): " + mensajeError, Toast.LENGTH_LONG).show();
                            break;
                        case 500:
                            Toast.makeText(context, "Error interno del servidor (500): " + mensajeError, Toast.LENGTH_LONG).show();
                            break;
                        default:
                            Toast.makeText(context, "Error desconocido (" + response.code() + "): " + mensajeError, Toast.LENGTH_LONG).show();
                            break;
                    }
                    btLoginEnabled.setValue(true);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Manejar el error en la solicitud
                Log.e("LoginViewModel", "Error en la solicitud: " + t.getMessage());

                // Mostrar mensaje específico según el tipo de error
                if (t instanceof IOException) {
                    // Error de red o fallo de conexión
                    Toast.makeText(context, "Error de conexión: Verifique su conexión a Internet", Toast.LENGTH_LONG).show();
                } else {
                    // Otro tipo de error inesperado
                    Toast.makeText(context, "Error inesperado: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }

                btLoginEnabled.setValue(true);
            }
        });
    }


    public LiveData<String> getErrorText() {
        return error_text;
    }
}
