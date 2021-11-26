package com.example.projetofirestore.Classes;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetofirestore.R;

public class GenerosViewHolder extends RecyclerView.ViewHolder {
    public ImageView ivGeneros;
    public TextView tvNomeGenero;

    public GenerosViewHolder(@NonNull View itemView) {
        super(itemView);

        ivGeneros = itemView.findViewById(R.id.ivGeneros);
        tvNomeGenero = itemView.findViewById(R.id.tvNomeGenero);
    }
}
