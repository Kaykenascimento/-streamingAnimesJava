package com.example.projetofirestore.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.projetofirestore.Fragments.CatalogoListaFragment;
import com.example.projetofirestore.Fragments.GenerosFragment;
import com.example.projetofirestore.Models.DTOAnimes;

import java.util.ArrayList;

public class ViewPagerCatAdapter extends FragmentStateAdapter {
    private ArrayList<DTOAnimes> animesArrayList = new ArrayList<>();

    public ViewPagerCatAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 1:
                return new CatalogoListaFragment();
        }

        return new GenerosFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
