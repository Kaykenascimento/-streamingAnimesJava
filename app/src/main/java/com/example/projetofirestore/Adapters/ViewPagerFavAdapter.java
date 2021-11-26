package com.example.projetofirestore.Adapters;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.projetofirestore.Classes.ConfFireBase;
import com.example.projetofirestore.Fragments.FavoritosListaFragment;
import com.example.projetofirestore.Fragments.HistoricoFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ViewPagerFavAdapter extends FragmentStateAdapter {
    FirebaseAuth auth = ConfFireBase.getFirebaseAuth();

    public ViewPagerFavAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new HistoricoFragment();
        }
        return new FavoritosListaFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
