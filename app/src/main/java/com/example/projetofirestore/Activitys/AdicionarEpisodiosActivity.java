package com.example.projetofirestore.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.projetofirestore.Classes.ConfFireBase;
import com.example.projetofirestore.Models.DTOEpisodios;
import com.example.projetofirestore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class AdicionarEpisodiosActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    FirebaseFirestore db = ConfFireBase.getFirebaseFirestore();
    EditText etTituloEp, etSinopse, etSaga, etAno,  etImagem, etLink, etDuracao;
    TextView tvNomeAnime;
    ImageView ivEpisodio;
    Spinner spinnerFiller;
    Button buttonAdicionar, buttonAlterarEp;
    String nomeAnime, sinopse, saga, ano, imagem, link, duracao, tituloEp, activity, cod, codigoEp;
    Boolean filler;
    Boolean fillerAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_episodios);

        etTituloEp = findViewById(R.id.etTituloEpAd);
        etSinopse = findViewById(R.id.etSinopseEpAd);
        etSaga = findViewById(R.id.etSagaEpAd);
        etAno = findViewById(R.id.etAnoEpAd);
        etImagem = findViewById(R.id.etImagemEpAd);
        etLink = findViewById(R.id.etLinkEpAd);
        etDuracao = findViewById(R.id.etDuracaoEpAd);
        etSaga = findViewById(R.id.etSagaEpAd);
        ivEpisodio = findViewById(R.id.ivEpisodioAdicionar);
        spinnerFiller = findViewById(R.id.spinnerFiller);
        buttonAdicionar = findViewById(R.id.buttonAdicionarEp);
        buttonAlterarEp = findViewById(R.id.btAlterar);
        tvNomeAnime = findViewById(R.id.tvNomeAnimeAdEp);

        buttonAlterarEp.setVisibility(View.INVISIBLE);

        Bundle bundle = getIntent().getExtras();
        nomeAnime = bundle.getString("nomeAnime");
        tituloEp = bundle.getString("titulo");
        sinopse = bundle.getString("sinopse");
        saga = bundle.getString("saga");
        ano = bundle.getString("ano");
        imagem = bundle.getString("imagem");
        link = bundle.getString("link");
        duracao = bundle.getString("duracao");
        cod = bundle.getString("codigo");
        codigoEp = bundle.getString("codigoEp");

        activity = getIntent().getStringExtra("activity");
        tvNomeAnime.setText(nomeAnime);

        if(activity.equalsIgnoreCase(EscolherEpsAlterarActivity.class.getName())){
            Picasso.get().load(imagem).into(ivEpisodio);
            etImagem.setText(imagem);
            etTituloEp.setText(tituloEp);
            etSaga.setText(saga);
            etAno.setText(ano);
            etLink.setText(link);
            etImagem.setText(imagem);
            etDuracao.setText(duracao);
            etSinopse.setText(sinopse);
            buttonAdicionar.setVisibility(View.INVISIBLE);
            buttonAlterarEp.setVisibility(View.VISIBLE);
        }
        else if(activity.equalsIgnoreCase(AdicionarEpisodiosActivity.class.getSimpleName())){
            nomeAnime = bundle.getString("nomeAnime");
            etSaga.setText(saga);
            etImagem.setText(imagem);
            etDuracao.setText(duracao);
            etAno.setText(ano);
            Picasso.get().load(imagem).into(ivEpisodio);
        }
        else if(activity.equalsIgnoreCase(AlterarAnimesActivity.class.getSimpleName())){
            buttonAlterarEp.setVisibility(View.INVISIBLE);
            buttonAdicionar.setVisibility(View.VISIBLE);
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.filler, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFiller.setAdapter(adapter);
        spinnerFiller.setOnItemSelectedListener(this);

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
                Glide.with(AdicionarEpisodiosActivity.this).load(uri).into(ivEpisodio);
            }
        });

        buttonAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tituloAd = etTituloEp.getText().toString();
                String sinopseAd = etSinopse.getText().toString();
                String imagemAd = etImagem.getText().toString();
                String duracaoAd = etDuracao.getText().toString();
                String anoAd = etAno.getText().toString();
                String linkAd = etLink.getText().toString();
                String sagaAd = etSaga.getText().toString();
                fillerAd = filler;
                DocumentReference epRef = db.collection("animes").document(cod).collection("episodios").document();
                String codigo = epRef.getId();
                DTOEpisodios episodios = new DTOEpisodios(tituloAd, sinopseAd, imagemAd, duracaoAd, anoAd, linkAd, sagaAd, fillerAd, codigo);
                db.collection("animes").document(cod).collection("episodios").document(codigo).set(episodios).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Intent intent = new Intent(AdicionarEpisodiosActivity.this, AdicionarEpisodiosActivity.class);
                        intent.putExtra("nomeAnime", nomeAnime);
                        intent.putExtra("ano", anoAd);
                        intent.putExtra("sinopse", sinopseAd);
                        intent.putExtra("duracao", duracaoAd);
                        intent.putExtra("imagem", imagemAd);
                        intent.putExtra("saga", sagaAd);
                        intent.putExtra("titulo", tituloAd);
                        intent.putExtra("codigo", cod);
                        intent.putExtra("activity", AdicionarEpisodiosActivity.class.getSimpleName());
                        startActivity(intent);
                        Toast.makeText(AdicionarEpisodiosActivity.this, tituloAd, Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdicionarEpisodiosActivity.this, "Deu merda", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        buttonAlterarEp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tituloAd = etTituloEp.getText().toString();
                String sinopseAd = etSinopse.getText().toString();
                String imagemAd = etImagem.getText().toString();
                String duracaoAd = etDuracao.getText().toString();
                String anoAd = etAno.getText().toString();
                String linkAd = etLink.getText().toString();
                String sagaAd = etSaga.getText().toString();
                fillerAd = filler;
                db.collection("animes").document(cod).collection("episodios").document(codigoEp).update("titulo", tituloAd,
                        "sinopse", sinopseAd, "imagem", imagemAd, "duracao", duracaoAd, "ano", anoAd, "link", linkAd, "saga", sagaAd, "filler", fillerAd).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(AdicionarEpisodiosActivity.this, "Alterado", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AdicionarEpisodiosActivity.this, EscolherEpsAlterarActivity.class);
                        intent.putExtra("nomeAnime", nomeAnime);
                        intent.putExtra("codigo", cod);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdicionarEpisodiosActivity.this, "Deu merda", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        filler = Boolean.valueOf(parent.getItemAtPosition(position).toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}