package com.example.projetofirestore.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetofirestore.Models.DTOAnimes;
import com.example.projetofirestore.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterCatalogo extends RecyclerView.Adapter<AdapterCatalogo.MyViewHolder> {
    ArrayList<DTOAnimes> animesArrayList;

    public AdapterCatalogo(ArrayList<DTOAnimes> animesArrayList){
        this.animesArrayList = animesArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_catalogo, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCatalogo.MyViewHolder holder, int position) {
        holder.tvNomeAnimeCat.setText(animesArrayList.get(position).getNomeAnime());
        String imagem = animesArrayList.get(position).getImagem();

        Picasso.get().load(imagem).into(holder.ivCatalogo);

    }

    @Override
    public int getItemCount() {
        return animesArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCatalogo;
        TextView tvNomeAnimeCat;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCatalogo = itemView.findViewById(R.id.ivCatalogo);
            tvNomeAnimeCat = itemView.findViewById(R.id.tvNomeAnimeCat);
        }
    }
}
