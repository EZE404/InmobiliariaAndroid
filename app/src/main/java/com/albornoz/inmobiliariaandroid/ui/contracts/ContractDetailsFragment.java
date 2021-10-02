package com.albornoz.inmobiliariaandroid.ui.contracts;

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
import com.albornoz.inmobiliariaandroid.databinding.FragmentContractDetailsBinding;
import com.albornoz.inmobiliariaandroid.databinding.FragmentTenantDetailsBinding;
import com.albornoz.inmobiliariaandroid.modelo.Contrato;

public class ContractDetailsFragment extends Fragment {

    private ContractDetailsViewModel cViewModel;
    private FragmentContractDetailsBinding binding;


    public static ContractDetailsFragment newInstance() {
        return new ContractDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        cViewModel = new ViewModelProvider(this).get(ContractDetailsViewModel.class);
        binding = FragmentContractDetailsBinding.inflate(inflater, container,false);
        View root = binding.getRoot();

        cViewModel.getContratoMutable().observe(getViewLifecycleOwner(), new Observer<Contrato>() {
            @Override
            public void onChanged(Contrato c) {
                // TODO: cargar datos de contrato en la vista
                binding.tvId.setText(String.valueOf(c.getIdContrato()));
                binding.tvDesde.setText(c.getFechaInicio());
                binding.tvHasta.setText(c.getFechaFin());
                binding.tvMonto.setText("$"+c.getMontoAlquiler());
                binding.tvInquilino.setText(c.getInquilino().getNombre()+" "+c.getInquilino().getApellido());
                binding.tvInmueble.setText(c.getInmueble().getDireccion());
            }
        });

        cViewModel.setContrato(getArguments());
        return root;
    }

}