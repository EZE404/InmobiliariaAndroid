package com.albornoz.inmobiliariaandroid.ui.profile;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.albornoz.inmobiliariaandroid.request.ApiClientRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PassViewModel extends AndroidViewModel {
    public PassViewModel(@NonNull Application application) {
        super(application);
    }

    public void changePassword(String actualPass, String nuevaPass) {
        if (actualPass.isEmpty() || nuevaPass.isEmpty()) {
            Toast.makeText(getApplication(), "Complete los campos", Toast.LENGTH_SHORT).show();
            return;
        }
        ApiClientRetrofit.InmobiliariaService service = ApiClientRetrofit.getInmobiliariaService(getApplication());
        service.actualizarClave(actualPass, nuevaPass).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplication(), "Contraseña cambiada", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplication(), "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                Toast.makeText(getApplication(), "Error en el servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }
}