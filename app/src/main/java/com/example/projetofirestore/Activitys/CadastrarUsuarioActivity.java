package com.example.projetofirestore.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projetofirestore.Classes.ConfFireBase;
import com.example.projetofirestore.Models.DTOUsuario;
import com.example.projetofirestore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CadastrarUsuarioActivity extends AppCompatActivity {
    FirebaseFirestore db = ConfFireBase.getFirebaseFirestore();
    FirebaseAuth auth = ConfFireBase.getFirebaseAuth();
    EditText etEmailCadastro, etSenhaCadastro, etNomeCad, etSobreNomeCad;
    Button buttonCadastrar;
    String sexo, imagem;
    CheckBox cbFeminino, cbMasculino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_usuario);

        etEmailCadastro = findViewById(R.id.etEmailCadastro);
        etSenhaCadastro = findViewById(R.id.etSenhaCadastro);
        etNomeCad = findViewById(R.id.etNomeCad);
        etSobreNomeCad = findViewById(R.id.etSobreNomeCad);
        buttonCadastrar = findViewById(R.id.buttonCadastrar);
        cbFeminino = findViewById(R.id.cbFeminino);
        cbMasculino = findViewById(R.id.cbMasculino);

        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbFeminino.isChecked()){
                    sexo = "feminino";
                    inserirAuth();
                }
                else if(cbMasculino.isChecked()){
                    sexo = "masculino";
                    inserirAuth();
                }
                else if(!cbFeminino.isChecked() || !cbMasculino.isChecked()){
                    Toast.makeText(CadastrarUsuarioActivity.this, "Marque um dos sexos", Toast.LENGTH_LONG).show();
                }
                else if(etEmailCadastro.getText().toString().isEmpty() || etSenhaCadastro.getText().toString().isEmpty() || etNomeCad.getText().toString().isEmpty() || etSobreNomeCad.getText().toString().isEmpty()){
                    Toast.makeText(CadastrarUsuarioActivity.this, "Preencha corretamente os campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void inserirAuth(){
        auth.createUserWithEmailAndPassword(etEmailCadastro.getText().toString(), etSenhaCadastro.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                auth.signInWithEmailAndPassword(etEmailCadastro.getText().toString(), etSenhaCadastro.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        inserirNoBanco();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CadastrarUsuarioActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CadastrarUsuarioActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void inserirNoBanco(){
        if(sexo == "masculino"){
            imagem = "https://animes.blob.core.windows.net/imagens/Luffy.JPEG";
        }
        else if(sexo == "feminino"){
            imagem = "https://animes.blob.core.windows.net/imagens/Nico%20Robin.jpg";
        }

        Date date = new Date();
        Timestamp timestamp = new Timestamp(date);
        DTOUsuario usuario = new DTOUsuario(etNomeCad.getText().toString(), etSobreNomeCad.getText().toString(), etEmailCadastro.getText().toString(), imagem, timestamp, sexo, false);
        db.collection("usuarios").document(auth.getCurrentUser().getEmail()).set(usuario).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Intent intent = new Intent(CadastrarUsuarioActivity.this, ConsultaActivity.class);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CadastrarUsuarioActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
