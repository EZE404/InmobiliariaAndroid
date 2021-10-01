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

import com.albornoz.inmobiliariaandroid.R;
import com.albornoz.inmobiliariaandroid.databinding.FragmentProfileBinding;
import com.albornoz.inmobiliariaandroid.databinding.FragmentRealEstateDetailsBinding;
import com.albornoz.inmobiliariaandroid.modelo.Inmueble;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

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

        binding.cbDisponible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                rViewModel.setDisponible(b);
            }
        });

        rViewModel.getInmuebleMutable().observe(getViewLifecycleOwner(), new Observer<Inmueble>() {
            @Override
            public void onChanged(Inmueble i) {
                // TODO: cargar datos en las vistas
                binding.tvAddress.setText(i.getDireccion());
                binding.tvPrecio.setText(String.valueOf(i.getPrecio()));
                binding.tvTipo.setText(i.getTipo());
                binding.tvUso.setText(i.getUso());
                binding.tvAmbientes.setText(String.valueOf(i.getAmbientes()));
                binding.tvPropietario.setText(i.getPropietario().getNombre()+" "+i.getPropietario().getApellido());

                binding.cbDisponible.setChecked(i.isEstado());

                Glide.with(root.getContext())
                        .load(i.getImagen())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.ivPhoto);
            }
        });

        rViewModel.setInmueble(getArguments());
        return root;
    }


}