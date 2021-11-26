package com.example.projetofirestore.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projetofirestore.Adapters.AdapterCatalogo;
import com.example.projetofirestore.Classes.ConfFireBase;
import com.example.projetofirestore.Classes.RecyclerViewItemClickListener;
import com.example.projetofirestore.Fragments.ContaFragment;
import com.example.projetofirestore.Models.DTOAnimes;
import com.example.projetofirestore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class EscolherAnimeAlterarActivity extends AppCompatActivity {
    FirebaseFirestore db = ConfFireBase.getFirebaseFirestore();
    ArrayList<DTOAnimes> animesArrayList = new ArrayList<>();
    RecyclerView rcEscolherAnimeAlterar;
    AdapterCatalogo adapterCatalogo;
    ImageView btVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolher_anime_alterar);


        rcEscolherAnimeAlterar = findViewById(R.id.rcEscolherAnimeAlterar);
        btVoltar = findViewById(R.id.btVoltarEscolherAnime);

        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EscolherAnimeAlterarActivity.this, ConsultaActivity.class);
                startActivity(intent);
            }
        });

        rcEscolherAnimeAlterar.setLayoutManager(new GridLayoutManager(rcEscolherAnimeAlterar.getContext(), 2, GridLayoutManager.VERTICAL, false));

        rcEscolherAnimeAlterar.addOnItemTouchListener(new RecyclerViewItemClickListener(EscolherAnimeAlterarActivity.this, rcEscolherAnimeAlterar, new RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(EscolherAnimeAlterarActivity.this, AlterarAnimesActivity.class);
                intent.putExtra("nomeAnime", animesArrayList.get(position).getNomeAnime());
                intent.putExtra("sinopse", animesArrayList.get(position).getSinopse());
                intent.putExtra("estudio", animesArrayList.get(position).getEstudio());
                intent.putExtra("genero", animesArrayList.get(position).getGenero());
                intent.putExtra("ano", animesArrayList.get(position).getAno());
                intent.putExtra("imagem", animesArrayList.get(position).getImagem());
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
    }

    private void carregarAnimes(){
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
                    rcEscolherAnimeAlterar.setAdapter(adapterCatalogo);
                } else{
                    Toast.makeText(EscolherAnimeAlterarActivity.this, "Deu merda", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        carregarAnimes();
    }
}