package com.example.deteccioninsuficienciarenal.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deteccioninsuficienciarenal.EnviarReporte;
import com.example.deteccioninsuficienciarenal.R;
import com.example.deteccioninsuficienciarenal.Riesgo;
import com.example.deteccioninsuficienciarenal.VerResultados;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class ListaRiesgosAdapter extends RecyclerView.Adapter<ListaRiesgosAdapter.RiesgoViewHolder> {

    ArrayList<Riesgo> listaRiesgo;

    public ListaRiesgosAdapter(ArrayList<Riesgo> listaRiesgo){
        this.listaRiesgo = listaRiesgo;
    }

    @NonNull
    @Override
    public RiesgoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.resultados, null, false);
        return new RiesgoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RiesgoViewHolder holder, int position) {
        holder.resultados_fecha.setText(listaRiesgo.get(position).getCreatedat());
    }

    @Override
    public int getItemCount() {
        return listaRiesgo.size();
    }

    public class RiesgoViewHolder extends RecyclerView.ViewHolder {

        TextView resultados_fecha;
        Button ver, enviar;
        public RiesgoViewHolder(@NonNull View itemView) {
            super(itemView);
            resultados_fecha = itemView.findViewById(R.id.resultados_fecha);
            ver = itemView.findViewById(R.id.ver);
            enviar = itemView.findViewById(R.id.enviar);

            ver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, VerResultados.class);
                    intent.putExtra("idrisk", listaRiesgo.get(getAdapterPosition()).getIdresults());
                    context.startActivity(intent);
                }
            });

            enviar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, EnviarReporte.class);
                    intent.putExtra("idrisk", listaRiesgo.get(getAdapterPosition()).getIdresults());
                    context.startActivity(intent);
                }
            });
        }

        public void idresult(){
            System.out.println(listaRiesgo.get(getAdapterPosition()).getIdresults());
        }


    }
}

