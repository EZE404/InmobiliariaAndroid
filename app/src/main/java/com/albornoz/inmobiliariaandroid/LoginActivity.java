package com.albornoz.inmobiliariaandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.albornoz.inmobiliariaandroid.modelo.Propietario;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel viewModel;
    private EditText editTextEmail, editTextPass;
    private Button buttonLogin;
    private TextView errorLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeViews();
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        editTextPass.setText("");
        editTextEmail.setText("");
        editTextEmail.requestFocus();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}