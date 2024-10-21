package com.albornoz.inmobiliariaandroid.ui.realestates;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.albornoz.inmobiliariaandroid.databinding.FragmentRealEstateDetailsBinding;
import com.albornoz.inmobiliariaandroid.modelo.Inmueble;
import com.albornoz.inmobiliariaandroid.request.ApiClientRetrofit;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.NumberFormat;
import java.util.Locale;

public class RealEstateDetailsFragment extends Fragment {

    private RealEstateDetailsViewModel rViewModel;
    private FragmentRealEstateDetailsBinding binding;

    public static RealEstateDetailsFragment newInstance() {
        return new RealEstateDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rViewModel = new ViewModelProvider(this).get(RealEstateDetailsViewModel.class);
        binding = FragmentRealEstateDetailsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        rViewModel.getInmuebleMutable().observe(getViewLifecycleOwner(), i -> {
            // Crear un NumberFormat para el locale español
            NumberFormat formatoEspanol = NumberFormat.getNumberInstance(new Locale("es", "AR"));
            // Asegurar que el formato utilice 2 decimales
            formatoEspanol.setMinimumFractionDigits(2);
            formatoEspanol.setMaximumFractionDigits(2);
            // Convertir el número a texto
            String numeroTexto = formatoEspanol.format(i.getPrecio());
            binding.tvPrecio.setText(String.format("$%s", numeroTexto));
            binding.tvAddress.setText(i.getDireccion());
            binding.tvTipo.setText(i.getTipo());
            binding.tvUso.setText(i.getUso());
            binding.tvAmbientes.setText(String.valueOf(i.getAmbientes()));
            binding.cbDisponible.setChecked(i.isDisponible());
            Glide.with(root.getContext())
                    .load(ApiClientRetrofit.getHost() + i.getImageUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.ivPhoto);
        });

        rViewModel.getDisponibleCheckEnabledMutable().observe(getViewLifecycleOwner(), enabled ->
            binding.cbDisponible.setEnabled(enabled)
        );

        // TODO: cuando el checkbox cambie, hacer que el viewmodel cambie en el server esta propiedad
        binding.cbDisponible.setOnCheckedChangeListener((compoundButton, b) -> rViewModel.setDisponible(b));

        rViewModel.setInmueble(getArguments());
        return root;
    }
}