package com.albornoz.inmobiliariaandroid.ui.realestates;

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

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class RealEstatesAdapter extends RecyclerView.Adapter<RealEstatesAdapter.MiViewHolder> {

    private List<Inmueble> inmuebles;
    private View root;

    public RealEstatesAdapter(View root, List<Inmueble> inmuebles) {
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

        // Formatear el precio
        NumberFormat formatoEspanol = NumberFormat.getNumberInstance(new Locale("es", "AR"));
        formatoEspanol.setMinimumFractionDigits(2);
        formatoEspanol.setMaximumFractionDigits(2);
        String numeroTexto = formatoEspanol.format(i.getPrecio());
        holder.binding.tvDetails.setText(String.format("$%s", numeroTexto));

        // Cargar la imagen
        Glide.with(root.getContext())
                .load(ApiClientRetrofit.getHost() + i.getImageUrl())
                .circleCrop()
                .into(holder.binding.ivPhoto);

        // Configurar el click del CardView
        holder.binding.cvRealEstate.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("realEstate", i);
            Navigation.findNavController(root).navigate(R.id.action_nav_real_estates_to_realEstateDetailsFragment, bundle);
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
