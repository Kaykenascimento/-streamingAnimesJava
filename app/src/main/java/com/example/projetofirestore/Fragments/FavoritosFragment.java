package com.example.projetofirestore.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.projetofirestore.Adapters.ViewPagerFavAdapter;
import com.example.projetofirestore.R;
import com.google.android.material.tabs.TabLayout;

public class FavoritosFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ViewPagerFavAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favoritos, container, false);

        tabLayout = view.findViewById(R.id.tbLayout);
        viewPager2  = view.findViewById(R.id.viewPager);

        FragmentManager fm = getFragmentManager();
        adapter = new ViewPagerFavAdapter(fm, getLifecycle());
        viewPager2.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText("Favoritos"));
        tabLayout.addTab(tabLayout.newTab().setText("Histórico"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        return view;
    }
}