package com.example.projetofirestore.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projetofirestore.Activitys.AnimeSelecionadoActivity;
import com.example.projetofirestore.Adapters.AdapterCatalogo;
import com.example.projetofirestore.Classes.ConfFireBase;
import com.example.projetofirestore.Classes.RecyclerViewItemClickListener;
import com.example.projetofirestore.Models.DTOAnimes;
import com.example.projetofirestore.R;
import com.example.projetofirestore.DAO.AnimesDAO;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AnimesGeneroFragment extends Fragment {
    FirebaseFirestore db = ConfFireBase.getFirebaseFirestore();
    RecyclerView rcAnimesGenero;
    TextView tvAnimeGenero;
    AdapterCatalogo adapterCatalogo;
    ArrayList<DTOAnimes> animesArrayList = new ArrayList<>();
    String genero;
    ImageView btVoltarGen;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_animes_genero, container, false);

        rcAnimesGenero = view.findViewById(R.id.rcAnimesGenero);
        tvAnimeGenero = view.findViewById(R.id.tvGenero);
        btVoltarGen = view.findViewById(R.id.btVoltarGen);

        Bundle bundle = getArguments();
        genero = bundle.getString("genero");
        tvAnimeGenero.setText(genero);

        rcAnimesGenero.setLayoutManager(new GridLayoutManager(rcAnimesGenero.getContext(), 2, GridLayoutManager.VERTICAL, false));

        rcAnimesGenero.addOnItemTouchListener(new RecyclerViewItemClickListener(getActivity(), rcAnimesGenero, new RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), AnimeSelecionadoActivity.class);
                intent.putExtra("nomeAnime", animesArrayList.get(position).getNomeAnime());
                intent.putExtra("codigo", animesArrayList.get(position).getCodigo());
                intent.putExtra("genero", animesArrayList.get(position).getGenero());
                intent.putExtra("estudio", animesArrayList.get(position).getEstudio());
                intent.putExtra("ano", animesArrayList.get(position).getAno());
                intent.putExtra("sinopse", animesArrayList.get(position).getSinopse());
                intent.putExtra("imagem", animesArrayList.get(position).getImagem());
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }));

        btVoltarGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CatalogoFragment fragment3 = new CatalogoFragment();
                FragmentTransaction transaction3 = getFragmentManager().beginTransaction();
                transaction3.replace(R.id.frameLayoutPrincipal, fragment3);
                transaction3.commit();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        AnimesDAO animesDAO = new AnimesDAO();
        animesDAO.carregarAnimesPorGenero(rcAnimesGenero, animesArrayList, genero, tvAnimeGenero);
    }
}