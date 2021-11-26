package com.example.projetofirestore.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetofirestore.Models.DTOEpisodios;
import com.example.projetofirestore.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterNextEpisode extends RecyclerView.Adapter<AdapterNextEpisode.MyViewHolder> {
    ArrayList<DTOEpisodios> dtoEpisodiosArrayList;

    public AdapterNextEpisode(ArrayList<DTOEpisodios> dtoEpisodiosArrayList){
        this.dtoEpisodiosArrayList = dtoEpisodiosArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_nextepisode, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  AdapterNextEpisode.MyViewHolder holder, int position) {
        holder.tvTitulo.setText(dtoEpisodiosArrayList.get(position).getTitulo());
        holder.tvDuracao.setText("Duração: "+dtoEpisodiosArrayList.get(position).getDuracao()+"min");
        holder.tvAno.setText("Ano: "+dtoEpisodiosArrayList.get(position).getAno());

        Picasso.get().load(dtoEpisodiosArrayList.get(position).getImagem()).into(holder.ivEpisodio);
    }

    @Override
    public int getItemCount() {
        return dtoEpisodiosArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivEpisodio;
        TextView tvTitulo, tvDuracao, tvAno;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivEpisodio = itemView.findViewById(R.id.ivProximoEp);
            tvTitulo = itemView.findViewById(R.id.tvTituloProxEp);
            tvDuracao = itemView.findViewById(R.id.tvDuracaoProxEp);
            tvAno = itemView.findViewById(R.id.tvAnoProxEp);

        }
    }
}

