package com.albornoz.inmobiliariaandroid.ui.contracts;

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

public class ContractsAdapter extends RecyclerView.Adapter<ContractsAdapter.MiViewHolder> {

    private List<Inmueble> inmuebles;
    private View root;

    public ContractsAdapter(View root, List<Inmueble> inmuebles) {
        this.root = root;
        this.inmuebles = inmuebles;
    }

    @NonNull
    @Override
    public ContractsAdapter.MiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemRealEstateBinding binding = ItemRealEstateBinding.inflate(layoutInflater, parent, false);
        return new MiViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ContractsAdapter.MiViewHolder holder, int position) {
        Inmueble i = inmuebles.get(position);
        holder.binding.tvAddress.setText(i.getDireccion());
        holder.binding.tvDetails.setText(String.format("%s - %s", i.getTipo(), i.getUso()));
        Glide.with(root.getContext())
                .load(ApiClientRetrofit.getHost() + i.getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .circleCrop()
                .into(holder.binding.ivPhoto);
        holder.binding.cvRealEstate.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("realEstate", i);
            Navigation.findNavController(root).navigate(R.id.contractDetailsFragment, bundle);
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
