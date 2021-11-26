package com.example.projetofirestore.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.projetofirestore.Classes.ConfFireBase;
import com.example.projetofirestore.Classes.ImagensViewHolder;
import com.example.projetofirestore.Models.DTOImagens;
import com.example.projetofirestore.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.exoplayer2.C;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SelecionarImagemFragment extends Fragment {
    FirebaseFirestore db = ConfFireBase.getFirebaseFirestore();
    FirebaseAuth auth = ConfFireBase.getFirebaseAuth();
    FirestoreRecyclerAdapter<DTOImagens, ImagensViewHolder> adapter;
    FirestoreRecyclerOptions<DTOImagens> options;
    ArrayList<DTOImagens> imagensArrayList = new ArrayList<>();
    RecyclerView rcImagens;
    Button buttonConfirmar;
    String sexo, img;
    TextView tvSelecioneImagem;
    Boolean checked = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_selecionar_imagem, container, false);

        rcImagens = view.findViewById(R.id.rcImagens);
        buttonConfirmar = view.findViewById(R.id.buttonConfirmar);
        tvSelecioneImagem = view.findViewById(R.id.tvSelecioneImagem);
        buttonConfirmar.setVisibility(View.INVISIBLE);

        Bundle bundle = getArguments();
        sexo = bundle.getString("sexo");

        rcImagens.setLayoutManager(new GridLayoutManager(rcImagens.getContext(), 3, GridLayoutManager.VERTICAL, false));

        Query query = db.collection("perfil").document("imagens").collection(sexo);
        options = new FirestoreRecyclerOptions.Builder<DTOImagens>().setQuery(query, DTOImagens.class).build();

        adapter = new FirestoreRecyclerAdapter<DTOImagens, ImagensViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ImagensViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull DTOImagens model) {
                imagensArrayList.add(model);
                img = imagensArrayList.get(position).getLink();
                Glide.with(getActivity()).load(img).into(holder.ivImagensPerfil);

                holder.fbDone.setVisibility(View.INVISIBLE);

                holder.ivImagensPerfil.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(checked){
                            holder.fbDone.setVisibility(View.INVISIBLE);
                            checked = false;
                            buttonConfirmar.setVisibility(View.INVISIBLE);
                            tvSelecioneImagem.setVisibility(View.VISIBLE);
                        }
                        else{
                            holder.fbDone.setVisibility(View.VISIBLE);
                            checked = true;
                            buttonConfirmar.setVisibility(View.VISIBLE);
                            tvSelecioneImagem.setVisibility(View.INVISIBLE);
                            String imagem = imagensArrayList.get(position).getLink();
                            buttonConfirmar.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    db.collection("usuarios").document(auth.getCurrentUser().getEmail()).update("imagemUsuario", imagem).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(@NonNull Void unused) {
                                            ContaFragment fragment = new ContaFragment();
                                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                            transaction.replace(R.id.frameLayoutPrincipal, fragment);
                                            transaction.commit();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getActivity(), "Deu errado", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                        }
                    }
                });

            }

            @NonNull
            @Override
            public ImagensViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_imagens, parent, false);
                return new ImagensViewHolder(view);
            }
        };
        adapter.startListening();
        rcImagens.setAdapter(adapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.startListening();
    }

}