package com.albornoz.inmobiliariaandroid.ui.contracts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.albornoz.inmobiliariaandroid.R;
import com.albornoz.inmobiliariaandroid.modelo.Pago;
import com.albornoz.inmobiliariaandroid.request.ApiClient;

import java.util.List;

public class PagosAdapter extends RecyclerView.Adapter<PagosAdapter.MiViewHolder> {

    private LayoutInflater layoutInflater;
    private Context context;
    private List<Pago> pagos;
    private View root;
    private ApiClient api;

    public PagosAdapter(
            View root,
            List<Pago> pagos
    ) {
        this.root = root;
        this.layoutInflater = LayoutInflater.from(root.getContext());
        this.context = root.getContext();
        this.pagos = pagos;
        this.api = ApiClient.getApi();
    }

    @NonNull
    @Override // Referenciar a la vista item_movie y pasarla a la clase MiViewHolder
    public PagosAdapter.MiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_payment, parent, false);
        return new PagosAdapter.MiViewHolder(view);
    }

    @Override // Se ejecuta por cada movie de la lista
    public void onBindViewHolder(@NonNull PagosAdapter.MiViewHolder holder, int position) {
        Pago p = pagos.get(position);
        holder.tvId.setText(String.valueOf(p.getIdPago()));
        holder.tvNum.setText(String.valueOf(p.getNumero()));
        holder.tvIdContrato.setText(String.valueOf(p.getContrato().getIdContrato()));
        holder.tvImporte.setText("$"+p.getImporte());
        holder.tvFecha.setText(p.getFechaDePago());
    }

    @Override // Retorna la cardinalidad de la lista de movies
    public int getItemCount() {
        return pagos.size();
    }

    public class MiViewHolder extends RecyclerView.ViewHolder {

        private TextView tvId, tvNum, tvIdContrato, tvImporte, tvFecha;

        public MiViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvIdPago);
            tvNum = itemView.findViewById(R.id.tvNum);
            tvIdContrato = itemView.findViewById(R.id.tvIdContrato);
            tvImporte = itemView.findViewById(R.id.tvImporte);
            tvFecha = itemView.findViewById(R.id.tvFecha);
        }
    }
}
