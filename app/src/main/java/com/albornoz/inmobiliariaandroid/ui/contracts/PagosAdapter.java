package com.albornoz.inmobiliariaandroid.ui.contracts;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.albornoz.inmobiliariaandroid.databinding.ItemPaymentBinding;
import com.albornoz.inmobiliariaandroid.modelo.Pago;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class PagosAdapter extends RecyclerView.Adapter<PagosAdapter.MiViewHolder> {

    private List<Pago> pagos;

    public PagosAdapter(List<Pago> pagos) {
        this.pagos = pagos;
    }

    @NonNull
    @Override
    public PagosAdapter.MiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemPaymentBinding binding = ItemPaymentBinding.inflate(layoutInflater, parent, false);
        return new MiViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PagosAdapter.MiViewHolder holder, int position) {
        Pago p = pagos.get(position);
        holder.binding.tvIdPago.setText("Código de Pago: " + p.getId());

        // Formateo del importe
        NumberFormat formatoEspanol = NumberFormat.getNumberInstance(new Locale("es", "AR"));
        formatoEspanol.setMinimumFractionDigits(2);
        formatoEspanol.setMaximumFractionDigits(2);
        String numeroTexto = formatoEspanol.format(p.getMonto());
        holder.binding.tvImporte.setText(String.format("Importe: $%s", numeroTexto));

        // Formateo de la fecha
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        holder.binding.tvFecha.setText(String.format("Fecha de Pago: %s", sdf.format(p.getFecha())));
    }

    @Override
    public int getItemCount() {
        return pagos.size();
    }

    public class MiViewHolder extends RecyclerView.ViewHolder {
        private final ItemPaymentBinding binding;

        public MiViewHolder(@NonNull ItemPaymentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
