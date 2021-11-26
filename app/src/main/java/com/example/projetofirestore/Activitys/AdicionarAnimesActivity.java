package com.example.projetofirestore.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.projetofirestore.Classes.ConfFireBase;
import com.example.projetofirestore.Models.DTOAnimes;
import com.example.projetofirestore.R;
import com.google.android.exoplayer2.C;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdicionarAnimesActivity extends AppCompatActivity {
    FirebaseFirestore db = ConfFireBase.getFirebaseFirestore();
    ImageView ivAdicionarAnime;
    EditText etNomeAnime, etSinopse, etEstudio, etGenero, etAno, etImagem;
    Button buttonAdicionarAnime, buttonAdicionarEpisodios, buttonLimpar;
    String nomeAnime, codigoAnime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_animes);

        etNomeAnime = findViewById(R.id.etNomeAnimeAd);
        etSinopse = findViewById(R.id.etSinopseAnimeAd);
        etEstudio = findViewById(R.id.etEstudioAd);
        etGenero = findViewById(R.id.etGenerosAd);
        etAno = findViewById(R.id.etAnoAd);
        etImagem = findViewById(R.id.etImagemAnimeAd);
        ivAdicionarAnime = findViewById(R.id.ivAdicionarAnime);
        buttonAdicionarAnime = findViewById(R.id.btAdicionarAnime);
        buttonAdicionarEpisodios = findViewById(R.id.btAdicionarEps);
        buttonLimpar = findViewById(R.id.btLimpar);

        buttonLimpar.setVisibility(View.INVISIBLE);
        buttonAdicionarAnime.setEnabled(false);
        buttonAdicionarEpisodios.setEnabled(false);

        etImagem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Uri uri = Uri.parse(s.toString());
                Glide.with(AdicionarAnimesActivity.this).load(uri).into(ivAdicionarAnime);
            }
        });

        etNomeAnime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length() <1){
                    buttonAdicionarAnime.setEnabled(false);
                    buttonAdicionarEpisodios.setEnabled(false);
                }
                else{
                    buttonAdicionarAnime.setEnabled(true);
                    buttonAdicionarEpisodios.setEnabled(true);
                }
            }
        });

        etNomeAnime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                nomeAnime = s.toString();
            }
        });

        buttonAdicionarAnime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference animeRef = db.collection("animes").document().collection("episodios").document();
                String codigo = animeRef.getId();
                DTOAnimes animes = new DTOAnimes(etNomeAnime.getText().toString(), etImagem.getText().toString(), etAno.getText().toString(), etSinopse.getText().toString(), etEstudio.getText().toString(), etGenero.getText().toString(), codigo, etNomeAnime.getText().toString().toLowerCase());
                db.collection("animes").document(codigo).set(animes).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(AdicionarAnimesActivity.this, "Inserido", Toast.LENGTH_SHORT).show();
                        Toast.makeText(AdicionarAnimesActivity.this, nomeAnime, Toast.LENGTH_SHORT).show();
                        buttonLimpar.setVisibility(View.VISIBLE);
                        carregarAnime();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdicionarAnimesActivity.this, "Deu merda", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        buttonAdicionarEpisodios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdicionarAnimesActivity.this, AdicionarEpisodiosActivity.class);
                intent.putExtra("nomeAnime", nomeAnime);
                intent.putExtra("codigo", codigoAnime);
                intent.putExtra("activity", AdicionarEpisodiosActivity.class.getSimpleName());
                startActivity(intent);
            }
        });

        buttonLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etNomeAnime.setText("");
                etSinopse.setText("");
                etEstudio.setText("");
                etAno.setText("");
                etGenero.setText("");
                etImagem.setText("");
            }
        });
    }

    private void carregarAnime(){
        db.collection("animes").whereEqualTo("nomeAnime", nomeAnime).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        DTOAnimes animes = new DTOAnimes();
                        animes.setNomeAnime((String) document.getData().get("nomeAnime"));
                        animes.setCodigo((String) document.getData().get("codigo"));
                        codigoAnime = animes.getCodigo();
                    }
                }
            }
        });
    }
}