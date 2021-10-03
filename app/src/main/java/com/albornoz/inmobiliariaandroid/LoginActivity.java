package com.albornoz.inmobiliariaandroid;

import static android.Manifest.permission.CALL_PHONE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.albornoz.inmobiliariaandroid.modelo.Propietario;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel viewModel;
    private EditText editTextEmail, editTextPass;
    private Button buttonLogin;
    private TextView errorLogin;
    // Necesarios para ShakeDetection
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeViews();
        getPermissions();
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())
                .create(LoginViewModel.class);

        viewModel.getErrorVisibility().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer visibility) {
                errorLogin.setVisibility(visibility);
            }
        });

        //Button listener
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.login(
                        editTextEmail.getText().toString(),
                        editTextPass.getText().toString()
                );
            }
        });
    }

    private void initializeViews() {
        editTextEmail = findViewById(R.id.editTextEmailAddress);
        editTextPass = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        errorLogin = findViewById(R.id.textViewLoginError);
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

            if (permisos.length>0) {
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

        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {

                // TODO: Hacer algo cuando se detecte el agite del celular
                //TextView tv = MainActivity.this.findViewById(R.id.textView);
                //tv.setText(String.valueOf(count));
                Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "911"));
                LoginActivity.this.startActivity(i);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        editTextPass.setText("");
        editTextEmail.setText("");
        editTextEmail.requestFocus();
        // Se registra el listener del sensor cada vez que la vista login se retoma
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
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