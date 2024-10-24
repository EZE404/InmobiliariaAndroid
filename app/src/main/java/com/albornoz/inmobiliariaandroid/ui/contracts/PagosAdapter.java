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

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class PagosAdapter extends RecyclerView.Adapter<PagosAdapter.MiViewHolder> {

    private LayoutInflater layoutInflater;
    private Context context;
    private List<Pago> pagos;
    private View root;

    public PagosAdapter(
            View root,
            List<Pago> pagos
    ) {
        this.root = root;
        this.layoutInflater = LayoutInflater.from(root.getContext());
        this.context = root.getContext();
        this.pagos = pagos;
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
        holder.tvId.setText("Código de Pago: "+p.getId());
        //holder.tvNum.setText("Número de Pago: "+p.getNumero());
        //holder.tvIdContrato.setText("Código de Contrato: "+p.getContrato().getId());
        // Crear un NumberFormat para el locale español
        NumberFormat formatoEspanol = NumberFormat.getNumberInstance(new Locale("es", "AR"));
        // Asegurar que el formato utilice 2 decimales
        formatoEspanol.setMinimumFractionDigits(2);
        formatoEspanol.setMaximumFractionDigits(2);
        // Convertir el número a texto
        String numeroTexto = formatoEspanol.format(p.getMonto());
        holder.tvImporte.setText(String.format("Importe: $%s", numeroTexto));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        holder.tvFecha.setText(String.format("Fecha de Pago: %s", sdf.format(p.getFecha())));
    }

    @Override // Retorna la cardinalidad de la lista de movies
    public int getItemCount() {
        return pagos.size();
    }

    public class MiViewHolder extends RecyclerView.ViewHolder {

        private TextView tvId;
        //private TextView tvNum;
        //private TextView tvIdContrato;
        private TextView tvImporte;
        private TextView tvFecha;

        public MiViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvIdPago);
            //tvNum = itemView.findViewById(R.id.tvNum);
            //tvIdContrato = itemView.findViewById(R.id.tvIdContrato);
            tvImporte = itemView.findViewById(R.id.tvImporte);
            tvFecha = itemView.findViewById(R.id.tvFecha);
        }
    }
}
