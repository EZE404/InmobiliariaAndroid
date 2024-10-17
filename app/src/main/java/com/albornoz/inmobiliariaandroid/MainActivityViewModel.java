package com.albornoz.inmobiliariaandroid;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.albornoz.inmobiliariaandroid.modelo.Propietario;
import com.albornoz.inmobiliariaandroid.request.ApiClientRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends AndroidViewModel {

    private final MutableLiveData<String> nombre = new MutableLiveData<>();
    private final MutableLiveData<String> email = new MutableLiveData<>();
    private final MutableLiveData<String> avatarUrl = new MutableLiveData<>();

    public MainActivityViewModel(Application application) {
        super(application);
        //fetchPropietario(); // Llama a la API al iniciar el ViewModel
    }

    public LiveData<String> getNombre() {
        return nombre;
    }

    public LiveData<String> getEmail() {
        return email;
    }

    public LiveData<String> getAvatarUrl() {
        return avatarUrl;
    }

    public void fetchPropietario() {
        ApiClientRetrofit.InmobiliariaService service = ApiClientRetrofit.getInmobiliariaService(getApplication());
        service.getPropietario().enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Propietario propietario = response.body();
                    nombre.setValue(propietario.getNombre() + " " + propietario.getApellido());
                    email.setValue(propietario.getEmail());
                    avatarUrl.setValue(ApiClientRetrofit.getHost() + propietario.getAvatarUrl());
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                // Manejo de errores
                Toast.makeText(getApplication(), "Error al obtener el propietario", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
