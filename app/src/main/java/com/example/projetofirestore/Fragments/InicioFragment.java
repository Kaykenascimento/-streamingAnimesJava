package com.example.projetofirestore.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.projetofirestore.Activitys.AnimeSelecionadoActivity;
import com.example.projetofirestore.Activitys.AssistirEpsActivity;
import com.example.projetofirestore.Classes.ConfFireBase;
import com.example.projetofirestore.Classes.RecyclerViewItemClickListener;
import com.example.projetofirestore.DAO.AnimesDAO;
import com.example.projetofirestore.Models.DTOAnimes;
import com.example.projetofirestore.Models.DTOHistorico;
import com.example.projetofirestore.R;
import com.example.projetofirestore.Testes.TesteDAO;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class InicioFragment extends Fragment {
    RecyclerView rcAnimesRecentes, rcAnimesPopulares, rcAnimeSugerido1, rcAnimeSugerido2, rcAnimesAventura, rcAnimesAcao, rcContinueAssistindo;
    TextView tvContinueAssistindo, tvAventura, tvAdRecentes, tvAcao, tvPopulares;
    FirebaseAuth auth = ConfFireBase.getFirebaseAuth();
    ArrayList<DTOAnimes> animesRecentesArrayList = new ArrayList<>();
    ArrayList<DTOAnimes> animesPopularesArrayList = new ArrayList<>();
    ArrayList<DTOAnimes> animesSugeridosArrayList = new ArrayList<>();
    ArrayList<DTOAnimes> animesSugeridosArrayList2 = new ArrayList<>();
    ArrayList<DTOAnimes> animesAventuraArrayLIst = new ArrayList<>();
    ArrayList<DTOAnimes> animesAcaoArrayList = new ArrayList<>();
    ArrayList<DTOHistorico> episodiosArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inicio, container, false);

        rcAnimesRecentes = view.findViewById(R.id.rcAnimesRecentes);
        rcAnimesPopulares = view.findViewById(R.id.rcAnimesPopulares);
        rcAnimesAventura = view.findViewById(R.id.rcAnimesAventura);
        rcAnimesAcao = view.findViewById(R.id.rcAnimesAcao);
        rcAnimeSugerido1 = view.findViewById(R.id.rcAnimeSugerido1);
        rcAnimeSugerido2 = view.findViewById(R.id.rcAnimeSugerido2);
        rcContinueAssistindo = view.findViewById(R.id.rcContinueAssistindo);
        tvContinueAssistindo = view.findViewById(R.id.tvContinueAssistindo);
        tvAdRecentes = view.findViewById(R.id.tvAdRecentes);
        tvAventura = view.findViewById(R.id.tvAventura);
        tvAcao = view.findViewById(R.id.tvAcao);
        tvPopulares = view.findViewById(R.id.tvPopulares);

        tvAdRecentes.setVisibility(View.INVISIBLE);
        tvAventura.setVisibility(View.INVISIBLE);
        tvAcao.setVisibility(View.INVISIBLE);
        tvPopulares.setVisibility(View.INVISIBLE);
        tvContinueAssistindo.setVisibility(View.INVISIBLE);

        rcAnimesRecentes.setLayoutManager(new LinearLayoutManager(rcAnimesRecentes.getContext(), RecyclerView.HORIZONTAL, false));
        rcAnimesPopulares.setLayoutManager(new LinearLayoutManager(rcAnimesPopulares.getContext(), RecyclerView.HORIZONTAL, false));
        rcAnimesAventura.setLayoutManager(new LinearLayoutManager(rcAnimesAventura.getContext(), RecyclerView.HORIZONTAL, false));
        rcAnimesAcao.setLayoutManager(new LinearLayoutManager(rcAnimesAcao.getContext(), RecyclerView.HORIZONTAL, false));
        rcAnimeSugerido1.setLayoutManager(new LinearLayoutManager(rcAnimeSugerido1.getContext(), RecyclerView.VERTICAL, false));
        rcAnimeSugerido2.setLayoutManager(new LinearLayoutManager(rcAnimeSugerido2.getContext(), RecyclerView.VERTICAL, false));
        rcContinueAssistindo.setLayoutManager(new LinearLayoutManager(rcContinueAssistindo.getContext(), RecyclerView.HORIZONTAL, false));

        rcAnimesRecentes.addOnItemTouchListener(new RecyclerViewItemClickListener(getActivity(), rcAnimesRecentes, new RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), AnimeSelecionadoActivity.class);
                intent.putExtra("nomeAnime", animesRecentesArrayList.get(position).getNomeAnime());
                intent.putExtra("sinopse", animesRecentesArrayList.get(position).getSinopse());
                intent.putExtra("ano", animesRecentesArrayList.get(position).getAno());
                intent.putExtra("estudio", animesRecentesArrayList.get(position).getEstudio());
                intent.putExtra("imagem", animesRecentesArrayList.get(position).getImagem());
                intent.putExtra("genero", animesRecentesArrayList.get(position).getGenero());
                intent.putExtra("codigo", animesRecentesArrayList.get(position).getCodigo());
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }));

        rcAnimesPopulares.addOnItemTouchListener(new RecyclerViewItemClickListener(getContext(), rcAnimesPopulares, new RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), AnimeSelecionadoActivity.class);
                intent.putExtra("nomeAnime", animesPopularesArrayList.get(position).getNomeAnime());
                intent.putExtra("sinopse", animesPopularesArrayList.get(position).getSinopse());
                intent.putExtra("ano", animesPopularesArrayList.get(position).getAno());
                intent.putExtra("estudio", animesPopularesArrayList.get(position).getEstudio());
                intent.putExtra("imagem", animesPopularesArrayList.get(position).getImagem());
                intent.putExtra("genero", animesPopularesArrayList.get(position).getGenero());
                intent.putExtra("codigo", animesPopularesArrayList.get(position).getCodigo());
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }));

        rcAnimesAventura.addOnItemTouchListener(new RecyclerViewItemClickListener(getContext(), rcAnimesAventura, new RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), AnimeSelecionadoActivity.class);
                intent.putExtra("nomeAnime", animesAventuraArrayLIst.get(position).getNomeAnime());
                intent.putExtra("genero", animesAventuraArrayLIst.get(position).getGenero());
                intent.putExtra("estudio", animesAventuraArrayLIst.get(position).getEstudio());
                intent.putExtra("ano", animesAventuraArrayLIst.get(position).getAno());
                intent.putExtra("sinopse", animesAventuraArrayLIst.get(position).getSinopse());
                intent.putExtra("imagem", animesAventuraArrayLIst.get(position).getImagem());
                intent.putExtra("codigo", animesAventuraArrayLIst.get(position).getCodigo());
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }));

        rcAnimesAcao.addOnItemTouchListener(new RecyclerViewItemClickListener(getContext(), rcAnimesAcao, new RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), AnimeSelecionadoActivity.class);
                intent.putExtra("nomeAnime", animesAcaoArrayList.get(position).getNomeAnime());
                intent.putExtra("genero", animesAcaoArrayList.get(position).getGenero());
                intent.putExtra("estudio", animesAcaoArrayList.get(position).getEstudio());
                intent.putExtra("ano", animesAcaoArrayList.get(position).getAno());
                intent.putExtra("sinopse", animesAcaoArrayList.get(position).getSinopse());
                intent.putExtra("imagem", animesAcaoArrayList.get(position).getImagem());
                intent.putExtra("codigo", animesAcaoArrayList.get(position).getCodigo());
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }));

        rcContinueAssistindo.addOnItemTouchListener(new RecyclerViewItemClickListener(getActivity(), rcContinueAssistindo, new RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), AssistirEpsActivity.class);
                intent.putExtra("nomeAnime", episodiosArrayList.get(position).getNomeAnime());
                intent.putExtra("titulo", episodiosArrayList.get(position).getTituloEp());
                intent.putExtra("duracao", episodiosArrayList.get(position).getDuracao());
                intent.putExtra("minutoAssistido", episodiosArrayList.get(position).getMinutoAssistido());
                intent.putExtra("link", episodiosArrayList.get(position).getLink());
                intent.putExtra("saga", episodiosArrayList.get(position).getSaga());
                intent.putExtra("codigo", episodiosArrayList.get(position).getCodigo());
                intent.putExtra("sinopse", episodiosArrayList.get(position).getSinopse());
                intent.putExtra("imagem", episodiosArrayList.get(position).getImagem());
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

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        AnimesDAO animesDAO = new AnimesDAO();
        animesDAO.carregarAnimeSugerido1(rcAnimeSugerido1, animesSugeridosArrayList);
        if(user != null){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                animesDAO.consultarAnimesRecentes(rcAnimesRecentes, animesRecentesArrayList, tvAdRecentes);
                animesDAO.carregarAnimesPopulares(rcAnimesPopulares, animesPopularesArrayList, tvPopulares);
                animesDAO.carregarAnimesAventura(rcAnimesAventura, animesAventuraArrayLIst, tvAventura);
                animesDAO.carregarAnimesAcao(rcAnimesAcao, animesAcaoArrayList, tvAcao);
                animesDAO.continueAssistindo(rcContinueAssistindo, episodiosArrayList, tvContinueAssistindo);
                animesDAO.carregarAnimeSugerido2(rcAnimeSugerido2, animesSugeridosArrayList2);
            }
        }, 500);
    }else{
            animesDAO.consultarAnimesRecentes(rcAnimesRecentes, animesRecentesArrayList, tvAdRecentes);
            animesDAO.carregarAnimesPopulares(rcAnimesPopulares, animesPopularesArrayList, tvPopulares);
            animesDAO.carregarAnimesAventura(rcAnimesAventura, animesAventuraArrayLIst, tvAventura);
            animesDAO.carregarAnimesAcao(rcAnimesAcao, animesAcaoArrayList, tvAcao);
            animesDAO.carregarAnimeSugerido2(rcAnimeSugerido2, animesSugeridosArrayList2);
        }
    }
}
