package com.albornoz.inmobiliariaandroid.ui.logout;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.albornoz.inmobiliariaandroid.LoginActivity;
import com.albornoz.inmobiliariaandroid.R;
import com.albornoz.inmobiliariaandroid.request.ApiClientRetrofit;

public class LogoutFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_logout, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        new AlertDialog.Builder(requireContext())
                .setTitle("Logout")
                .setMessage("¿Desea cerrar sesión?")
                .setPositiveButton("Sí", (dialogInterface, i) -> {
                    ApiClientRetrofit.eliminarToken(requireContext());
                    startActivity(new Intent(requireContext(), LoginActivity.class));
                    requireActivity().finish(); // Cierra la actividad actual para evitar regresar a ella
                })
                .setNegativeButton("No", (dialogInterface, i) ->
                        Navigation.findNavController(requireView()).popBackStack())
                .show();
    }
}
