package com.example.projetdam;
import static androidx.core.content.ContextCompat.startActivity;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ListeOfEmployee extends AppCompatActivity {
    DataBaseHelper db;
    static  Context context;
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_of_employee);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Demande d'autorisation si elle n'a pas été accordée
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_READ_EXTERNAL_STORAGE);
        }
        context = this ;
        RemplaceFragment(new ListeEmployeeFragment(this));
        db = DataBaseHelper.GetDataBaseHelper(this);
        BottomNavigationView nv = this.findViewById(R.id.navigationBar);

        nv.setOnNavigationItemSelectedListener(item ->{
            int s = item.getItemId();
            if (s == R.id.Navigationadd) {
                RemplaceFragment(new AddFragment(this));
            } else if (s == R.id.Navigationcontact) {
                RemplaceFragment(new ListeEmployeeFragment(this));
            }
            return true;
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int s = item.getItemId();

        if (s == R.id.Navigationadd) {
            RemplaceFragment(new AddFragment(this));
        } else if (s == R.id.Navigationcontact) {
            RemplaceFragment(new ListeEmployeeFragment(this));
        }
        return super.onOptionsItemSelected(item);
    }
    private void RemplaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.FrameLayoutId,fragment);
        fragmentTransaction.commit();
    }

    public void modification(int position){
        Intent intent = new Intent( ListeOfEmployee.this,Modification.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }
    public void Profil(int position){
        Intent intent = new Intent( ListeOfEmployee.this,Profile.class);
        intent.putExtra("p", position);
        startActivity(intent);
    }
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_READ_EXTERNAL_STORAGE) {
            // Vérifie si l'autorisation a été accordée
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // L'autorisation a été accordée, vous pouvez exécuter votre logique ici
                // Par exemple, charger des images à partir du stockage externe
            } else {
                // L'utilisateur a refusé l'autorisation, vous pouvez afficher un message ou prendre une autre action
            }
        }
    }
}