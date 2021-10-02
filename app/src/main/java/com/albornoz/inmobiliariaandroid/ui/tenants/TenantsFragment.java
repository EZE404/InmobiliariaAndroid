package com.albornoz.inmobiliariaandroid.ui.tenants;

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
import com.albornoz.inmobiliariaandroid.databinding.FragmentRealEstatesBinding;
import com.albornoz.inmobiliariaandroid.databinding.FragmentTenantsBinding;
import com.albornoz.inmobiliariaandroid.modelo.Inmueble;
import com.albornoz.inmobiliariaandroid.ui.realestates.RealEstatesAdapter;

import java.util.List;

public class TenantsFragment extends Fragment {

    private TenantsViewModel tViewModel;
    private FragmentTenantsBinding binding;
    private TenantsAdapter adapter;
    private RecyclerView recyclerViewLista;

    public static TenantsFragment newInstance() {
        return new TenantsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        tViewModel = new ViewModelProvider(this).get(TenantsViewModel.class);
        binding = FragmentTenantsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerViewLista = binding.RVLista;

        tViewModel.getRealEstatesMutable().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                        getContext(),
                        LinearLayoutManager.VERTICAL,
                        false
                );
                recyclerViewLista.setLayoutManager(linearLayoutManager);
                adapter = new TenantsAdapter(root, inmuebles);
                recyclerViewLista.setAdapter(adapter);
            }
        });
        tViewModel.setInmueblesMutable();
        return root;
    }
}