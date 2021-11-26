package com.example.projetofirestore.DAO;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetofirestore.Adapters.AdapterAnimeSugerido;
import com.example.projetofirestore.Adapters.AdapterAnimes;
import com.example.projetofirestore.Adapters.AdapterCatalogo;
import com.example.projetofirestore.Adapters.AdapterContinueAssistindo;
import com.example.projetofirestore.Classes.ConfFireBase;
import com.example.projetofirestore.Models.DTOAnimes;
import com.example.projetofirestore.Models.DTOHistorico;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AnimesDAO {
    private FirebaseFirestore db = ConfFireBase.getFirebaseFirestore();
    private AdapterAnimes adapterAnimes;
    private AdapterAnimeSugerido adapterAnimeSugerido;
    private AdapterCatalogo adapterCatalogo;
    private AdapterContinueAssistindo adapterContinueAssistindo;
    private FirebaseAuth auth = ConfFireBase.getFirebaseAuth();

    public void consultarAnimesRecentes(RecyclerView recyclerView, ArrayList<DTOAnimes> animesArrayList, TextView textView){
        db.collection("animes").orderBy("nomeAnime", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(QueryDocumentSnapshot document: task.getResult()){
                    DTOAnimes dtoAnimes = new DTOAnimes();
                    dtoAnimes.setNomeAnime((String) document.getData().get("nomeAnime"));
                    dtoAnimes.setAno((String) document.getData().get("ano"));
                    dtoAnimes.setImagem((String) document.getData().get("imagem"));
                    dtoAnimes.setSinopse((String) document.getData().get("sinopse"));
                    dtoAnimes.setEstudio((String) document.getData().get("estudio"));
                    dtoAnimes.setGenero((String) document.getData().get("genero"));
                    dtoAnimes.setCodigo((String) document.getData().get("codigo"));
                    animesArrayList.add(dtoAnimes);
                }
                adapterAnimes = new AdapterAnimes(animesArrayList);
                recyclerView.setAdapter(adapterAnimes);
                textView.setVisibility(View.VISIBLE);
            }
        });
    }

    public void carregarAnimesPopulares(RecyclerView recyclerView, ArrayList<DTOAnimes> animesArrayList, TextView textView) {
        db.collection("animes").orderBy("nomeAnime").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        DTOAnimes dtoAnimes = new DTOAnimes();
                        dtoAnimes.setNomeAnime((String) document.getData().get("nomeAnime"));
                        dtoAnimes.setAno((String) document.getData().get("ano"));
                        dtoAnimes.setImagem((String) document.getData().get("imagem"));
                        dtoAnimes.setSinopse((String) document.getData().get("sinopse"));
                        dtoAnimes.setEstudio((String) document.getData().get("estudio"));
                        dtoAnimes.setGenero((String) document.getData().get("genero"));
                        dtoAnimes.setCodigo((String) document.getData().get("codigo"));
                        animesArrayList.add(dtoAnimes);
                    }
                    adapterAnimes = new AdapterAnimes(animesArrayList);
                    recyclerView.setAdapter(adapterAnimes);
                    textView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void carregarAnimesAventura(RecyclerView recyclerView, ArrayList<DTOAnimes> animesArrayList, TextView textView) {
        db.collection("animes").orderBy("genero")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DTOAnimes dtoAnimes = new DTOAnimes();
                                dtoAnimes.setNomeAnime((String) document.getData().get("nomeAnime"));
                                dtoAnimes.setAno((String) document.getData().get("ano"));
                                dtoAnimes.setImagem((String) document.getData().get("imagem"));
                                dtoAnimes.setSinopse((String) document.getData().get("sinopse"));
                                dtoAnimes.setEstudio((String) document.getData().get("estudio"));
                                dtoAnimes.setGenero((String) document.getData().get("genero"));
                                dtoAnimes.setCodigo((String) document.getData().get("codigo"));
                                String genero = dtoAnimes.getGenero();
                                if (genero.contains("Aventura")) {
                                    animesArrayList.add(dtoAnimes);
                                }
                            }
                            adapterAnimes = new AdapterAnimes(animesArrayList);
                            recyclerView.setAdapter(adapterAnimes);
                            textView.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }

    public void carregarAnimesAcao(RecyclerView recyclerView, ArrayList<DTOAnimes> animesArrayList, TextView textView) {
        db.collection("animes").orderBy("genero").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        DTOAnimes dtoAnimes = new DTOAnimes();
                        dtoAnimes.setNomeAnime((String) document.getData().get("nomeAnime"));
                        dtoAnimes.setAno((String) document.getData().get("ano"));
                        dtoAnimes.setImagem((String) document.getData().get("imagem"));
                        dtoAnimes.setSinopse((String) document.getData().get("sinopse"));
                        dtoAnimes.setEstudio((String) document.getData().get("estudio"));
                        dtoAnimes.setGenero((String) document.getData().get("genero"));
                        dtoAnimes.setCodigo((String) document.getData().get("codigo"));
                        String gen = dtoAnimes.getGenero();
                        if(gen.contains("Ação")){
                            animesArrayList.add(dtoAnimes);
                        }
                    }
                    adapterAnimes = new AdapterAnimes(animesArrayList);
                    recyclerView.setAdapter(adapterAnimes);
                    textView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void carregarAnimeSugerido1(RecyclerView recyclerView, ArrayList<DTOAnimes> animesArrayList) {
        db.collection("animeSugeridos").orderBy("nomeAnime", Query.Direction.DESCENDING).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        DTOAnimes dtoAnimes = new DTOAnimes();
                        dtoAnimes.setNomeAnime((String) document.getData().get("nomeAnime"));
                        dtoAnimes.setImagem((String) document.getData().get("imagem"));
                        dtoAnimes.setSinopse((String) document.getData().get("sinopse"));
                        dtoAnimes.setCodigo((String) document.getData().get("codigo"));
                        animesArrayList.add(dtoAnimes);
                    }
                    adapterAnimeSugerido = new AdapterAnimeSugerido(animesArrayList);
                    recyclerView.setAdapter(adapterAnimeSugerido);
                }
            }
        });
    }

    public void carregarAnimeSugerido2(RecyclerView recyclerView, ArrayList<DTOAnimes> animesArrayList) {
        db.collection("animeSugeridos").orderBy("nomeAnime", Query.Direction.ASCENDING).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        DTOAnimes dtoAnimes = new DTOAnimes();
                        dtoAnimes.setNomeAnime((String) document.getData().get("nomeAnime"));
                        dtoAnimes.setImagem((String) document.getData().get("imagem"));
                        dtoAnimes.setSinopse((String) document.getData().get("sinopse"));
                        dtoAnimes.setCodigo((String) document.getData().get("codigo"));
                        animesArrayList.add(dtoAnimes);
                    }
                    adapterAnimeSugerido = new AdapterAnimeSugerido(animesArrayList);
                    recyclerView.setAdapter(adapterAnimeSugerido);
                }
            }
        });
    }

    public void carregarCatalogo(RecyclerView recyclerView, ArrayList<DTOAnimes> animesArrayList) {
        db.collection("animes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        DTOAnimes dtoAnimes = new DTOAnimes();
                        dtoAnimes.setNomeAnime((String) document.getData().get("nomeAnime"));
                        dtoAnimes.setAno((String) document.getData().get("ano"));
                        dtoAnimes.setImagem((String) document.getData().get("imagem"));
                        dtoAnimes.setSinopse((String) document.getData().get("sinopse"));
                        dtoAnimes.setEstudio((String) document.getData().get("estudio"));
                        dtoAnimes.setGenero((String) document.getData().get("genero"));
                        dtoAnimes.setCodigo((String) document.getData().get("codigo"));
                        animesArrayList.add(dtoAnimes);
                    }
                    adapterCatalogo = new AdapterCatalogo(animesArrayList);
                    recyclerView.setAdapter(adapterCatalogo);
                }
            }
        });
    }

    public void continueAssistindo(RecyclerView recyclerView, ArrayList<DTOHistorico> historicoArrayList, TextView textView){
        db.collection("usuarios").document(auth.getCurrentUser().getEmail())
                .collection("historico")
                .orderBy("data", Query.Direction.DESCENDING).limit(1).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document: task.getResult()){
                                DTOHistorico historico = new DTOHistorico();
                                historico.setNomeAnime((String) document.getData().get("nomeAnime"));
                                historico.setTituloEp((String) document.getData().get("tituloEp"));
                                historico.setMinutoAssistido((long) document.getData().get("minutoAssistido"));
                                historico.setImagem((String) document.getData().get("imagem"));
                                historico.setLink((String) document.getData().get("link"));
                                historico.setDuracao((String) document.getData().get("duracao"));
                                historico.setSaga((String) document.getData().get("saga"));
                                historico.setCodigo((String) document.getData().get("codigo"));
                                historico.setSinopse((String) document.getData().get("sinopse"));
                                historico.setImagem((String) document.getData().get("imagem"));
                                historicoArrayList.add(historico);
                            }
                            if(historicoArrayList.size() <1){
                                textView.setVisibility(View.INVISIBLE);
                            }
                            else{
                                textView.setVisibility(View.VISIBLE);
                                adapterContinueAssistindo = new AdapterContinueAssistindo(historicoArrayList);
                                recyclerView.setAdapter(adapterContinueAssistindo);
                            }
                        }
                    }
                });
            }

    public void carregarAnimesPorGenero(RecyclerView recyclerView, ArrayList<DTOAnimes> animesArrayList, String genero, TextView textView){
        db.collection("animes").orderBy("genero").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(QueryDocumentSnapshot document : task.getResult()) {
                    DTOAnimes animes = new DTOAnimes();
                    animes.setNomeAnime((String) document.getData().get("nomeAnime"));
                    animes.setImagem((String) document.getData().get("imagem"));
                    animes.setCodigo((String) document.getData().get("codigo"));
                    animes.setGenero((String) document.getData().get("genero"));
                    animes.setAno((String) document.getData().get("ano"));
                    animes.setEstudio((String) document.getData().get("estudio"));
                    animes.setSinopse((String) document.getData().get("sinopse"));
                    String gen = animes.getGenero();
                    if(gen.contains(genero)){
                        animesArrayList.add(animes);
                        textView.setText(genero);
                        if(animesArrayList.size()<1){
                            Log.d("animes", "Vazio");
                        }
                    }
                    else{
                        Log.d("animes", "Animes de outros gêneros");
                    }
                }
                adapterCatalogo = new AdapterCatalogo(animesArrayList);
                recyclerView.setAdapter(adapterCatalogo);
            }
        });
    }

    public void buscarAnimes(RecyclerView recyclerView, ArrayList<DTOAnimes> animesArrayList, String animeBusca, TextView tvBuscar1, TextView tvBuscar2){
        db.collection("animes").orderBy("nomeInsensitive").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    animesArrayList.clear();
                    for(QueryDocumentSnapshot document: task.getResult()){
                        DTOAnimes animes = new DTOAnimes();
                        animes.setNomeAnime((String) document.getData().get("nomeAnime"));
                        animes.setNomeAnimeInsensitive((String) document.getData().get("nomeInsensitive"));
                        animes.setSinopse((String) document.getData().get("sinopse"));
                        animes.setEstudio((String) document.getData().get("estudio"));
                        animes.setAno((String) document.getData().get("ano"));
                        animes.setGenero((String) document.getData().get("genero"));
                        animes.setCodigo((String) document.getData().get("codigo"));
                        animes.setImagem((String) document.getData().get("imagem"));
                        String nomeAnime = animes.getNomeAnime();
                        String nomeAnimeInsensitive = animes.getNomeAnimeInsensitive();
                        if(nomeAnime.contains(animeBusca) || nomeAnimeInsensitive.contains(animeBusca)){
                            animesArrayList.add(animes);
                        }
                        else{
                            Log.d("animes", "animes incompatíveis "+nomeAnime);
                        }
                    }
                    adapterCatalogo = new AdapterCatalogo(animesArrayList);
                    int count = adapterCatalogo.getItemCount();
                    if(count <1){
                        tvBuscar1.setVisibility(View.VISIBLE);
                        tvBuscar2.setVisibility(View.VISIBLE);
                    }
                    else{
                        tvBuscar1.setVisibility(View.INVISIBLE);
                        tvBuscar2.setVisibility(View.INVISIBLE);
                    }
                    recyclerView.setAdapter(adapterCatalogo);
                }
            }
        });
    }
}

