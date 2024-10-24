package com.albornoz.inmobiliariaandroid.ui.contracts;

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

import java.util.List;

public class ContractsAdapter extends RecyclerView.Adapter<ContractsAdapter.MiViewHolder> {

    private LayoutInflater layoutInflater;
    //private Context context;
    private List<Inmueble> inmuebles;
    private View root;

    public ContractsAdapter(
            View root,
            List<Inmueble> inmuebles
    ) {
        this.root = root;
        this.layoutInflater = LayoutInflater.from(root.getContext());
        //this.context = root.getContext();
        this.inmuebles = inmuebles;
    }

    @NonNull
    @Override // Referenciar a la vista item_real_estate y pasarla a la clase MiViewHolder
    public ContractsAdapter.MiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_real_estate, parent, false);
        return new ContractsAdapter.MiViewHolder(view);
    }

    @Override // Se ejecuta por cada inmueble de la lista
    public void onBindViewHolder(@NonNull ContractsAdapter.MiViewHolder holder, int position) {
        Inmueble i = inmuebles.get(position);
        holder.tvAddress.setText(i.getDireccion());
        holder.tvDetails.setText(String.format("%s - %s", i.getTipo(), i.getUso()));
        Glide.with(root.getContext())
                .load(ApiClientRetrofit.getHost() + i.getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .circleCrop()
                .into(holder.ivPhoto);
        holder.cvRealEstate.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("realEstate", i);
            Navigation.findNavController(root).navigate(R.id.contractDetailsFragment, bundle);
        });
    }

    @Override // Retorna la cardinalidad de la lista de movies
    public int getItemCount() {
        return inmuebles.size();
    }

    public class MiViewHolder extends RecyclerView.ViewHolder {

        private CardView cvRealEstate;
        private TextView tvDetails;
        private TextView tvAddress;
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
