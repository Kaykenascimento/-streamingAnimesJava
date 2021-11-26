package com.example.projetofirestore.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projetofirestore.Activitys.AnimeSelecionadoActivity;
import com.example.projetofirestore.Adapters.AdapterAnimesFavoritos;
import com.example.projetofirestore.Classes.ConfFireBase;
import com.example.projetofirestore.Classes.RecyclerViewItemClickListener;
import com.example.projetofirestore.Models.DTOAnimesFavoritos;
import com.example.projetofirestore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FavoritosListaFragment extends Fragment {
    FirebaseFirestore db = ConfFireBase.getFirebaseFirestore();
    FirebaseAuth auth = ConfFireBase.getFirebaseAuth();
    AdapterAnimesFavoritos adapterAnimesFavoritos;
    ArrayList<DTOAnimesFavoritos> animesFavoritosArrayList = new ArrayList<>();
    ImageView imageViewFav;
    TextView textViewFav;
    RecyclerView rcFavoritos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favoritos_lista, container, false);

        imageViewFav = view.findViewById(R.id.imageViewFavoritos);
        textViewFav = view.findViewById(R.id.textViewFav);
        rcFavoritos = view.findViewById(R.id.rcFavoritos);

        imageViewFav.setVisibility(View.INVISIBLE);
        textViewFav.setVisibility(View.INVISIBLE);
        rcFavoritos.setVisibility(View.INVISIBLE);

        rcFavoritos.setLayoutManager(new GridLayoutManager(rcFavoritos.getContext(), 1, GridLayoutManager.VERTICAL, false));

        rcFavoritos.addOnItemTouchListener(new RecyclerViewItemClickListener(getActivity(), rcFavoritos, new RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), AnimeSelecionadoActivity.class);
                intent.putExtra("nomeAnime", animesFavoritosArrayList.get(position).getNomeAnime());
                intent.putExtra("genero", animesFavoritosArrayList.get(position).getGenero());
                intent.putExtra("estudio", animesFavoritosArrayList.get(position).getEstudio());
                intent.putExtra("ano", animesFavoritosArrayList.get(position).getAno());
                intent.putExtra("sinopse", animesFavoritosArrayList.get(position).getSinopse());
                intent.putExtra("imagem", animesFavoritosArrayList.get(position).getImagem());
                intent.putExtra("codigo", animesFavoritosArrayList.get(position).getCodigoAnime());
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

    private void carregarFavoritos(){
        db.collection("usuarios").document(auth.getCurrentUser().getEmail()).collection("favoritos").orderBy("nomeAnime")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        animesFavoritosArrayList.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DTOAnimesFavoritos favoritos = new DTOAnimesFavoritos();
                                favoritos.setNomeAnime((String) document.getData().get("nomeAnime"));
                                favoritos.setDataAdicionado((String) document.getData().get("dataAdicionado"));
                                favoritos.setImagem((String) document.getData().get("imagem"));
                                favoritos.setEmailUsuario((String) document.getData().get("emailUsuario"));
                                favoritos.setSinopse((String) document.getData().get("sinopse"));
                                favoritos.setEstudio((String) document.getData().get("estudio"));
                                favoritos.setGenero((String) document.getData().get("genero"));
                                favoritos.setAno((String) document.getData().get("ano"));
                                favoritos.setCodigo((String) document.getData().get("codigo"));
                                favoritos.setCodigoAnime((String) document.getData().get("codigoAnime"));
                                animesFavoritosArrayList.add(favoritos);
                            }
                            adapterAnimesFavoritos = new AdapterAnimesFavoritos(animesFavoritosArrayList);
                            int count = adapterAnimesFavoritos.getItemCount();
                            if (count < 1) {
                                imageViewFav.setVisibility(View.VISIBLE);
                                textViewFav.setVisibility(View.VISIBLE);
                                rcFavoritos.setVisibility(View.INVISIBLE);
                            } else {
                                imageViewFav.setVisibility(View.INVISIBLE);
                                textViewFav.setVisibility(View.INVISIBLE);
                                rcFavoritos.setVisibility(View.VISIBLE);
                                rcFavoritos.setAdapter(adapterAnimesFavoritos);
                            }
                        }
                    }
                });
            }

    @Override
    public void onStart() {
        FirebaseUser currentUser = auth.getCurrentUser();
        super.onStart();
        if(currentUser != null) {
            carregarFavoritos();
        }else{
            textViewFav.setVisibility(View.VISIBLE);
        }
    }
}