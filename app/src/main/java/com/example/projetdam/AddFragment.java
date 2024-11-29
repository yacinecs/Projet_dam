package com.example.projetdam;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.net.URI;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    String[] cameraPermission;
    static final int CAMERA_PERMISSION_CODE = 100 ;
    static final int Image_from_Gallery_Code = 300 ;
    static final int Image_from_Camera_Code = 400 ;
    String[] storagePermission;
    static final int STORAGE_PERMISSION_CODE = 200 ;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ActivityResultLauncher<Intent> result ;
    ListeOfEmployee emp ;
    ImageView image ;
    Uri UriImage;
    DataBaseHelper db = DataBaseHelper.GetDataBaseHelper(this.getContext());

    public AddFragment() {
        // Required empty public constructor
    }
    public AddFragment(ListeOfEmployee emp ) {
        this.emp = emp ;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        Button btn = view.findViewById(R.id.button_ajouter);
        tackPick();

        image = view.findViewById(R.id.imageview_preview);

        image.setOnClickListener(v -> {
            // Ook !
            Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
            result.launch(intent);
        });

        btn.setOnClickListener(v->{
            EditText nom = view.findViewById(R.id.edittext_nom);
            EditText prenom = view.findViewById(R.id.edittext_prenom);
            EditText numero = view.findViewById(R.id.edittext_numero);
            EditText email = view.findViewById(R.id.edittext_email);
            String n = nom.getText().toString();
            String num = numero.getText().toString();
            if ( !n.isEmpty() && !num.isEmpty() ) {
                Employee e = new Employee( n ,
                        prenom.getText().toString(),num,
                        0,email.getText().toString(),UriImage) ;
                db.AddOne(e);
                nom.setText("");
                prenom.setText("");
                numero.setText("");
                email.setText("");
            } else {
                Toast.makeText(emp, "Vous devez Au moin remplire le nom et Numero", Toast.LENGTH_SHORT).show();
            }
            
        });

        return view;
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
                            Toast.makeText(emp, "vous n'avais Choisi Aucune photo", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}