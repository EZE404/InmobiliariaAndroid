package com.albornoz.inmobiliariaandroid.ui.contracts;

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

import com.albornoz.inmobiliariaandroid.databinding.FragmentContractsBinding;

public class ContractsFragment extends Fragment {

    private ContractsViewModel cViewModel;
    private FragmentContractsBinding binding;
    private ContractsAdapter adapter;
    private RecyclerView recyclerViewLista;

    public static ContractsFragment newInstance() {
        return new ContractsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        cViewModel = new ViewModelProvider(this).get(ContractsViewModel.class);
        binding = FragmentContractsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerViewLista = binding.RVLista;

        cViewModel.getRealEstatesMutable().observe(getViewLifecycleOwner(), inmuebles -> {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                    getContext(),
                    LinearLayoutManager.VERTICAL,
                    false
            );
            recyclerViewLista.setLayoutManager(linearLayoutManager);
            adapter = new ContractsAdapter(root, inmuebles);
            recyclerViewLista.setAdapter(adapter);
        });
        cViewModel.setInmueblesMutable();

        return root;
    }
}