package com.albornoz.inmobiliariaandroid.ui.tenants;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.albornoz.inmobiliariaandroid.databinding.FragmentTenantDetailsBinding;

public class TenantDetailsFragment extends Fragment {

    private TenantDetailsViewModel tViewModel;
    private FragmentTenantDetailsBinding binding;

    public static TenantDetailsFragment newInstance() {
        return new TenantDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        tViewModel = new ViewModelProvider(this).get(TenantDetailsViewModel.class);
        binding = FragmentTenantDetailsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        tViewModel.getInquilinoMutable().observe(getViewLifecycleOwner(), i -> {
            binding.tvNombreApellido.setText(i.getNombre());
            binding.tvDni.setText(i.getDni());
            binding.tvTel.setText(i.getTelefono());
            binding.tvEmailAddress.setText(i.getEmail());
            binding.tvTrabajo.setText(i.getDireccionTrabajo());
        });

        tViewModel.setInquilino(getArguments());
        return root;
    }
}