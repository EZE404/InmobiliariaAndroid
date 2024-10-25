package com.albornoz.inmobiliariaandroid.ui.tenants;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.albornoz.inmobiliariaandroid.R;
import com.albornoz.inmobiliariaandroid.databinding.ItemRealEstateBinding;
import com.albornoz.inmobiliariaandroid.modelo.Inmueble;
import com.albornoz.inmobiliariaandroid.request.ApiClientRetrofit;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class TenantsAdapter extends RecyclerView.Adapter<TenantsAdapter.MiViewHolder> {

    private List<Inmueble> inmuebles;
    private View root;

    public TenantsAdapter(View root, List<Inmueble> inmuebles) {
        this.root = root;
        this.inmuebles = inmuebles;
    }

    @NonNull
    @Override
    public MiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemRealEstateBinding binding = ItemRealEstateBinding.inflate(layoutInflater, parent, false);
        return new MiViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MiViewHolder holder, int position) {
        Inmueble i = inmuebles.get(position);
        holder.binding.tvAddress.setText(i.getDireccion());
        holder.binding.tvDetails.setText(String.format("%s - %s", i.getTipo(), i.getUso()));

        // Cargar la imagen
        Glide.with(root.getContext())
                .load(ApiClientRetrofit.getHost() + i.getImageUrl())
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.binding.ivPhoto);

        // Configurar el click del CardView
        holder.binding.cvRealEstate.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("realEstate", i);
            Navigation.findNavController(root).navigate(R.id.action_nav_tenants_to_tenantDetailsFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return inmuebles.size();
    }

    public class MiViewHolder extends RecyclerView.ViewHolder {
        private final ItemRealEstateBinding binding;

        public MiViewHolder(@NonNull ItemRealEstateBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
