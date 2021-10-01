package com.albornoz.inmobiliariaandroid.ui.realestates;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.albornoz.inmobiliariaandroid.R;
import com.albornoz.inmobiliariaandroid.databinding.FragmentProfileBinding;
import com.albornoz.inmobiliariaandroid.databinding.FragmentRealEstatesBinding;
import com.albornoz.inmobiliariaandroid.modelo.Inmueble;

import java.util.List;

public class RealEstatesFragment extends Fragment {

    private RealEstatesViewModel rViewModel;
    private FragmentRealEstatesBinding binding;
    private RealEstatesAdapter adapter;
    private RecyclerView recyclerViewLista;

    public static RealEstatesFragment newInstance() {
        return new RealEstatesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        rViewModel = new ViewModelProvider(this).get(RealEstatesViewModel.class);
        binding = FragmentRealEstatesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerViewLista = binding.RVLista;

        rViewModel.getRealEstatesMutable().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                        getContext(),
                        LinearLayoutManager.VERTICAL,
                        false
                );
                recyclerViewLista.setLayoutManager(linearLayoutManager);
                adapter = new RealEstatesAdapter(root, inmuebles);
                recyclerViewLista.setAdapter(adapter);
            }
        });

        rViewModel.setInmueblesMutable();
        return root;
    }

}