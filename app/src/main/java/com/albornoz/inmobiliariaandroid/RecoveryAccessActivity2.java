package com.albornoz.inmobiliariaandroid;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.albornoz.inmobiliariaandroid.databinding.ActivityRecoveryAccess2Binding;

public class RecoveryAccessActivity2 extends AppCompatActivity {

    private ActivityRecoveryAccess2Binding binding;
    private RecoveryAccessViewModel2 viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecoveryAccess2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(RecoveryAccessViewModel2.class);

        viewModel.getBtSendTokenEnabled().observe(this, enabled -> binding.sendTokenButton.setEnabled(enabled));
        viewModel.getBtChangePasswordEnabled().observe(this, enabled -> binding.createNewPasswordButton.setEnabled(enabled));
        viewModel.getVolverHaciaLogin().observe(this, volver -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        binding.sendTokenButton.setOnClickListener(
                v -> viewModel.sendToken(binding.emailInput.getText().toString().trim()));

        binding.createNewPasswordButton.setOnClickListener(
                v -> viewModel.changePassword(
                        binding.newPasswordInput.getText().toString().trim(),
                        binding.tokenInput.getText().toString().trim()));
    }
}