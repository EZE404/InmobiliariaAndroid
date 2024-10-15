package com.albornoz.inmobiliariaandroid.ui.profile;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.albornoz.inmobiliariaandroid.databinding.FragmentProfileBinding;
import com.albornoz.inmobiliariaandroid.modelo.Propietario;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;
    private FragmentProfileBinding binding;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        initialize();

        View root = binding.getRoot();
        return root;
    }

    private void initialize() {
        mViewModel.getCurrentUser().observe(getViewLifecycleOwner(), p -> {
            binding.editTextEmailAddress.setText(p.getEmail());
            binding.editTextName.setText(p.getNombre());
            binding.editTextLastName.setText(p.getApellido());
            binding.editTextDni.setText(p.getDni().toString());
            binding.editTextTel.setText(p.getTelefono());
            binding.editTextPassword.setText(p.getContrasena());
        });

        mViewModel.getButtonEditVisibility().observe(getViewLifecycleOwner(), visibility -> binding.buttonEdit.setVisibility(visibility));

        mViewModel.getButtonSaveVisibility().observe(getViewLifecycleOwner(), visibility -> binding.buttonSave.setVisibility(visibility));

        mViewModel.getEditEnabled().observe(getViewLifecycleOwner(), flag -> {
            binding.editTextEmailAddress.setEnabled(flag);
            binding.editTextName.setEnabled(flag);
            binding.editTextLastName.setEnabled(flag);
            binding.editTextDni.setEnabled(flag);
            binding.editTextTel.setEnabled(flag);
            binding.editTextPassword.setEnabled(flag);
        });
        mViewModel.getMsgVisibility().observe(getViewLifecycleOwner(), visibility -> binding.textViewMsg.setVisibility(visibility));

        mViewModel.getMsgMutableLiveData().observe(getViewLifecycleOwner(), s -> binding.textViewMsg.setText(s));

        binding.buttonEdit.setOnClickListener(view -> mViewModel.enableEdit());

        binding.buttonSave.setOnClickListener(view -> {
            Propietario p = new Propietario();
            p.setEmail(binding.editTextEmailAddress.getText().toString());
            p.setNombre(binding.editTextName.getText().toString());
            p.setApellido(binding.editTextLastName.getText().toString());
            p.setDni((long) Integer.parseInt(binding.editTextDni.getText().toString()));
            p.setTelefono(binding.editTextTel.getText().toString());
            p.setContrasena(binding.editTextPassword.getText().toString());
            mViewModel.saveChanges(p);
        });

        mViewModel.setCurrentUser();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}