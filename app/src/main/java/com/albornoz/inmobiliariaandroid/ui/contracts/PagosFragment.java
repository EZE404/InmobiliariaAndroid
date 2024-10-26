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

import com.albornoz.inmobiliariaandroid.databinding.FragmentPagosBinding;

public class PagosFragment extends Fragment {

    private PagosViewModel mViewModel;
    private FragmentPagosBinding binding;
    private PagosAdapter adapter;
    private RecyclerView recyclerViewLista;

    public static PagosFragment newInstance() {
        return new PagosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(PagosViewModel.class);
        binding = FragmentPagosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerViewLista = binding.RVLista;

        mViewModel.getPagosMutable().observe(getViewLifecycleOwner(), pagos -> {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                    getContext(),
                    LinearLayoutManager.VERTICAL,
                    false
            );
            recyclerViewLista.setLayoutManager(linearLayoutManager);
            adapter = new PagosAdapter(pagos);
            recyclerViewLista.setAdapter(adapter);
        });

        mViewModel.setPagosMutable(getArguments());
        return root;
    }

}