package com.albornoz.inmobiliariaandroid.ui.tenants;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.albornoz.inmobiliariaandroid.R;
import com.albornoz.inmobiliariaandroid.databinding.FragmentTenantDetailsBinding;
import com.albornoz.inmobiliariaandroid.modelo.Inmueble;
import com.albornoz.inmobiliariaandroid.modelo.Inquilino;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

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

        tViewModel.getInquilinoMutable().observe(getViewLifecycleOwner(), new Observer<Inquilino>() {
            @Override
            public void onChanged(Inquilino i) {
                // TODO: cargar datos en las vistas
                binding.tvNombreApellido.setText(i.getNombre()+" "+i.getApellido());
                binding.tvDni.setText(String.valueOf(i.getDNI()));
                binding.tvTel.setText(i.getTelefono());
                binding.tvEmailAddress.setText(i.getEmail());
                binding.tvTrabajo.setText(i.getLugarDeTrabajo());
                binding.tvNombreGarante.setText(i.getNombreGarante());
                binding.tvTelGarante.setText(i.getTelefonoGarante());
            }
        });

        tViewModel.setInquilino(getArguments());
        return root;
    }
}