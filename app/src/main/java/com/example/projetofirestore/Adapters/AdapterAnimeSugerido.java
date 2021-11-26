package com.example.projetofirestore.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetofirestore.Activitys.EpisodiosActivity;
import com.example.projetofirestore.Models.DTOAnimes;
import com.example.projetofirestore.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterAnimeSugerido extends RecyclerView.Adapter<AdapterAnimeSugerido.MyViewHolder> {
    ArrayList<DTOAnimes> sugeridosArrayList;
    Context mContext;

    private static Activity desembrulhar (Context context) {
        while (!(context instanceof Activity) && context instanceof ContextWrapper) {
            context = ((ContextWrapper) context).getBaseContext();
        }
        return (Activity) context;
    }

    public AdapterAnimeSugerido(ArrayList<DTOAnimes> sugeridosArrayList){
        this.sugeridosArrayList = sugeridosArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_sugestao_anime, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAnimeSugerido.MyViewHolder holder, int position) {
        holder.tvNomeAnimeSug.setText(sugeridosArrayList.get(position).getNomeAnime());
        holder.tvSinopseSug.setText(sugeridosArrayList.get(position).getSinopse());
        holder.buttonAssistirAgora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) desembrulhar(v.getContext());
                Intent intent = new Intent(activity, EpisodiosActivity.class);
                intent.putExtra("nomeAnime", sugeridosArrayList.get(position).getNomeAnime());
                intent.putExtra("codigo", sugeridosArrayList.get(position).getCodigo());
                /**intent.putExtra("sinopse", (String) null);
                intent.putExtra("estudio", (String) null);
                intent.putExtra("ano", (String) null);
                intent.putExtra("genero", (String) null);
                intent.putExtra("imagem", (String) null);
                 */
                activity.startActivity(intent);
            }
        });

        String imagem = sugeridosArrayList.get(position).getImagem();

        Picasso.get().load(imagem).into(holder.ivAnimeSugerido);
    }

    @Override
    public int getItemCount() {
        return sugeridosArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAnimeSugerido;
        TextView tvNomeAnimeSug, tvSinopseSug;
        Button buttonAssistirAgora;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAnimeSugerido = itemView.findViewById(R.id.ivAnimeSugerido);
            tvNomeAnimeSug = itemView.findViewById(R.id.tvNomeAnimeSug);
            tvSinopseSug = itemView.findViewById(R.id.tvSinopseAnimeSug);
            buttonAssistirAgora = itemView.findViewById(R.id.btAssistirAgora);
        }
    }
}
