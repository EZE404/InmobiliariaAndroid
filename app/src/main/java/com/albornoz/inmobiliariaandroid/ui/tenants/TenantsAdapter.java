package com.albornoz.inmobiliariaandroid.ui.tenants;

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
import com.albornoz.inmobiliariaandroid.request.ApiClient;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.common.api.Api;

import java.util.List;

public class TenantsAdapter extends RecyclerView.Adapter<TenantsAdapter.MiViewHolder> {

    private LayoutInflater layoutInflater;
    private Context context;
    private List<Inmueble> inmuebles;
    private View root;
    private ApiClient api;

    public TenantsAdapter(
            View root,
            List<Inmueble> inmuebles
    ) {
        this.root = root;
        this.layoutInflater = LayoutInflater.from(root.getContext());
        this.context = root.getContext();
        this.inmuebles = inmuebles;
        this.api = ApiClient.getApi();
    }

    @NonNull
    @Override // Referenciar a la vista item_movie y pasarla a la clase MiViewHolder
    public TenantsAdapter.MiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_real_estate, parent, false);
        return new TenantsAdapter.MiViewHolder(view);
    }

    @Override // Se ejecuta por cada movie de la lista
    public void onBindViewHolder(@NonNull TenantsAdapter.MiViewHolder holder, int position) {
        Inmueble i = inmuebles.get(position);
        holder.tvAddress.setText(i.getDireccion());
        //holder.tvDetails.setText(String.valueOf(i.getPrecio()));
        //holder.tvDetails.setText("$"+i.getPrecio());
        holder.tvDetails.setText(api.obtenerInquilino(i).getNombre()+" "+api.obtenerInquilino(i).getApellido());
        Glide.with(root.getContext())
                .load(i.getImagen())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivPhoto);
        holder.cvRealEstate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("realEstate", i);
                Navigation.findNavController(root).navigate(R.id.tenantDetailsFragment, bundle); // TODO: CAMBIAR A R.id.tenantDetailsFragment
            }
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
