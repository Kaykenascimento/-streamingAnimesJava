package com.example.projetofirestore.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.projetofirestore.Classes.ConfFireBase;
import com.example.projetofirestore.Classes.GenerosViewHolder;
import com.example.projetofirestore.Classes.RecyclerViewItemClickListener;
import com.example.projetofirestore.Models.DTOGeneros;
import com.example.projetofirestore.Models.DTOImagens;
import com.example.projetofirestore.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GenerosFragment extends Fragment {
    FirebaseFirestore db = ConfFireBase.getFirebaseFirestore();
    FirestoreRecyclerOptions<DTOGeneros> options;
    FirestoreRecyclerAdapter<DTOGeneros, GenerosViewHolder> adapter;
    RecyclerView rcGeneros;
    ArrayList<DTOGeneros> generosArrayList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_generos, container, false);

        rcGeneros = view.findViewById(R.id.rcGeneros);
        rcGeneros.setLayoutManager(new GridLayoutManager(rcGeneros.getContext(), 2, GridLayoutManager.VERTICAL, false));

        Query query = db.collection("generos");
        options = new FirestoreRecyclerOptions.Builder<DTOGeneros>().setQuery(query, DTOGeneros.class).build();
        adapter = new FirestoreRecyclerAdapter<DTOGeneros, GenerosViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull GenerosViewHolder holder, int position, @NonNull DTOGeneros model) {
                generosArrayList.add(model);
                holder.tvNomeGenero.setText(model.getNomeGenero());
                String imagem = model.getImagem();
                Picasso.get().load(imagem).into(holder.ivGeneros);
            }

            @NonNull
            @Override
            public GenerosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_generos, parent, false);

                return new GenerosViewHolder(view);
            }
        };
        adapter.startListening();
        rcGeneros.setAdapter(adapter);

        rcGeneros.addOnItemTouchListener(new RecyclerViewItemClickListener(getActivity(), rcGeneros, new RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String genero = generosArrayList.get(position).getNomeGenero();
                Fragment myFrag = new AnimesGeneroFragment();
                Bundle bundle = new Bundle();
                bundle.putString("genero", genero);
                myFrag.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frameLayoutPrincipal, myFrag).commit();
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