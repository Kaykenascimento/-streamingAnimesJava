package com.example.projetofirestore.Adapters;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetofirestore.Models.DTOImagens;
import com.example.projetofirestore.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterImagensPerfil extends RecyclerView.Adapter<AdapterImagensPerfil.MyViewHolder> {
    ArrayList<DTOImagens> imagensArrayList;
    Boolean checked = false;

    public AdapterImagensPerfil(ArrayList<DTOImagens> imagensArrayList){
        this.imagensArrayList = imagensArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_imagens, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String imagem = imagensArrayList.get(position).getLink();
        Picasso.get().load(imagem).into(holder.ivImagensPerfil);
    }

    @Override
    public int getItemCount() {
        return imagensArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImagensPerfil;
        FloatingActionButton fbDone;

        @RequiresApi(api = Build.VERSION_CODES.O)
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImagensPerfil = itemView.findViewById(R.id.ivImagensPerfil);
            fbDone = itemView.findViewById(R.id.fbDone);

            fbDone.setVisibility(View.INVISIBLE);

            ivImagensPerfil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checked){
                        fbDone.setVisibility(View.INVISIBLE);
                        checked = false;
                    }
                    else{
                        fbDone.setVisibility(View.VISIBLE);
                        checked = true;
                    }
                }
            });
        }
    }
}

