package com.example.projetofirestore.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.projetofirestore.Adapters.AdapterEpisodios;
import com.example.projetofirestore.Classes.ConfFireBase;
import com.example.projetofirestore.Classes.RecyclerViewItemClickListener;
import com.example.projetofirestore.Models.DTOEpisodios;
import com.example.projetofirestore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class EscolherEpsAlterarActivity extends AppCompatActivity {
    FirebaseFirestore db = ConfFireBase.getFirebaseFirestore();
    RecyclerView rcEscolherEpsAlterar;
    ArrayList<DTOEpisodios> episodiosArrayList = new ArrayList<>();
    AdapterEpisodios adapterEpisodios;
    String cod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolher_eps_alterar);

        Bundle bundle = getIntent().getExtras();
        String nomeAnime = bundle.getString("nomeAnime");
        cod = bundle.getString("codigo");

        carregarEpisodios(cod);

        rcEscolherEpsAlterar = findViewById(R.id.rcEscolherEpsAlterar);

        rcEscolherEpsAlterar.setLayoutManager(new LinearLayoutManager(rcEscolherEpsAlterar.getContext(), RecyclerView.VERTICAL, false));

        rcEscolherEpsAlterar.addOnItemTouchListener(new RecyclerViewItemClickListener(EscolherEpsAlterarActivity.this, rcEscolherEpsAlterar, new RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(EscolherEpsAlterarActivity.this, AdicionarEpisodiosActivity.class);
                intent.putExtra("titulo", episodiosArrayList.get(position).getTitulo());
                intent.putExtra("sinopse", episodiosArrayList.get(position).getSinopse());
                intent.putExtra("link", episodiosArrayList.get(position).getLink());
                intent.putExtra("ano", episodiosArrayList.get(position).getAno());
                intent.putExtra("nomeAnime", nomeAnime);
                intent.putExtra("imagem", episodiosArrayList.get(position).getImagem());
                intent.putExtra("duracao", episodiosArrayList.get(position).getDuracao());
                intent.putExtra("saga", episodiosArrayList.get(position).getSaga());
                intent.putExtra("codigoEp", episodiosArrayList.get(position).getCodigo());
                intent.putExtra("codigo", cod);
                intent.putExtra("activity", EscolherEpsAlterarActivity.class.getName());
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

    private void carregarEpisodios(String cod) {
        db.collection("animes").document(cod).collection("episodios").orderBy("titulo")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                                dtoEpisodios.setCodigo((String) document.getData().get("codigo"));
                                dtoEpisodios.setFiller((boolean) document.getData().get("filler"));
                                episodiosArrayList.add(dtoEpisodios);
                            }
                            adapterEpisodios = new AdapterEpisodios(episodiosArrayList);
                            int count = adapterEpisodios.getItemCount();
                            if(count <1 ){
                                Toast.makeText(EscolherEpsAlterarActivity.this, "Sem episódios", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                rcEscolherEpsAlterar.setAdapter(adapterEpisodios);
                            }
                        } else {
                            Toast.makeText(EscolherEpsAlterarActivity.this, "Erro inesperado", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

    @Override
    protected void onStart() {
        super.onStart();
    }
}