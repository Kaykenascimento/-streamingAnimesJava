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
import com.example.projetofirestore.Models.DTOHistorico;
import com.example.projetofirestore.R;
import com.google.android.exoplayer2.ui.DefaultTimeBar;

import java.util.ArrayList;

public class AdapterContinueAssistindo extends RecyclerView.Adapter<AdapterContinueAssistindo.MyViewHolder> {
    ArrayList<DTOHistorico> episodiosArrayList;
    Context context;

    public AdapterContinueAssistindo(ArrayList<DTOHistorico> episodiosArrayList){
        this.episodiosArrayList = episodiosArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_continue_assistindo, parent, false);
        context = parent.getContext();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvNomeAnime.setText(episodiosArrayList.get(position).getNomeAnime());
        holder.tvTituloEp.setText(episodiosArrayList.get(position).getTituloEp());
        String imagem = episodiosArrayList.get(position).getImagem();
        int duracao = Integer.parseInt(episodiosArrayList.get(position).getDuracao());

        long dur = duracao * 60000;
        long min = episodiosArrayList.get(position).getMinutoAssistido();

        Glide.with(context).load(imagem).into(holder.ivContinue);
        holder.pgContinue.setDuration(dur);
        holder.pgContinue.setPosition(min);
        holder.pgContinue.setEnabled(false);
    }

    @Override
    public int getItemCount() {
        return episodiosArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivContinue;
        TextView tvNomeAnime, tvTituloEp;
        DefaultTimeBar pgContinue;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivContinue = itemView.findViewById(R.id.ivContinueAssistindo);
            tvNomeAnime = itemView.findViewById(R.id.tvNomeAnimeCont);
            tvTituloEp = itemView.findViewById(R.id.tvTituloEpCont);
            pgContinue = itemView.findViewById(R.id.pgMinuto);
        }
    }
}
