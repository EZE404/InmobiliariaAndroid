package com.albornoz.inmobiliariaandroid;

import static android.Manifest.permission.CALL_PHONE;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.albornoz.inmobiliariaandroid.databinding.ActivityLoginBinding;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel viewModel;
    // Necesarios para ShakeDetection
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeSensor();
        getPermissions();
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())
                .create(LoginViewModel.class);
        viewModel.getErrorVisibility().observe(this, visibility -> binding.textViewLoginError.setVisibility(visibility));
        viewModel.getErrorText().observe(this, text -> binding.textViewLoginError.setText(text));
        viewModel.getBtLoginEnabled().observe(this, enabled -> binding.buttonLogin.setEnabled(enabled));
        //Button listener
        binding.buttonLogin.setOnClickListener(view -> viewModel.login(
                binding.editTextEmailAddress.getText().toString(),
                binding.editTextPassword.getText().toString()
        ));

        // Olvidé mi contraseña
        binding.tvForgotPassword.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RecoveryAccessActivity2.class);
            startActivity(intent);
        });
    }

    private void initializeSensor() {
        // Necesarios para ShakeDetection
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
    }

    private void getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            String[] permisosNecesarios = new String[]{
                    // LISTAR PERMISOS REQUERIDOS
                    CALL_PHONE
            };

            List<String> listaPermisos = new ArrayList<>();

            for (String permiso : permisosNecesarios) {
                if (ActivityCompat.checkSelfPermission(this, permiso) != PackageManager.PERMISSION_GRANTED) {
                    listaPermisos.add(permiso);
                }
            }

            String[] permisos = new String[listaPermisos.size()];
            listaPermisos.toArray(permisos);

            if (permisos.length > 0) {
                Log.d("permisos", "dentro del if de requestPermissions");
                ActivityCompat.requestPermissions(this, permisos, 100);
            } else {
                shakeInitialization();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] != 0) {
                    Log.d("permisos", "onRequestPermissionsResult: Hubo un permiso denegado");
                    finishAffinity();
                }
            }
            // realizar la tarea
            shakeInitialization();
        } else {
            Log.d("permisos", "onRequestPermissionsResult: requestCode distinto de 100");
            finishAffinity();
        }
    }

    private void shakeInitialization() {
        mShakeDetector.setOnShakeListener(count -> {
            Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "2665281216"));
            LoginActivity.this.startActivity(i);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.editTextPassword.setText("");
        binding.editTextEmailAddress.setText("");
        binding.editTextEmailAddress.requestFocus();
        // Se registra el listener del sensor cada vez que la vista login se retoma
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        // Se desregistra el listener del sensor cuando la vista Login sale de pantalla
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}