package com.albornoz.inmobiliariaandroid.ui.contracts;

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
import com.albornoz.inmobiliariaandroid.databinding.FragmentPagosBinding;
import com.albornoz.inmobiliariaandroid.modelo.Pago;
import com.albornoz.inmobiliariaandroid.ui.tenants.TenantsAdapter;

import java.util.List;

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

        mViewModel.getPagosMutable().observe(getViewLifecycleOwner(), new Observer<List<Pago>>() {
            @Override
            public void onChanged(List<Pago> pagos) {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                        getContext(),
                        LinearLayoutManager.VERTICAL,
                        false
                );
                recyclerViewLista.setLayoutManager(linearLayoutManager);
                adapter = new PagosAdapter(root, pagos);
                recyclerViewLista.setAdapter(adapter);
            }
        });

        mViewModel.setPagosMutable(getArguments());
        return root;
    }

}