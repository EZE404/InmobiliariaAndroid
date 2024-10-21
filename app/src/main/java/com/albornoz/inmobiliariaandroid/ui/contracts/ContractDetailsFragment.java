package com.albornoz.inmobiliariaandroid.ui.contracts;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.albornoz.inmobiliariaandroid.R;
import com.albornoz.inmobiliariaandroid.databinding.FragmentContractDetailsBinding;
import com.albornoz.inmobiliariaandroid.request.ApiClientRetrofit;
import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

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

        cViewModel.getContratoMutable().observe(getViewLifecycleOwner(), c -> {
            binding.tvAddress.setText(c.getInmueble().getDireccion());
            Glide.with(this)
                    .load(ApiClientRetrofit.getHost() + c.getInmueble().getImageUrl())
                    .circleCrop()
                    .into(binding.ivInmuebleImage);
            // Crear un NumberFormat para el locale español
            NumberFormat formatoEspanol = NumberFormat.getNumberInstance(new Locale("es", "AR"));
            // Asegurar que el formato utilice 2 decimales
            formatoEspanol.setMinimumFractionDigits(2);
            formatoEspanol.setMaximumFractionDigits(2);
            // Convertir el número a texto
            String numeroTexto = formatoEspanol.format(c.getMonto());
            binding.tvMonto.setText(String.format("$%s", numeroTexto));
            binding.tvId.setText(String.valueOf(c.getId()));
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            binding.tvDesde.setText(sdf.format(c.getDesde()));
            binding.tvHasta.setText(sdf.format(c.getHasta()));
            binding.tvInquilino.setText(c.getInquilino().getNombre());
            binding.tvNombreGarante.setText(c.getNombreGarante());
            binding.tvDniGarante.setText(c.getDniGarante());
            binding.tvTelGarante.setText(c.getTelefonoGarante());
            binding.tvEmailGarante.setText(c.getEmailGarante());
        });

        // binding.btPagos.setOnClickListener(view -> cViewModel.openPagos(root));
        binding.btPagos.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("contrato", cViewModel.getContratoMutable().getValue());
            Navigation.findNavController(root).navigate(R.id.pagosFragment, bundle);
        });
        cViewModel.setContrato(getArguments());
        return root;
    }

}