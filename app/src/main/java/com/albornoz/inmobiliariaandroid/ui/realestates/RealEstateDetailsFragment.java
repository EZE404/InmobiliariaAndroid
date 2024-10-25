package com.albornoz.inmobiliariaandroid.ui.realestates;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.albornoz.inmobiliariaandroid.R;
import com.albornoz.inmobiliariaandroid.databinding.FragmentRealEstateDetailsBinding;
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

        // Crear un listener para el checkbox
        CompoundButton.OnCheckedChangeListener checkBoxListener = (compoundButton, isChecked) -> rViewModel.setDisponible(isChecked);

        // Observador para cargar los datos del inmueble
        rViewModel.getInmuebleMutable().observe(getViewLifecycleOwner(), i -> {
            // Crear un NumberFormat para el locale español
            NumberFormat formatoEspanol = NumberFormat.getNumberInstance(new Locale("es", "AR"));
            formatoEspanol.setMinimumFractionDigits(2);
            formatoEspanol.setMaximumFractionDigits(2);
            String numeroTexto = formatoEspanol.format(i.getPrecio());

            // Asignar los valores a las views
            binding.tvPrecio.setText(String.format("$%s", numeroTexto));
            binding.tvAddress.setText(i.getDireccion());
            binding.tvTipo.setText(i.getTipo());
            binding.tvUso.setText(i.getUso());
            binding.tvAmbientes.setText(String.valueOf(i.getAmbientes()));

            // Eliminar temporalmente el listener antes de actualizar el checkbox programáticamente
            binding.cbDisponible.setOnCheckedChangeListener(null);
            binding.cbDisponible.setChecked(i.isDisponible());
            // Restaurar el listener después de actualizar el valor
            binding.cbDisponible.setOnCheckedChangeListener(checkBoxListener);

            // Cargar la imagen con Glide
            Glide.with(root.getContext())
                    .load(ApiClientRetrofit.getHost() + i.getImageUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.ivPhoto);
        });

        // Observador para habilitar o deshabilitar el checkbox
        rViewModel.getDisponibleCheckEnabledMutable().observe(getViewLifecycleOwner(), enabled ->
                binding.cbDisponible.setEnabled(enabled)
        );

        // Inicializar el listener en el checkbox
        binding.cbDisponible.setOnCheckedChangeListener(checkBoxListener);

        // Cargar el inmueble según los argumentos
        rViewModel.setInmueble(getArguments());

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Añadir un callback personalizado para manejar el botón "atrás"
        requireActivity().getOnBackPressedDispatcher().addCallback(
                getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        // Navegar manualmente hacia RealEstatesFragment
                        NavController navController = NavHostFragment.findNavController(RealEstateDetailsFragment.this);
                        navController.navigate(R.id.action_realEstateDetailsFragment_to_nav_real_estates);  // Ir a RealEstatesFragment
                    }
                });
    }
}
