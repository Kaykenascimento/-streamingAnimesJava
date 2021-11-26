package com.example.projetofirestore.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetofirestore.Adapters.AdapterNextEpisode;
import com.example.projetofirestore.Classes.ConfFireBase;
import com.example.projetofirestore.Classes.RecyclerViewItemClickListener;
import com.example.projetofirestore.Models.DTOAnimes;
import com.example.projetofirestore.Models.DTOEpisodios;
import com.example.projetofirestore.Models.DTOHistorico;
import com.example.projetofirestore.R;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.RendererCapabilities;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.mediacodec.MediaCodecAdapter;
import com.google.android.exoplayer2.mediacodec.MediaCodecInfo;
import com.google.android.exoplayer2.mediacodec.MediaCodecSelector;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.MediaCodecVideoRenderer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AssistirEpsActivity extends AppCompatActivity {
    PlayerView playerViewEpisodio;
    TextView tvNomeAnime, tvTituloAnime, tvSinopseAnime, tvSagaAnime, tvListaEpisodios, tvProximoEp, tvProxTemp, tvNomeAnimeExo;
    RecyclerView rcProxEpisodio;
    FirebaseFirestore db = ConfFireBase.getFirebaseFirestore();
    FirebaseAuth auth = ConfFireBase.getFirebaseAuth();
    ArrayList<DTOEpisodios> episodiosArrayList = new ArrayList<>();
    ArrayList<DTOHistorico> historicoArrayList = new ArrayList<>();
    AdapterNextEpisode adapterNextEpisode;
    String nomeAnime, sinopse, tituloEp, link, saga, imagemAnime, codigoAnime, duracao;
    String nomeProxTemp, sinopseProxTemp, anoProxTemp, estudioProxTemp, imagemProxTemp, generoProxTemp, codigoProxTemp;
    boolean fullscreen = false;
    boolean subtitle = false;
    Boolean user = false;
    boolean proxTemp = false;
    long minuto, minAssistido;
    ImageView btFullscreen, btSubtitle, btVoltar;
    SimpleExoPlayer simpleExoPlayer;
    MediaSource mediaSource;
    AlertDialog.Builder msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistir_eps);

        playerViewEpisodio = findViewById(R.id.playerView);
        tvNomeAnime = findViewById(R.id.tvNomeAnimePlayer);
        tvTituloAnime = findViewById(R.id.tvTituloEpPlayer);
        tvSinopseAnime = findViewById(R.id.tvSinopseEpPlayer);
        tvListaEpisodios = findViewById(R.id.tvListaEpisodios);
        tvSagaAnime = findViewById(R.id.tvSagaAnime);
        tvProximoEp = findViewById(R.id.tvProximoEp);
        tvProxTemp = findViewById(R.id.tvProxTemp);
        rcProxEpisodio = findViewById(R.id.rcProxEpisodio);
        btFullscreen = playerViewEpisodio.findViewById(R.id.bt_fullscreen);
        btSubtitle = playerViewEpisodio.findViewById(R.id.btSubtitle);
        btVoltar = playerViewEpisodio.findViewById(R.id.btVoltar);
        tvNomeAnimeExo = playerViewEpisodio.findViewById(R.id.tvNomeAnimeExo);
        
        msg = new AlertDialog.Builder(this).setMessage("Deseja retomar de onde parou anteriormente?");

        if (saga == "") {
            tvSagaAnime.setVisibility(View.INVISIBLE);
        } else {
            tvSagaAnime.setText(saga);
        }

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        tvNomeAnimeExo.setVisibility(View.INVISIBLE);

        Bundle bundle = getIntent().getExtras();
        nomeAnime = bundle.getString("nomeAnime");
        imagemAnime = bundle.getString("imagem");
        tituloEp = bundle.getString("titulo");
        link = bundle.getString("link");
        saga = bundle.getString("saga");
        sinopse = bundle.getString("sinopse");
        minuto = bundle.getLong("minutoAssistido");
        codigoAnime = bundle.getString("codigo");
        duracao = bundle.getString("duracao");

        tvNomeAnime.setText(nomeAnime);
        tvTituloAnime.setText(tituloEp);
        tvSinopseAnime.setText(sinopse);

        Uri videoUri = Uri.parse(link);
        DefaultTrackSelector trackSelector = new DefaultTrackSelector(this);

        btSubtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(subtitle){
                        trackSelector.setParameters(trackSelector.buildUponParameters().setRendererDisabled(2, false).build());
                        subtitle = false;
                        btSubtitle.setImageDrawable(getResources().getDrawable(R.drawable.ic_subtitles_on));
                }
                else{
                    trackSelector.setParameters(trackSelector.buildUponParameters().setRendererDisabled(2, true).build());
                    subtitle = true;
                    btSubtitle.setImageDrawable(getResources().getDrawable(R.drawable.ic_subtitles_off));
                }
            }
        });

        trackSelector.setParameters(trackSelector.buildUponParameters().setMaxVideoSizeSd().setPreferredTextLanguage("pt-br"));
        DataSource.Factory dataSourceFactory = new DefaultHttpDataSource.Factory();
        mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(videoUri));
        simpleExoPlayer = new SimpleExoPlayer.Builder(this).setTrackSelector(trackSelector).setSeekBackIncrementMs(10000).setSeekForwardIncrementMs(10000).build();
        playerViewEpisodio.setPlayer(simpleExoPlayer);
        simpleExoPlayer.setMediaSource(mediaSource);
        simpleExoPlayer.prepare();
        simpleExoPlayer.play();

        tvProxTemp.setVisibility(View.INVISIBLE);

        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AssistirEpsActivity.this, AnimeSelecionadoActivity.class);
                intent.putExtra("codigo", codigoAnime);
                intent.putExtra("nomeAnime", nomeAnime);
                intent.putExtra("genero", (String) null);
                intent.putExtra("estudio", (String) null);
                intent.putExtra("ano", (String) null);
                intent.putExtra("sinopse", (String) null);
                intent.putExtra("imagem", (String) null);
                startActivity(intent);
            }
        });

        btFullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fullscreen) {
                    btFullscreen.setImageDrawable(ContextCompat.getDrawable(AssistirEpsActivity.this, R.drawable.ic_fullscreen));
                    AssistirEpsActivity.this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().show();
                    }
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) playerViewEpisodio.getLayoutParams();
                    params.width = params.MATCH_PARENT;
                    params.height = (int) (230 * getApplicationContext().getResources().getDisplayMetrics().density);
                    playerViewEpisodio.setLayoutParams(params);
                    btVoltar.setVisibility(View.VISIBLE);
                    tvNomeAnimeExo.setVisibility(View.INVISIBLE);
                    fullscreen = false;

                } else {
                    btFullscreen.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen_exit));
                    AssistirEpsActivity.this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE);
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().show();
                    }
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) playerViewEpisodio.getLayoutParams();
                    params.width = params.MATCH_PARENT;
                    params.height = params.MATCH_PARENT;
                    playerViewEpisodio.setLayoutParams(params);
                    btVoltar.setVisibility(View.INVISIBLE);
                    tvNomeAnimeExo.setVisibility(View.VISIBLE);
                    tvNomeAnimeExo.setText(tituloEp);
                    fullscreen = true;
                }
            }
        });

        tvListaEpisodios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AssistirEpsActivity.this, EpisodiosActivity.class);
                intent.putExtra("nomeAnime", nomeAnime);
                intent.putExtra("saga", saga);
                intent.putExtra("codigo", codigoAnime);
                intent.putExtra("duracao", duracao);
                startActivity(intent);
            }
        });

        tvProxTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AssistirEpsActivity.this, AnimeSelecionadoActivity.class);
                intent.putExtra("nomeAnime", nomeProxTemp);
                intent.putExtra("sinopse", sinopseProxTemp);
                intent.putExtra("estudio", estudioProxTemp);
                intent.putExtra("ano", anoProxTemp);
                intent.putExtra("genero", generoProxTemp);
                intent.putExtra("imagem", imagemProxTemp);
                intent.putExtra("codigo", codigoProxTemp);
                startActivity(intent);
            }
        });

        rcProxEpisodio.setLayoutManager(new GridLayoutManager(rcProxEpisodio.getContext(), 1, GridLayoutManager.VERTICAL, false));

        rcProxEpisodio.addOnItemTouchListener(new RecyclerViewItemClickListener(AssistirEpsActivity.this, rcProxEpisodio, new RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(AssistirEpsActivity.this, AssistirEpsActivity.class);
                intent.putExtra("titulo", episodiosArrayList.get(position).getTitulo());
                intent.putExtra("sinopse", episodiosArrayList.get(position).getSinopse());
                intent.putExtra("link", episodiosArrayList.get(position).getLink());
                intent.putExtra("ano", episodiosArrayList.get(position).getAno());
                intent.putExtra("codigo", codigoAnime);
                intent.putExtra("nomeAnime", nomeAnime);
                intent.putExtra("imagem", imagemAnime);
                intent.putExtra("duracao", duracao);
                if (episodiosArrayList.get(position).getSaga() == "" && saga == "") {
                    startActivity(intent);
                } else if (episodiosArrayList.get(position).getSaga() != "") {
                    intent.putExtra("saga", episodiosArrayList.get(position).getSaga());
                    startActivity(intent);
                } else if (saga != "") {
                    intent.putExtra("saga", saga);
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
    }

    private void carregarProximoEp(String codigoAnime) {
        db.collection("animes").document(codigoAnime).collection("episodios").orderBy("titulo").startAfter(tvTituloAnime.getText().toString()).limit(1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DTOEpisodios episodios = new DTOEpisodios();
                                episodios.setTitulo((String) document.getData().get("titulo"));
                                episodios.setAno((String) document.getData().get("ano"));
                                episodios.setImagem((String) document.getData().get("imagem"));
                                episodios.setSinopse((String) document.getData().get("sinopse"));
                                episodios.setDuracao((String) document.getData().get("duracao"));
                                episodios.setLink((String) document.getData().get("link"));
                                episodios.setSaga((String) document.getData().get("saga"));
                                episodios.setCodigo((String) document.getData().get("codigo"));
                                episodiosArrayList.add(episodios);
                            }
                            adapterNextEpisode = new AdapterNextEpisode(episodiosArrayList);
                            int count = adapterNextEpisode.getItemCount();
                            if (count < 1) {
                                tvProximoEp.setVisibility(View.INVISIBLE);
                                if(nomeProxTemp.startsWith(nomeAnime)){
                                    tvProxTemp.setVisibility(View.VISIBLE);
                                }
                                else if(nomeProxTemp != nomeAnime){
                                    tvProxTemp.setVisibility(View.INVISIBLE);
                                }
                                else{
                                    tvProxTemp.setVisibility(View.INVISIBLE);
                                }
                            }
                            rcProxEpisodio.setAdapter(adapterNextEpisode);
                        } else {
                            Toast.makeText(AssistirEpsActivity.this, "Erro inesperado", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

    private void checarProxTemp() {
        db.collection("animes").orderBy("nomeAnime").startAt(nomeAnime).startAfter(nomeAnime).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    if (task.isSuccessful()) {
                        DTOAnimes animes = new DTOAnimes();
                        animes.setNomeAnime((String) document.getData().get("nomeAnime"));
                        animes.setEstudio((String) document.getData().get("estudio"));
                        animes.setAno((String) document.getData().get("ano"));
                        animes.setSinopse((String) document.getData().get("sinopse"));
                        animes.setImagem((String) document.getData().get("imagem"));
                        animes.setGenero((String) document.getData().get("genero"));
                        animes.setCodigo((String) document.getData().get("codigo"));
                        nomeProxTemp = animes.getNomeAnime();
                        sinopseProxTemp = animes.getSinopse();
                        anoProxTemp = animes.getAno();
                        generoProxTemp = animes.getGenero();
                        imagemProxTemp = animes.getImagem();
                        estudioProxTemp = animes.getEstudio();
                        codigoProxTemp = animes.getCodigo();
                        if(!episodiosArrayList.isEmpty()){
                            proxTemp = true;
                        }
                        else{
                            proxTemp = false;;
                        }
                    } else {
                        Toast.makeText(AssistirEpsActivity.this, "Deu erro", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void inserirNoHistorico(){
        Date data = new Date();
        Timestamp timestamp = new Timestamp(data);
        DTOHistorico historico = new DTOHistorico(nomeAnime, tituloEp, sinopse, saga, link, imagemAnime, simpleExoPlayer.getCurrentPosition(), codigoAnime, duracao, timestamp);
        db.collection("usuarios").document(auth.getCurrentUser().getEmail()).collection("historico").document(nomeAnime+" - "+tituloEp).set(historico).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("Insert", "Inserido");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AssistirEpsActivity.this, "Erro ao registrar no histórico", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checarHistorico(){
        db.collection("usuarios").document(auth.getCurrentUser().getEmail()).collection("historico").whereEqualTo("nomeAnime", nomeAnime).whereEqualTo("tituloEp", tituloEp).limit(1).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document: task.getResult()){
                                DTOHistorico historico = new DTOHistorico();
                                historico.setNomeAnime((String) document.getData().get("nomeAnime"));
                                historico.setMinutoAssistido((long) document.getData().get("minutoAssistido"));
                                historicoArrayList.add(historico);
                                minAssistido = historico.getMinutoAssistido();
                            }
                            if(minAssistido<1){
                                simpleExoPlayer.play();
                            }
                            else{
                                msg.setPositiveButton("Não", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        simpleExoPlayer.prepare();
                                    }
                                }).setNegativeButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        simpleExoPlayer.setMediaSource(mediaSource, minAssistido);
                                        simpleExoPlayer.prepare();
                                    }
                                }).show();
                            }
                        }
                    }
                });
            }

    @Override
    protected void onPause() {
        super.onPause();
        simpleExoPlayer.setPlayWhenReady(false);
        simpleExoPlayer.getPlaybackState();
        if(user == true){
            inserirNoHistorico();
            user = true;
        }
        else{
            user = false;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.getPlaybackState();
        if(user == true){
            inserirNoHistorico();
            user = true;
        }
        else{
            user = false;
        }
    }

    @Override
    protected void onStart() {
        FirebaseUser currentUser = auth.getCurrentUser();
        super.onStart();
        checarProxTemp();
        carregarProximoEp(codigoAnime);
        if(currentUser != null){
            user = true;
            checarHistorico();
        }
        else{
            simpleExoPlayer.setPlayWhenReady(true);
            simpleExoPlayer.play();
            user = false;
        }
    }
}










