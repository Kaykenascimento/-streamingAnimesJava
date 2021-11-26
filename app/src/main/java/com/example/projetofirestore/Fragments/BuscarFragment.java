package com.example.projetofirestore.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.projetofirestore.Activitys.AnimeSelecionadoActivity;
import com.example.projetofirestore.Classes.RecyclerViewItemClickListener;
import com.example.projetofirestore.Models.DTOAnimes;
import com.example.projetofirestore.R;
import com.example.projetofirestore.DAO.AnimesDAO;

import java.util.ArrayList;

public class BuscarFragment extends Fragment {
    SearchView searchViewAnimes;
    RecyclerView rcBuscarAnimes;
    TextView tvBuscar1, tvBuscar2;
    ArrayList<DTOAnimes> animesArrayList = new ArrayList<>();
    AnimesDAO animesDAO = new AnimesDAO();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_buscar, container, false);

        searchViewAnimes = view.findViewById(R.id.searchViewAnimes);
        rcBuscarAnimes = view.findViewById(R.id.rcBuscarAnimes);
        tvBuscar1 = view.findViewById(R.id.tvBuscar1);
        tvBuscar2 = view.findViewById(R.id.tvBuscar2);

        rcBuscarAnimes.setLayoutManager(new GridLayoutManager(rcBuscarAnimes.getContext(), 2, GridLayoutManager.VERTICAL, false));

        tvBuscar1.setVisibility(View.INVISIBLE);
        tvBuscar2.setVisibility(View.INVISIBLE);

        searchViewAnimes.setIconified(false);
        searchViewAnimes.setQueryHint("Buscar nome do anime");
        searchViewAnimes.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String animeBusca) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String animeBusca) {
                animesDAO.buscarAnimes(rcBuscarAnimes, animesArrayList, animeBusca, tvBuscar1, tvBuscar2);
                if(animeBusca.length()<1){
                    rcBuscarAnimes.setVisibility(View.INVISIBLE);
                }
                else{
                    rcBuscarAnimes.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });

        rcBuscarAnimes.addOnItemTouchListener(new RecyclerViewItemClickListener(getActivity(), rcBuscarAnimes, new RecyclerViewItemClickListener.OnItemClickListener() {
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