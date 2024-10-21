package com.albornoz.inmobiliariaandroid.ui.realestates;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.albornoz.inmobiliariaandroid.R;
import com.albornoz.inmobiliariaandroid.databinding.FragmentAddRealEstateBinding;

public class AddRealEstateFragment extends Fragment {

    private AddRealEstateViewModel mViewModel;
    private FragmentAddRealEstateBinding binding;

    public static AddRealEstateFragment newInstance() {
        return new AddRealEstateFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Usar View Binding para inflar el layout
        binding = FragmentAddRealEstateBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        mViewModel = new ViewModelProvider(this).get(AddRealEstateViewModel.class);
        // TODO: Use the ViewModel
        // Configurar los spinners usando View Binding
        setupSpinners();

        return view;
    }

    private void setupSpinners() {
        // Configurar Spinner para "Tipo"
        ArrayAdapter<CharSequence> adapterTipo = ArrayAdapter.createFromResource(getContext(),
                R.array.tipos_inmueble, android.R.layout.simple_spinner_item);
        adapterTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerTipo.setAdapter(adapterTipo);

        // Configurar Spinner para "Uso"
        ArrayAdapter<CharSequence> adapterUso = ArrayAdapter.createFromResource(getContext(),
                R.array.usos_inmueble, android.R.layout.simple_spinner_item);
        adapterUso.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerUso.setAdapter(adapterUso);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Evitar memory leaks
    }
}
