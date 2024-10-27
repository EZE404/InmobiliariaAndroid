package com.albornoz.inmobiliariaandroid;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.albornoz.inmobiliariaandroid.databinding.ActivityRecoveryAccessBinding;

// ESTA ACTIVIY ES PARA RECUPERAR LA CONTRASEÑA ABRIENDO LA ACTIVITY DESDE UN ENLACE
// ES A MODO PRUEBA Y NO DEBE SER CONSIDERADA PARTE DE LA ENTREGA DEL PROYECTO
// LA FUNCIONALIDAD DE CONTRASEÑA OLVIDADA ESTÁ EN RecoveryAccessActivity2
public class RecoveryAccessActivity extends AppCompatActivity {

    private ActivityRecoveryAccessBinding binding;
    private RecoveryAccessViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecoveryAccessBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(RecoveryAccessViewModel.class);

        Intent intent = getIntent();
        handleIntent(intent);

    }

    private void handleIntent(Intent intent) {
        Uri data = intent.getData();
        if (data != null) {
            String token = data.getQueryParameter("token");
            viewModel.changePassword(token);
        }
    }
}