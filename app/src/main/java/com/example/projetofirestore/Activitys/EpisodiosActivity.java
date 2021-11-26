package com.example.projetofirestore.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projetofirestore.Adapters.AdapterEpisodios;
import com.example.projetofirestore.Classes.RecyclerViewItemClickListener;
import com.example.projetofirestore.Models.DTOEpisodios;
import com.example.projetofirestore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class EpisodiosActivity extends AppCompatActivity {
    RecyclerView recyclerViewEpisodios;
    AdapterEpisodios adapterEpisodios;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<DTOEpisodios> arrayListEpisodios = new ArrayList<>();
    String nomeAnime, imagem, codigoAnime;
    ImageView btVoltarEps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodios);

        recyclerViewEpisodios = findViewById(R.id.rcEpisodios);
        btVoltarEps = findViewById(R.id.btVoltarEps);

        recyclerViewEpisodios.setLayoutManager(new GridLayoutManager(recyclerViewEpisodios.getContext(), 1, GridLayoutManager.VERTICAL, false));

        Bundle bundle = getIntent().getExtras();
        nomeAnime = bundle.getString("nomeAnime");
        codigoAnime = bundle.getString("codigo");


        btVoltarEps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recyclerViewEpisodios.addOnItemTouchListener(new RecyclerViewItemClickListener(EpisodiosActivity.this, recyclerViewEpisodios, new RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(EpisodiosActivity.this, AssistirEpsActivity.class);
                intent.putExtra("titulo", arrayListEpisodios.get(position).getTitulo());
                intent.putExtra("sinopse", arrayListEpisodios.get(position).getSinopse());
                intent.putExtra("link", arrayListEpisodios.get(position).getLink());
                intent.putExtra("ano", arrayListEpisodios.get(position).getAno());
                intent.putExtra("nomeAnime", nomeAnime);
                intent.putExtra("imagem", arrayListEpisodios.get(position).getImagem());
                intent.putExtra("duracao", arrayListEpisodios.get(position).getDuracao());
                intent.putExtra("codigo", codigoAnime);
                if(arrayListEpisodios.get(position).getSaga() == null){
                    startActivity(intent);
                }
                else{
                    intent.putExtra("saga", arrayListEpisodios.get(position).getSaga());
                    startActivity(intent);
                }
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }));
        carregarEpisodios(codigoAnime);


    }
    private void carregarEpisodios(String codigoAnime) {
        db.collection("animes").document(codigoAnime).collection("episodios").orderBy("titulo")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            arrayListEpisodios.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DTOEpisodios dtoEpisodios = new DTOEpisodios();
                                dtoEpisodios.setTitulo((String) document.getData().get("titulo"));
                                dtoEpisodios.setAno((String) document.getData().get("ano"));
                                dtoEpisodios.setImagem((String) document.getData().get("imagem"));
                                dtoEpisodios.setDuracao((String) document.getData().get("duracao"));
                                dtoEpisodios.setSinopse((String) document.getData().get("sinopse"));
                                dtoEpisodios.setLink((String) document.getData().get("link"));
                                dtoEpisodios.setSaga((String) document.getData().get("saga"));
                                dtoEpisodios.setCodigo((String) document.getData().get("codigo"));
                                dtoEpisodios.setDuracao((String) document.getData().get("duracao"));
                                arrayListEpisodios.add(dtoEpisodios);
                            }
                            adapterEpisodios = new AdapterEpisodios(arrayListEpisodios);
                            int count = adapterEpisodios.getItemCount();
                                if(count <1 ){
                                    Toast.makeText(EpisodiosActivity.this, "Sem episódios", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    recyclerViewEpisodios.setAdapter(adapterEpisodios);
                                }
                            } else {
                                Toast.makeText(EpisodiosActivity.this, "Erro inesperado", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
}