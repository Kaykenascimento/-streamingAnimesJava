package com.example.projetofirestore.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.projetofirestore.Activitys.AnimeSelecionadoActivity;
import com.example.projetofirestore.Classes.RecyclerViewItemClickListener;
import com.example.projetofirestore.Models.DTOAnimes;
import com.example.projetofirestore.R;
import com.example.projetofirestore.DAO.AnimesDAO;

import java.util.ArrayList;

public class CatalogoListaFragment extends Fragment {
    ArrayList<DTOAnimes> animesArrayList = new ArrayList<>();
    RecyclerView rcCatalogo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_catalogo_lista, container, false);

        rcCatalogo = view.findViewById(R.id.rcCatalogo);
        rcCatalogo.setLayoutManager(new GridLayoutManager(rcCatalogo.getContext(), 2, GridLayoutManager.VERTICAL, false));

        AnimesDAO animesDAO = new AnimesDAO();
        animesDAO.carregarCatalogo(rcCatalogo, animesArrayList);

        rcCatalogo.addOnItemTouchListener(new RecyclerViewItemClickListener(getContext(), rcCatalogo, new RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), AnimeSelecionadoActivity.class);
                intent.putExtra("nomeAnime", animesArrayList.get(position).getNomeAnime());
                intent.putExtra("sinopse", animesArrayList.get(position).getSinopse());
                intent.putExtra("ano", animesArrayList.get(position).getAno());
                intent.putExtra("estudio", animesArrayList.get(position).getEstudio());
                intent.putExtra("imagem", animesArrayList.get(position).getImagem());
                intent.putExtra("genero", animesArrayList.get(position).getGenero());
                intent.putExtra("codigo", animesArrayList.get(position).getCodigo());
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }));

        return view;
    }
}