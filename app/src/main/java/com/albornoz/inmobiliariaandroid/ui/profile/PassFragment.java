package com.albornoz.inmobiliariaandroid.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.albornoz.inmobiliariaandroid.databinding.FragmentPassBinding;

public class PassFragment extends Fragment {

    private FragmentPassBinding binding;
    private PassViewModel passViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPassBinding.inflate(inflater, container, false);
        passViewModel = new ViewModelProvider(this).get(PassViewModel.class);

        // Configurar el botón para cambiar la contraseña
        binding.btnChangePassword.setOnClickListener(v -> {
            String currentPass = binding.etCurrentPassword.getText().toString();
            String newPass = binding.etNewPassword.getText().toString();
            passViewModel.changePassword(currentPass, newPass);
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
