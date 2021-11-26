package com.example.projetofirestore.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.projetofirestore.Activitys.CadastrarUsuarioActivity;
import com.example.projetofirestore.Activitys.LoginActivity;
import com.example.projetofirestore.R;

import java.util.zip.Inflater;

public class ContaSemLoginFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_conta_sem_login, container, false);

        Button buttonLogin = view.findViewById(R.id.buttonLogin);
        Button buttonCriarConta = view.findViewById(R.id.btCriarConta);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        buttonCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CadastrarUsuarioActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}