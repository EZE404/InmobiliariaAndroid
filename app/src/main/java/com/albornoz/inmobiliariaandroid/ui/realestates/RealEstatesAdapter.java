package com.albornoz.inmobiliariaandroid.ui.realestates;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.albornoz.inmobiliariaandroid.R;
import com.albornoz.inmobiliariaandroid.modelo.Inmueble;
import com.albornoz.inmobiliariaandroid.request.ApiClientRetrofit;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class RealEstatesAdapter extends RecyclerView.Adapter<RealEstatesAdapter.MiViewHolder> {

    private LayoutInflater layoutInflater;
    private Context context;
    private List<Inmueble> inmuebles;
    private View root;

    public RealEstatesAdapter(
            View root,
            List<Inmueble> inmuebles
    ) {
        this.root = root;
        this.layoutInflater = LayoutInflater.from(root.getContext());
        this.context = root.getContext();
        this.inmuebles = inmuebles;
    }

    @NonNull
    @Override // Referenciar a la vista item_real_estate y pasarla a la clase MiViewHolder
    public MiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_real_estate, parent, false);
        return new MiViewHolder(view);
    }

    @Override // Se ejecuta por cada inmueble de la lista
    public void onBindViewHolder(@NonNull MiViewHolder holder, int position) {
        Inmueble i = inmuebles.get(position);
        holder.tvAddress.setText(i.getDireccion());
        // Crear un NumberFormat para el locale español
        NumberFormat formatoEspanol = NumberFormat.getNumberInstance(new Locale("es", "AR"));
        // Asegurar que el formato utilice 2 decimales
        formatoEspanol.setMinimumFractionDigits(2);
        formatoEspanol.setMaximumFractionDigits(2);
        // Convertir el número a texto
        String numeroTexto = formatoEspanol.format(i.getPrecio());
        holder.tvDetails.setText(String.format("$%s", numeroTexto));
        Glide.with(root.getContext())
                .load(ApiClientRetrofit.getHost() + i.getImageUrl())
                .circleCrop()
                .into(holder.ivPhoto);
        holder.cvRealEstate.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("realEstate", i);
            Navigation.findNavController(root).navigate(R.id.realEstateDetailsFragment, bundle);
        });
    }

    @Override // Retorna la cardinalidad de la lista de movies
    public int getItemCount() {
        return inmuebles.size();
    }

    public class MiViewHolder extends RecyclerView.ViewHolder {

        private CardView cvRealEstate;
        private TextView tvDetails, tvAddress;
        private ImageView ivPhoto;

        public MiViewHolder(@NonNull View itemView) {
            super(itemView);
            cvRealEstate = itemView.findViewById(R.id.cvRealEstate);
            ivPhoto = itemView.findViewById(R.id.ivPhoto);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvDetails = itemView.findViewById(R.id.tvDetails);
        }
    }
}
