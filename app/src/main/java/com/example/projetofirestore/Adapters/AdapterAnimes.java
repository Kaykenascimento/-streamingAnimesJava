package com.example.projetofirestore.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetofirestore.Classes.ConfFireBase;
import com.example.projetofirestore.Models.DTOAnimes;
import com.example.projetofirestore.Models.DTOUsuario;
import com.example.projetofirestore.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterAnimes extends RecyclerView.Adapter<AdapterAnimes.MyViewHolder> {
    ArrayList<DTOAnimes> dtoAnimesArrayList;

    public AdapterAnimes(ArrayList<DTOAnimes> dtoAnimesArrayList){
        this.dtoAnimesArrayList = dtoAnimesArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_animes, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAnimes.MyViewHolder holder, int position) {
        holder.tvNomeAnime.setText(dtoAnimesArrayList.get(position).getNomeAnime());
        String imagem = dtoAnimesArrayList.get(position).getImagem();

        Picasso.get().load(imagem).into(holder.imageViewAnime);
    }

    @Override
    public int getItemCount() {
        return dtoAnimesArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewAnime;
        TextView tvNomeAnime;

        public MyViewHolder(@NonNull  View itemView) {
            super(itemView);
            tvNomeAnime = itemView.findViewById(R.id.tvNomeAnime);
            imageViewAnime = itemView.findViewById(R.id.imageViewAnime);
        }
    }
}
