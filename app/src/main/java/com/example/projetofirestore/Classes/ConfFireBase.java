package com.example.projetofirestore.Classes;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class ConfFireBase {
    //private static StorageReference firebaseStorage;
    private static FirebaseFirestore firebaseFirestore;
    private static FirebaseAuth firebaseAuth;

   /** public static StorageReference getFirebaseStorage(){
        if(firebaseStorage==null){
            firebaseStorage = FirebaseStorage.getInstance().getReference();
        }
        return firebaseStorage;
    }*/

    public static FirebaseAuth getFirebaseAuth(){
        if(firebaseAuth==null){
            firebaseAuth = FirebaseAuth.getInstance();
        }
        return firebaseAuth;
    }

    public static FirebaseFirestore getFirebaseFirestore(){
        if(firebaseFirestore==null){
            firebaseFirestore = FirebaseFirestore.getInstance();
        }
        return firebaseFirestore;
    }
}
