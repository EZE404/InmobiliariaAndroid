package com.albornoz.inmobiliariaandroid;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.albornoz.inmobiliariaandroid.databinding.ActivityMainBinding;
import com.albornoz.inmobiliariaandroid.databinding.NavHeaderMainBinding;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicializamos el View Binding para activity_main
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Inicializamos el ViewModel
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MainActivityViewModel.class);

        // Configuramos el header usando View Binding
        setHeader(navigationView);

        // Configuración de navegación
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile, R.id.nav_real_estates, R.id.nav_tenants, R.id.nav_contracts)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    private void setHeader(NavigationView navigationView) {
        // Utilizamos View Binding para el header del NavigationView
        NavHeaderMainBinding headerBinding = NavHeaderMainBinding.bind(navigationView.getHeaderView(0));
        // Observar los cambios en los LiveData del ViewModel
        viewModel.getNombre().observe(this, headerBinding.textViewHeaderName::setText);
        viewModel.getEmail().observe(this, headerBinding.textViewHeaderEmail::setText);
        viewModel.getAvatarUrl().observe(this, url -> {
            Glide.with(this)
                    .load(url)
                    .into(headerBinding.imageViewAvatar);
        });

        // Llamamos a fetchPropietario para cargar los datos del propietario
        viewModel.fetchPropietario();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
