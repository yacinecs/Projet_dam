package com.example.projetdam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent intent = getIntent();
        int p = intent.getIntExtra("p",0);
        DataBaseHelper db = DataBaseHelper.GetDataBaseHelper(this);
        Employee emp = db.list.get(p);

        TextView nom = this.findViewById(R.id.nomProfile);
        ImageView img = this.findViewById(R.id.ImageProfile);
        ImageView call = this.findViewById(R.id.ProfileAppel);
        ImageView send_email = this.findViewById(R.id.ProfileEmail);
        ImageView sms = this.findViewById(R.id.ProfileSMS);

        TextView pre_nom = this.findViewById(R.id.PrenomProfile);
        TextView email = this.findViewById(R.id.EmailProfile);
        TextView num_ero = this.findViewById(R.id.NumeroProfile);

        call.setOnClickListener(v->{
            Intent intent2 = new Intent(Intent.ACTION_DIAL);
            intent2.setData(Uri.parse("tel:"+emp.getNumero()));
            startActivity(intent2);
        });

        nom.setText(emp.getNom());
        pre_nom.setText(emp.getPrenom());
        email.setText(emp.getEmail());
        num_ero.setText(emp.getNumero());
        img.setImageURI(emp.getPhoto());
    }
}