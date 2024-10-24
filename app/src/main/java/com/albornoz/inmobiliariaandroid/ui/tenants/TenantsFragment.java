package com.albornoz.inmobiliariaandroid.ui.tenants;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.albornoz.inmobiliariaandroid.databinding.FragmentTenantsBinding;

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

        tViewModel.getRealEstatesMutable().observe(getViewLifecycleOwner(), inmuebles -> {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                    getContext(),
                    LinearLayoutManager.VERTICAL,
                    false
            );
            recyclerViewLista.setLayoutManager(linearLayoutManager);
            adapter = new TenantsAdapter(root, inmuebles);
            recyclerViewLista.setAdapter(adapter);
        });
        tViewModel.setInmueblesMutable();
        return root;
    }
}