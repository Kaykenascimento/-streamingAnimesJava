package com.example.projetofirestore.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.projetofirestore.Classes.ConfFireBase;
import com.example.projetofirestore.Fragments.BuscarFragment;
import com.example.projetofirestore.Fragments.CatalogoFragment;
import com.example.projetofirestore.Fragments.ContaFragment;
import com.example.projetofirestore.Fragments.ContaSemLoginFragment;
import com.example.projetofirestore.Fragments.FavoritosFragment;
import com.example.projetofirestore.Fragments.InicioFragment;
import com.example.projetofirestore.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ConsultaActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FirebaseAuth auth = ConfFireBase.getFirebaseAuth();
    Boolean user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.inicio:
                        InicioFragment fragment = new InicioFragment();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frameLayoutPrincipal, fragment);
                        transaction.commit();
                        return true;

                    case R.id.favoritos:
                        FavoritosFragment fragment2 = new FavoritosFragment();
                        FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                        transaction2.replace(R.id.frameLayoutPrincipal, fragment2);
                        transaction2.commit();
                        return true;

                    case R.id.catalogo:
                        CatalogoFragment fragment3 = new CatalogoFragment();
                        FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();
                        transaction3.replace(R.id.frameLayoutPrincipal, fragment3);
                        transaction3.commit();
                        return true;

                    case R.id.conta:
                        if(user == true) {
                            ContaFragment fragment4 = new ContaFragment();
                            FragmentTransaction transaction4 = getSupportFragmentManager().beginTransaction();
                            transaction4.replace(R.id.frameLayoutPrincipal, fragment4);
                            transaction4.commit();
                        }
                        else{
                            ContaSemLoginFragment fragment4 = new ContaSemLoginFragment();
                            FragmentTransaction transaction4 = getSupportFragmentManager().beginTransaction();
                            transaction4.replace(R.id.frameLayoutPrincipal, fragment4);
                            transaction4.commit();
                        }
                        return true;

                    case R.id.buscar:
                         BuscarFragment fragment5 = new BuscarFragment();
                         FragmentTransaction transaction5 = getSupportFragmentManager().beginTransaction();
                         transaction5.replace(R.id.frameLayoutPrincipal, fragment5);
                         transaction5.commit();
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.appbar_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.bt_buscarAnimes);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) menuItem.getActionView();
        searchView.setQueryHint("Digite algo aqui");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            user = true;
            bottomNavigationView.setSelectedItemId(R.id.inicio);
            InicioFragment fragment = new InicioFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayoutPrincipal, fragment);
            transaction.commit();
        } else {
            user = false;
            bottomNavigationView.setSelectedItemId(R.id.inicio);
            InicioFragment fragment = new InicioFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayoutPrincipal, fragment);
            transaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ConsultaActivity.this.finishAffinity();
        } else{
            ConsultaActivity.this.finish();
            System.exit(0);
        }
    }
}


