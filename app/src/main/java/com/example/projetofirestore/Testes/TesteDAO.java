package com.example.projetofirestore.Testes;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetofirestore.Adapters.AdapterAnimeSugerido;
import com.example.projetofirestore.Adapters.AdapterAnimes;
import com.example.projetofirestore.Classes.ConfFireBase;
import com.example.projetofirestore.Models.DTOAnimes;
import com.example.projetofirestore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TesteDAO {
    FirebaseFirestore db = ConfFireBase.getFirebaseFirestore();
    ArrayList<DTOAnimes> animesArrayList = new ArrayList<>();
    ArrayList<DTOAnimes> animesArrayList2 = new ArrayList<>(animesArrayList);
    AdapterAnimes adapterAnimes;
    Context context;
    FragmentActivity view;

    public TesteDAO (Context context, FragmentActivity view){
        this.context = context;
        this.view = view;
    }

    public TesteDAO (Context context){
        this.context = context;
    }



}
