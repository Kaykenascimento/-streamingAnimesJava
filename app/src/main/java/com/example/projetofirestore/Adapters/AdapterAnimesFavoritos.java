package com.example.projetofirestore.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetofirestore.Models.DTOAnimesFavoritos;
import com.example.projetofirestore.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterAnimesFavoritos extends RecyclerView.Adapter<AdapterAnimesFavoritos.MyViewHolder> {
    ArrayList<DTOAnimesFavoritos> dtoAnimesFavoritosArrayList;

    public AdapterAnimesFavoritos(ArrayList<DTOAnimesFavoritos> dtoAnimesFavoritosArrayList){
        this.dtoAnimesFavoritosArrayList = dtoAnimesFavoritosArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_lista_favoritos, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAnimesFavoritos.MyViewHolder holder, int position) {
        holder.tvNomeAnimeFav.setText(dtoAnimesFavoritosArrayList.get(position).getNomeAnime());
        holder.tvDataAdicionadoFav.setText("Adicionado em: "+dtoAnimesFavoritosArrayList.get(position).getDataAdicionado());
        String imagem = dtoAnimesFavoritosArrayList.get(position).getImagem();
        Picasso.get().load(imagem).into(holder.ivFavoritos);
    }

    @Override
    public int getItemCount() {
        return dtoAnimesFavoritosArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFavoritos;
        TextView tvNomeAnimeFav, tvDataAdicionadoFav;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFavoritos = itemView.findViewById(R.id.ivFavoritos);
            tvNomeAnimeFav = itemView.findViewById(R.id.tvNomeAnimeFav);
            tvDataAdicionadoFav = itemView.findViewById(R.id.tvDataAdicionadoFav);
        }
    }
}
