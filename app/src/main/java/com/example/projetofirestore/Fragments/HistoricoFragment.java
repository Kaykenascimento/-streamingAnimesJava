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
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetofirestore.Activitys.AnimeSelecionadoActivity;
import com.example.projetofirestore.Activitys.AssistirEpsActivity;
import com.example.projetofirestore.Adapters.AdapterHistorico;
import com.example.projetofirestore.Classes.ConfFireBase;
import com.example.projetofirestore.Classes.RecyclerViewItemClickListener;
import com.example.projetofirestore.Models.DTOHistorico;
import com.example.projetofirestore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HistoricoFragment extends Fragment {
    FirebaseAuth auth = ConfFireBase.getFirebaseAuth();
    FirebaseFirestore db = ConfFireBase.getFirebaseFirestore();
    ArrayList<DTOHistorico> historicoArrayList = new ArrayList<>();
    AdapterHistorico adapterHistorico;
    RecyclerView rcHistorico;
    TextView tvAnimeAssistido;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_historico, container, false);

        rcHistorico = view.findViewById(R.id.rcHistorico);
        tvAnimeAssistido = view.findViewById(R.id.tvAnimeAssistido);

        tvAnimeAssistido.setVisibility(View.INVISIBLE);

        rcHistorico.setLayoutManager(new GridLayoutManager(rcHistorico.getContext(), 2, GridLayoutManager.VERTICAL, false));

        rcHistorico.addOnItemTouchListener(new RecyclerViewItemClickListener(getActivity(), rcHistorico, new RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), AssistirEpsActivity.class);
                intent.putExtra("nomeAnime", historicoArrayList.get(position).getNomeAnime());
                intent.putExtra("minutoAssistido", historicoArrayList.get(position).getMinutoAssistido());
                intent.putExtra("titulo", historicoArrayList.get(position).getTituloEp());
                intent.putExtra("imagem", historicoArrayList.get(position).getImagem());
                intent.putExtra("sinopse", historicoArrayList.get(position).getSinopse());
                intent.putExtra("link", historicoArrayList.get(position).getLink());
                intent.putExtra("saga", historicoArrayList.get(position).getSaga());
                intent.putExtra("codigo", historicoArrayList.get(position).getCodigo());
                intent.putExtra("duracao", historicoArrayList.get(position).getDuracao());
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

    private void checarHistorico(){
        db.collection("usuarios").document(auth.getCurrentUser().getEmail()).collection("historico").orderBy("data", Query.Direction.DESCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        historicoArrayList.clear();
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DTOHistorico historico = new DTOHistorico();
                                historico.setNomeAnime((String) document.getData().get("nomeAnime"));
                                historico.setTituloEp((String) document.getData().get("tituloEp"));
                                historico.setImagem((String) document.getData().get("imagem"));
                                historico.setLink((String) document.getData().get("link"));
                                historico.setSinopse((String) document.getData().get("sinopse"));
                                historico.setMinutoAssistido((Long) document.getData().get("minutoAssistido"));
                                historico.setSaga((String) document.getData().get("saga"));
                                historico.setCodigo((String) document.getData().get("codigo"));
                                historico.setDuracao((String) document.getData().get("duracao"));
                                historicoArrayList.add(historico);
                            }
                            adapterHistorico = new AdapterHistorico(historicoArrayList);
                            rcHistorico.setAdapter(adapterHistorico);
                            int count = adapterHistorico.getItemCount();
                            if(count <1 ){
                                tvAnimeAssistido.setVisibility(View.VISIBLE);
                            }
                            else{
                                tvAnimeAssistido.setVisibility(View.INVISIBLE);
                            }
                        }
                        else{
                            Toast.makeText(getActivity(), "Erro ao checar o histórico", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if(user != null){
            checarHistorico();
            tvAnimeAssistido.setVisibility(View.INVISIBLE);
        }
        else if(user == null){
            tvAnimeAssistido.setVisibility(View.VISIBLE);
        }
    }
}