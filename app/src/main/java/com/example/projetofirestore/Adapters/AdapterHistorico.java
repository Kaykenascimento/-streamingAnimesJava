package com.example.projetofirestore.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projetofirestore.Models.DTOHistorico;
import com.example.projetofirestore.R;
import com.google.android.exoplayer2.ui.DefaultTimeBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterHistorico extends RecyclerView.Adapter<AdapterHistorico.MyViewHolder> {
    ArrayList<DTOHistorico> dtoHistoricoArrayList;
    Context context;

    public AdapterHistorico(ArrayList<DTOHistorico> dtoHistoricoArrayList){
        this.dtoHistoricoArrayList = dtoHistoricoArrayList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_historico, parent, false);
        context = parent.getContext();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  AdapterHistorico.MyViewHolder holder, int position) {
        holder.tvNomeAnime.setText(dtoHistoricoArrayList.get(position).getNomeAnime());
        holder.tvTituloEp.setText(dtoHistoricoArrayList.get(position).getTituloEp());
        String imagem = dtoHistoricoArrayList.get(position).getImagem();

        int duracao = Integer.parseInt(dtoHistoricoArrayList.get(position).getDuracao());
        long dur = duracao * 60000;
        long min = dtoHistoricoArrayList.get(position).getMinutoAssistido();

        holder.tbAssistidoHist.setDuration(dur);
        holder.tbAssistidoHist.setPosition(min);
        holder.tbAssistidoHist.setEnabled(false);

        if(imagem.isEmpty()|| imagem == null){
            Log.d("Erro", "Não possui imagem");
        }
        else {
            Glide.with(context).load(imagem).into(holder.ivHistorico);
        }
    }

    @Override
    public int getItemCount() {
        return dtoHistoricoArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivHistorico;
        TextView tvNomeAnime, tvTituloEp;
        DefaultTimeBar tbAssistidoHist;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivHistorico = itemView.findViewById(R.id.ivHistorico);
            tvNomeAnime = itemView.findViewById(R.id.tvNomeAnimeHist);
            tvTituloEp = itemView.findViewById(R.id.tvTituloEpHist);
            tbAssistidoHist = itemView.findViewById(R.id.tbAssistidoHist);
        }
    }
}
