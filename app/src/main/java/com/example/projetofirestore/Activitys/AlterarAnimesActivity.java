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

import com.example.projetofirestore.Classes.ConfFireBase;
import com.example.projetofirestore.Models.DTOAnimes;
import com.example.projetofirestore.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class AlterarAnimesActivity extends AppCompatActivity {
    FirebaseFirestore db = ConfFireBase.getFirebaseFirestore();
    ImageView ivAnimeAlterar, btVoltar;
    EditText etNomeAnime, etSinopse, etEstudio, etAno, etGenero, etImagem;
    Button buttonAlterarAnime, buttonAdicionarEpisodios, buttonAlterarEpisodios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_animes);

        etNomeAnime = findViewById(R.id.etNomeAnime);
        etSinopse = findViewById(R.id.etSinopseAlterar);
        etEstudio = findViewById(R.id.etEstudioAlterar);
        etAno = findViewById(R.id.etAnoAlterar);
        etImagem = findViewById(R.id.etImagemAlterar);
        etGenero = findViewById(R.id.etGeneroAlterar);
        buttonAlterarAnime = findViewById(R.id.btAlterarAnimes);
        buttonAdicionarEpisodios = findViewById(R.id.btInserirEps);
        buttonAlterarEpisodios = findViewById(R.id.btAlterarEpisodios);
        btVoltar = findViewById(R.id.btVoltarAlterarAnime);
        ivAnimeAlterar = findViewById(R.id.ivImagemAlterar);

        Bundle bundle = getIntent().getExtras();
        String nomeAnime = bundle.getString("nomeAnime");
        String sinopse = bundle.getString("sinopse");
        String estudio = bundle.getString("estudio");
        String ano = bundle.getString("ano");
        String imagem = bundle.getString("imagem");
        String genero = bundle.getString("genero");
        String cod = bundle.getString("codigo");

        etNomeAnime.setText(nomeAnime);
        etSinopse.setText(sinopse);
        etEstudio.setText(estudio);
        etAno.setText(ano);
        etImagem.setText(imagem);
        etGenero.setText(genero);
        Picasso.get().load(imagem).into(ivAnimeAlterar);

        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlterarAnimesActivity.this, EscolherAnimeAlterarActivity.class);
                intent.putExtra("codigo", cod);
                startActivity(intent);
            }
        });

        etImagem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String imagem = s.toString();
                Uri uri = Uri.parse(imagem);
                Picasso.get().load(uri).into(ivAnimeAlterar);
            }
        });

        buttonAlterarAnime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = etNomeAnime.getText().toString();
                String sinopseAd = etSinopse.getText().toString();
                String anoAd = etAno.getText().toString();
                String estudioAd = etEstudio.getText().toString();
                String generoAd = etGenero.getText().toString();
                String imagemAd = etImagem.getText().toString();
                String codigo = cod;
                DTOAnimes animes = new DTOAnimes(nomeAnime, imagem, ano, sinopse, estudio, genero, cod, nome.toLowerCase());
                db.collection("animes").document(codigo).update("nomeAnime", nome, "imagem", imagemAd, "ano", anoAd, "sinopse", sinopseAd, "estudio", estudioAd, "genero", generoAd)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(AlterarAnimesActivity.this, "Inalterado", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AlterarAnimesActivity.this, EscolherAnimeAlterarActivity.class);
                                intent.putExtra("codigo", cod);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AlterarAnimesActivity.this, "Deu merda", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        buttonAdicionarEpisodios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlterarAnimesActivity.this, AdicionarEpisodiosActivity.class);
                intent.putExtra("nomeAnime", nomeAnime);
                intent.putExtra("imagem", (String) null);
                intent.putExtra("codigo", cod);
                intent.putExtra("activity", AlterarAnimesActivity.class.getName());
                startActivity(intent);
            }
        });

        buttonAlterarEpisodios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlterarAnimesActivity.this, EscolherEpsAlterarActivity.class);
                intent.putExtra("nomeAnime", nomeAnime);
                intent.putExtra("codigo", cod);
                intent.putExtra("activity", AlterarAnimesActivity.class.getSimpleName());
                startActivity(intent);
            }
        });
    }
}