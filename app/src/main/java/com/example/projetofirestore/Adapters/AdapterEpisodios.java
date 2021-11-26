package com.example.projetofirestore.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projetofirestore.Models.DTOEpisodios;
import com.example.projetofirestore.R;

import java.util.ArrayList;

public class AdapterEpisodios extends RecyclerView.Adapter<AdapterEpisodios.MyViewHolder> {
    ArrayList<DTOEpisodios> dtoEpisodiosArrayList;
    Context context;

    public AdapterEpisodios(ArrayList<DTOEpisodios> dtoEpisodiosArrayList){
        this.dtoEpisodiosArrayList = dtoEpisodiosArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_episodios, parent, false);
        context = parent.getContext();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  AdapterEpisodios.MyViewHolder holder, int position) {
        holder.tvTitulo.setText(dtoEpisodiosArrayList.get(position).getTitulo());
        holder.tvSinopse.setText(dtoEpisodiosArrayList.get(position).getSinopse());
        holder.tvDuracao.setText(dtoEpisodiosArrayList.get(position).getDuracao()+"min");
        holder.tvAno.setText("Ano: "+dtoEpisodiosArrayList.get(position).getAno());

        Glide.with(context).load(dtoEpisodiosArrayList.get(position).getImagem()).into(holder.ivEpisodio);

    }

    @Override
    public int getItemCount() {
        return dtoEpisodiosArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivEpisodio;
        TextView tvTitulo, tvSinopse, tvDuracao, tvAno;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivEpisodio = itemView.findViewById(R.id.ivHistorico);
            tvTitulo = itemView.findViewById(R.id.tvNomeAnimeHist);
            tvSinopse = itemView.findViewById(R.id.tvTituloEpHist);
            tvDuracao = itemView.findViewById(R.id.tvDuracaoEp);
            tvAno = itemView.findViewById(R.id.tvAnoEp);
        }
    }
}
