package com.example.projetdam;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Modification extends AppCompatActivity {
    private Employee emp ;
    ImageView image;
    Uri UriImage;

    ActivityResultLauncher<Intent> result ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modification);
        Intent intent = getIntent();
        int p = intent.getIntExtra("position",0);
        DataBaseHelper dataBaseHelper = DataBaseHelper.GetDataBaseHelper(this);
        emp = dataBaseHelper.list.get(p);
        EditText nom = this.findViewById(R.id.modifier_nom);
        EditText prenom = this.findViewById(R.id.modifier_prenom);
        EditText email = this.findViewById(R.id.modifier_email);
        EditText numero = this.findViewById(R.id.modifier_numero);

        tackPick();

        image = this.findViewById(R.id.imageview);

        image.setOnClickListener(v -> {
            // Ook !
            Intent intent2 = new Intent(MediaStore.ACTION_PICK_IMAGES);
            result.launch(intent2);
        });

        nom.setText(emp.getNom());
        prenom.setText(emp.getPrenom());
        email.setText(emp.getEmail());
        numero.setText(emp.getNumero());
        image.setImageURI(emp.getPhoto());
        UriImage = emp.getPhoto();
        Button btn = this.findViewById(R.id.button_Modifier);
        btn.setOnClickListener(v -> {
            emp.setNom(nom.getText().toString());
            emp.setPrenom(prenom.getText().toString());
            emp.setEmail(email.getText().toString());
            emp.setNumero(numero.getText().toString());
            dataBaseHelper.updateEmployee(emp);
        });
        Toolbar toolbar = this.findViewById(R.id.toolbarModification);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void tackPick(){
        result = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        try {
                            UriImage = o.getData().getData();
                            image.setImageURI(UriImage);
                        } catch (Exception e){
                            Toast.makeText(Modification.this, "vous n'avais Choisi Aucune photo", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }
}