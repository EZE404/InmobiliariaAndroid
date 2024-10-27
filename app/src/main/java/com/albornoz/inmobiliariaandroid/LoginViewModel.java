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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> btLoginEnabled = new MutableLiveData<>();
    private MutableLiveData<Integer> error_visibility;
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
            Toast.makeText(context, "Por favor, complete todos los campos", Toast.LENGTH_LONG).show();
            return;
        }
        ApiClientRetrofit.InmobiliariaService service = ApiClientRetrofit.getInmobiliariaService(context);

        btLoginEnabled.setValue(false);
        Call<String> call = service.login(email, password);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Guardar el token en SharedPreferences
                    String token = response.body();
                    ApiClientRetrofit.guardarToken(context, token);
                    // Aquí puedes manejar lo que ocurre después de un login exitoso
                    // Por ejemplo, navegar a otra actividad
                    error_visibility.setValue(View.INVISIBLE);
                    Intent i = new Intent(context, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                } else {
                    // Manejar un login fallido (credenciales incorrectas)
                    Log.d("LoginViewModel", "Login fallido: " + response.message());
                    Toast.makeText(context, "Error en la petición", Toast.LENGTH_LONG).show();
                    btLoginEnabled.setValue(true);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Manejar el error en la solicitud
                Log.d("LoginViewModel", "Error en el servidor: " + t.getMessage());
                Toast.makeText(context, "Error en el servidor", Toast.LENGTH_LONG).show();
                btLoginEnabled.setValue(true);
            }
        });
    }

}
