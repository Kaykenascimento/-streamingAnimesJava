package com.example.projetofirestore.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.projetofirestore.Adapters.AdapterEpisodios;
import com.example.projetofirestore.Classes.ConfFireBase;
import com.example.projetofirestore.Classes.RecyclerViewItemClickListener;
import com.example.projetofirestore.Models.DTOAnimes;
import com.example.projetofirestore.Models.DTOAnimesFavoritos;
import com.example.projetofirestore.Models.DTOEpisodios;
import com.example.projetofirestore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AnimeSelecionadoActivity extends AppCompatActivity {
    FirebaseFirestore db = ConfFireBase.getFirebaseFirestore();
    FirebaseAuth auth = ConfFireBase.getFirebaseAuth();
    TextView textViewNome, textViewSinopse, textViewEstudio, textViewAno, tvListaEpisodiosDet, tvGeneroDet;
    ImageView ivAnimeFavorito, imageViewDet, btVoltarDet;
    RecyclerView recyclerViewEpisodios;
    Boolean favorito = false;
    String nomeAnime, imagemAnime, codigo, ano, sinopse, genero, estudio, codigoAnime;
    ArrayList<DTOAnimesFavoritos> animesFavoritosArrayList = new ArrayList<>();
    ArrayList<DTOEpisodios> episodiosArrayList = new ArrayList<>();
    AdapterEpisodios adapterEpisodios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_selecionado);

        imageViewDet = findViewById(R.id.ivAnimesDet);
        textViewNome = findViewById(R.id.tvNomeAnimeDet);
        textViewSinopse = findViewById(R.id.tvSinopseDet);
        textViewEstudio = findViewById(R.id.tvEstudioDet);
        textViewAno = findViewById(R.id.tvAnoDet);
        tvGeneroDet = findViewById(R.id.tvGeneroDet);
        tvListaEpisodiosDet = findViewById(R.id.tvListaEpisodiosDet);
        ivAnimeFavorito = findViewById(R.id.ivAnimeFavorito);
        recyclerViewEpisodios = findViewById(R.id.rcListaEpisodios);
        btVoltarDet = findViewById(R.id.btVoltarDet);

        Bundle bundle = getIntent().getExtras();
        nomeAnime = bundle.getString("nomeAnime");
        codigoAnime = bundle.getString("codigo");
        genero = bundle.getString("genero");
        estudio = bundle.getString("estudio");
        ano = bundle.getString("ano");
        imagemAnime = bundle.getString("imagem");
        sinopse = bundle.getString("sinopse");

        carregarEpisodios();
        if(genero == null && estudio == null && ano == null && sinopse == null){
            carregarDetalhesAnime(codigoAnime);
        }
        else{
            Glide.with(this).load(imagemAnime).into(imageViewDet);
            textViewNome.setText(nomeAnime);
            textViewSinopse.setText(sinopse);
            tvGeneroDet.setText("Gênero(s): "+genero);
            textViewEstudio.setText("Estúdio: "+estudio);
            textViewAno.setText("Ano de lançamento: "+ano);
        }

        recyclerViewEpisodios.setLayoutManager(new GridLayoutManager(recyclerViewEpisodios.getContext(), 1, GridLayoutManager.VERTICAL, false));

        recyclerViewEpisodios.addOnItemTouchListener(new RecyclerViewItemClickListener(AnimeSelecionadoActivity.this, recyclerViewEpisodios, new RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(AnimeSelecionadoActivity.this, AssistirEpsActivity.class);
                intent.putExtra("nomeAnime", nomeAnime);
                intent.putExtra("titulo", episodiosArrayList.get(position).getTitulo());
                intent.putExtra("sinopse", episodiosArrayList.get(position).getSinopse());
                intent.putExtra("saga", episodiosArrayList.get(position).getSaga());
                intent.putExtra("ano", episodiosArrayList.get(position).getAno());
                intent.putExtra("imagem", episodiosArrayList.get(position).getImagem());
                intent.putExtra("duracao", episodiosArrayList.get(position).getDuracao());
                intent.putExtra("link", episodiosArrayList.get(position).getLink());
                intent.putExtra("duracao", episodiosArrayList.get(position).getDuracao());
                intent.putExtra("codigo", codigoAnime);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }));

        ivAnimeFavorito.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if (favorito == true){
                       deletarAnimeFavorito();
                       ivAnimeFavorito.setImageDrawable(getResources().getDrawable(R.drawable.ic_anime_favorito));
                   }else{
                       inserirAnimeFavorito();
                       ivAnimeFavorito.setImageDrawable(getResources().getDrawable(R.drawable.ic_anime_favorito_click));
                   }
               }
           });

        btVoltarDet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnimeSelecionadoActivity.this, ConsultaActivity.class);
                startActivity(intent);
            }
        });
    }

    private void checarAnimeFavorito(){
        db.collection("usuarios").document(auth.getCurrentUser().getEmail()).collection("favoritos").whereEqualTo("nomeAnime", nomeAnime).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    animesFavoritosArrayList.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        DTOAnimesFavoritos favoritos = new DTOAnimesFavoritos();
                        favoritos.setNomeAnime((String) document.getData().get("nomeAnime"));
                        favoritos.setDataAdicionado((String) document.getData().get("dataAdicionado"));
                        favoritos.setImagem((String) document.getData().get("imagem"));
                        favoritos.setEmailUsuario((String) document.getData().get("emailUsuario"));
                        favoritos.setCodigo((String) document.getData().get("codigo"));
                        favoritos.setEstudio((String) document.getData().get("estudio"));
                        favoritos.setGenero((String) document.getData().get("genero"));
                        favoritos.setAno((String) document.getData().get("ano"));
                        favoritos.setSinopse((String) document.getData().get("sinopse"));
                        favoritos.setCodigoAnime((String) document.getData().get("codigoAnime"));
                        animesFavoritosArrayList.add(favoritos);
                    }
                    String id = "3";
                    int position = -1;
                    for (int i = 0; i < animesFavoritosArrayList.size(); i++) {
                        if (animesFavoritosArrayList.get(i).getCodigo() == id) {
                            position = i;
                        }
                        codigo = animesFavoritosArrayList.get(i).getCodigo();
                    }
                    if(!animesFavoritosArrayList.isEmpty()){
                        favorito = true;
                        ivAnimeFavorito.setImageDrawable(getResources().getDrawable(R.drawable.ic_anime_favorito_click));
                    }
                    else{
                        favorito = false;
                    }
                }
                else{
                    Toast.makeText(AnimeSelecionadoActivity.this, "Erro Inesperado", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void inserirAnimeFavorito(){
        SimpleDateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String dataAdicionado = dataFormat.format(date);
        DocumentReference docRef =  db.collection("usuarios").document(auth.getCurrentUser().getEmail()).collection("favoritos").document();
        String cod = docRef.getId();
        DTOAnimesFavoritos favoritos = new DTOAnimesFavoritos(nomeAnime, imagemAnime, dataAdicionado, auth.getCurrentUser().getEmail(), sinopse, genero, estudio, ano, cod, codigoAnime);
        db.collection("usuarios").document(auth.getCurrentUser().getEmail()).collection("favoritos").document(cod).set(favoritos).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                checarAnimeFavorito();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AnimeSelecionadoActivity.this, "Falha ao inserir anime favorito", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deletarAnimeFavorito() {
        db.collection("usuarios").document(auth.getCurrentUser().getEmail()).collection("favoritos").document(codigo).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    favorito = false;
                    ivAnimeFavorito.setImageDrawable(getResources().getDrawable(R.drawable.ic_anime_favorito));
                    checarAnimeFavorito();
                }
                else{
                    Toast.makeText(AnimeSelecionadoActivity.this, "Não deletou", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void carregarDetalhesAnime(String codigoAnime){
        db.collection("animes").whereEqualTo("codigo", codigoAnime).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        DTOAnimes animes = new DTOAnimes();
                        animes.setNomeAnime((String) document.getData().get("nomeAnime"));
                        animes.setSinopse((String) document.getData().get("sinopse"));
                        animes.setEstudio((String) document.getData().get("estudio"));
                        animes.setAno((String) document.getData().get("ano"));
                        animes.setGenero((String) document.getData().get("genero"));
                        animes.setImagem((String) document.getData().get("imagem"));
                        animes.setCodigo((String) document.getData().get("codigo"));
                        textViewNome.setText(animes.getNomeAnime());
                        textViewSinopse.setText(animes.getSinopse());
                        textViewEstudio.setText("Estúdio: "+animes.getEstudio());
                        textViewAno.setText("Ano de lançamento: "+animes.getAno());
                        tvGeneroDet.setText("Gênero(s): "+animes.getGenero());
                        ano = animes.getAno();
                        estudio = animes.getEstudio();
                        genero = animes.getGenero();
                        imagemAnime = animes.getImagem();
                        sinopse = animes.getSinopse();
                        Glide.with(AnimeSelecionadoActivity.this).load(animes.getImagem()).into(imageViewDet);
                    }
                }
            }
        });
    }

    private void carregarEpisodios() {
        db.collection("animes").document(codigoAnime).collection("episodios").orderBy("titulo")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            episodiosArrayList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DTOEpisodios dtoEpisodios = new DTOEpisodios();
                                dtoEpisodios.setTitulo((String) document.getData().get("titulo"));
                                dtoEpisodios.setAno((String) document.getData().get("ano"));
                                dtoEpisodios.setImagem((String) document.getData().get("imagem"));
                                dtoEpisodios.setDuracao((String) document.getData().get("duracao"));
                                dtoEpisodios.setSinopse((String) document.getData().get("sinopse"));
                                dtoEpisodios.setLink((String) document.getData().get("link"));
                                dtoEpisodios.setSaga((String) document.getData().get("saga"));
                                dtoEpisodios.setDuracao((String) document.getData().get("duracao"));
                                episodiosArrayList.add(dtoEpisodios);
                            }
                            adapterEpisodios = new AdapterEpisodios(episodiosArrayList);
                            int count = adapterEpisodios.getItemCount();
                            if(count <1 ){
                                tvListaEpisodiosDet.setText("Sem Episódios - Em Breve");
                            }
                            else{
                                recyclerViewEpisodios.setAdapter(adapterEpisodios);
                            }
                        } else {
                            Toast.makeText(AnimeSelecionadoActivity.this, "Erro inesperado", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser != null){
            checarAnimeFavorito();
        }
        else{
            ivAnimeFavorito.setVisibility(View.INVISIBLE);
            return;
        }
    }
}
