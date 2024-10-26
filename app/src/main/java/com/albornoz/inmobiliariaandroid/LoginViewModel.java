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

    private MutableLiveData<Integer> error_visibility;
    private Context context;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public LiveData<Integer> getErrorVisibility() {
        if (error_visibility == null) {
            error_visibility = new MutableLiveData<>();
        }
        return error_visibility;
    }

    public void login(String email, String password) {
        ApiClientRetrofit.InmobiliariaService service = ApiClientRetrofit.getInmobiliariaService(context);

        // Llamada al metodo login en la interfaz de la API
        //RequestBody emailBody = RequestBody.create(MediaType.parse("multipart/form-data"), email);
        //RequestBody passBody = RequestBody.create(MediaType.parse("multipart/form-data"), password);
        //Call<String> call = service.login2(emailBody, passBody);
        Call<String> call = service.login(email, password);
        call.enqueue(new Callback<String>() {
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
                    Toast.makeText(context, "No se pudo iniciar sesión", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Manejar el error en la solicitud
                Log.d("LoginViewModel", "Error en la solicitud: " + t.getMessage());
                Toast.makeText(context, "Error en la solicitud", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
