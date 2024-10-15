package com.albornoz.inmobiliariaandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.albornoz.inmobiliariaandroid.modelo.Propietario;
import com.albornoz.inmobiliariaandroid.request.ApiClient;
import com.albornoz.inmobiliariaandroid.request.ApiClientRetrofit;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.albornoz.inmobiliariaandroid.databinding.ActivityMainBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
        binding.appBarMain.fab.setVisibility(View.INVISIBLE);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        setHeader(navigationView);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,
                R.id.nav_profile,
                R.id.nav_real_estates,
                R.id.nav_tenants,
                R.id.nav_contracts)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    private void setHeader(NavigationView navigationView) {


        //####### Completando el header con datos de sesión #########
        // Rescatar header de navigationView
        View header = navigationView.getHeaderView(0);
        // Rescatar views del header
        ImageView avatar = header.findViewById(R.id.imageViewAvatar);
        TextView nombre = header.findViewById(R.id.textViewHeaderName);
        TextView email = header.findViewById(R.id.textViewHeaderEmail);

        // Esto ya no va porque hay que traer el propietario de la api retrofit
        //####### Obtengo ApiClient ########
        //ApiClient api = ApiClient.getApi();
        // Rescato propietario logueado y seteo valores en las vistas
        /*Propietario p = api.obtenerUsuarioActual();
        avatar.setImageResource(p.getAvatar());
        nombre.setText(p.getNombre()+" "+p.getApellido());
        email.setText(p.getEmail());*/

        //####### Completando el header con datos de sesión #########
        ApiClientRetrofit.InmobiliariaService service = ApiClientRetrofit.getInmobiliariaService(this);
        Call<Propietario> callPropietario = service.getPropietario();

        callPropietario.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Obtenemos el propietario exitosamente
                    Propietario p = response.body();
                    // Aquí puedes manejar lo que ocurre después de obtener el propietario
                    // Por ejemplo, actualizar la UI o guardar los datos en el ViewModel
                    Log.d("getPropietario", "onResponse: " + p);
                    nombre.setText(p.getNombre() + " " + p.getApellido());
                    email.setText(p.getEmail());
                    Glide.with(getApplicationContext()) // No estoy seguro de este context
                            .load(ApiClientRetrofit.getHost() + p.getAvatarUrl()) // Carga la URL de la imagen
                            .into(avatar); // Coloca la imagen en el ImageView
                } else {
                    // Manejar el caso de que no se encuentre el propietario o error en la respuesta
                    Log.d("LoginViewModel", "Error al obtener propietario: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable throwable) {
                Toast.makeText(MainActivity.this, "Error al obtener propietario", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        new AlertDialog.Builder(this)
                .setTitle("Salir")
                .setMessage("¿Desea salir de la aplicación?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.this.finishAffinity();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }
}