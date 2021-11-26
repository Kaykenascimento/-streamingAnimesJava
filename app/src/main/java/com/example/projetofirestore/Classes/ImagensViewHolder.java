package com.example.projetofirestore.Classes;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetofirestore.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ImagensViewHolder extends RecyclerView.ViewHolder {
    public ImageView ivImagensPerfil;
    public FloatingActionButton fbDone;

    public ImagensViewHolder(@NonNull View itemView) {
        super(itemView);

        ivImagensPerfil = itemView.findViewById(R.id.ivImagensPerfil);
        fbDone = itemView.findViewById(R.id.fbDone);
    }
}
