package com.albornoz.inmobiliariaandroid.ui.tenants;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.albornoz.inmobiliariaandroid.R;

public class TenantsFragment extends Fragment {

    private TenantsViewModel mViewModel;

    public static TenantsFragment newInstance() {
        return new TenantsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tenants, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TenantsViewModel.class);
        // TODO: Use the ViewModel
    }

}