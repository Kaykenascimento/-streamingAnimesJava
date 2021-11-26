package com.example.projetofirestore.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.projetofirestore.Activitys.AdicionarAnimesActivity;
import com.example.projetofirestore.Activitys.EscolherAnimeAlterarActivity;
import com.example.projetofirestore.Activitys.LoginActivity;
import com.example.projetofirestore.Classes.ConfFireBase;
import com.example.projetofirestore.Models.DTOUsuario;
import com.example.projetofirestore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.type.DateTime;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContaFragment extends Fragment {
    FirebaseAuth auth = ConfFireBase.getFirebaseAuth();
    FirebaseFirestore db = ConfFireBase.getFirebaseFirestore();
    CircleImageView ivUser;
    TextView tvNomeUsuario, tvEmailUsuario, tvDataCadastro, tvAlterarAnimesEps, tvInserirAnimesEps;
    String nome, sobrenome, email, sexo;
    Boolean admin = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_conta, container, false);

        Button buttonDeslogar = view.findViewById(R.id.buttonDeslogar);
        FloatingActionButton fbImage = view.findViewById(R.id.fbImage);
        ivUser = view.findViewById(R.id.ivUser);
        tvNomeUsuario = view.findViewById(R.id.tvNomeUser);
        tvEmailUsuario = view.findViewById(R.id.tvEmailUser);
        tvDataCadastro = view.findViewById(R.id.tvDataCadastro);
        tvAlterarAnimesEps = view.findViewById(R.id.tvAlterarAnimeEps);
        tvInserirAnimesEps = view.findViewById(R.id.tvInserirAnimesEps);

        tvAlterarAnimesEps.setVisibility(View.INVISIBLE);
        tvInserirAnimesEps.setVisibility(View.INVISIBLE);

        fbImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment myFrag = new SelecionarImagemFragment();
                Bundle bundle = new Bundle();
                bundle.putString("sexo", sexo);
                myFrag.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frameLayoutPrincipal, myFrag).commit();
            }
        });

        buttonDeslogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        tvAlterarAnimesEps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EscolherAnimeAlterarActivity.class);
                startActivity(intent);
            }
        });

        tvInserirAnimesEps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AdicionarAnimesActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void carregarUsuario() {
        db.collection("usuarios").whereEqualTo("emailUsuario", auth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DTOUsuario usuario = new DTOUsuario();
                                usuario.setNomeUsuario((String) document.getData().get("nomeUsuario"));
                                usuario.setSobrenome((String) document.getData().get("sobrenome"));
                                usuario.setImagemUsuario((String) document.getData().get("imagemUsuario"));
                                usuario.setDataCadastro((Timestamp) document.getData().get("dataCadastro"));
                                usuario.setEmailUsuario((String) document.getData().get("emailUsuario"));
                                usuario.setSexo((String) document.getData().get("sexo"));
                                usuario.setAdmin((Boolean) document.getData().get("admin"));
                                nome = usuario.getNomeUsuario();
                                sobrenome = usuario.getSobrenome();
                                email = usuario.getEmailUsuario();
                                sexo = usuario.getSexo();
                                admin = usuario.getAdmin();
                                Timestamp data = usuario.getDataCadastro();
                                Date date = data.toDate();
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                String dataFormatada = dateFormat.format(date);
                                tvNomeUsuario.setText("Nome: "+nome +" "+sobrenome);
                                tvEmailUsuario.setText("Email: "+email);
                                if(admin == true){
                                    tvAlterarAnimesEps.setVisibility(View.VISIBLE);
                                    tvInserirAnimesEps.setVisibility(View.VISIBLE);
                                }
                                else{
                                    tvAlterarAnimesEps.setVisibility(View.INVISIBLE);
                                    tvInserirAnimesEps.setVisibility(View.INVISIBLE);

                                }
                                if(sexo.equals("feminino")){
                                    tvDataCadastro.setText("Usuária desde: "+dataFormatada);
                                }
                                else if(sexo.equals("masculino")){
                                    tvDataCadastro.setText("Usuário desde: " + dataFormatada);
                                }
                                String imagem = usuario.getImagemUsuario();
                                Glide.with(getActivity()).load(imagem).into(ivUser);
                            }

                        } else {
                            Toast.makeText(getActivity(), "Erro Inesperado", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

    @Override
    public void onStart() {
        super.onStart();
        carregarUsuario();
    }
}

