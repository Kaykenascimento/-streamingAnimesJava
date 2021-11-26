package com.example.projetofirestore.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projetofirestore.Adapters.ViewPagerCatAdapter;
import com.example.projetofirestore.R;
import com.google.android.material.tabs.TabLayout;

public class CatalogoFragment extends Fragment {
    TabLayout tabLayout;
    ViewPagerCatAdapter adapter;
    ViewPager2 viewPager2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_catalogo, container, false);

        tabLayout = view.findViewById(R.id.tabLayoutCat);
        viewPager2 = view.findViewById(R.id.viewPagerCat);

        FragmentManager fm = getFragmentManager();
        adapter = new ViewPagerCatAdapter(fm, getLifecycle());
        viewPager2.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText("Gêneros"));
        tabLayout.addTab(tabLayout.newTab().setText("Todos"));

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