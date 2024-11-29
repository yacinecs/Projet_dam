package com.example.projetdam;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

public class AdapterListContact extends RecyclerView.Adapter<AdapterListContact.ViewHolder>  {
    ArrayList<Employee> itemViewemp;
    ListeEmployeeFragment fragment;
    public AdapterListContact( ArrayList<Employee> itemView) {
        this.itemViewemp = itemView;
    }
    public AdapterListContact( ArrayList<Employee> itemView , ListeEmployeeFragment fragment ) {
        this.itemViewemp = itemView;
        this.fragment = fragment ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carte_phone_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Employee employee = itemViewemp.get(position);
        holder.emp = employee;
        holder.position = position;
        holder.fragment = fragment ;
        holder.txt_contactname.setText(employee.getNom());
        holder.txt_contactnumber.setText(employee.getNumero());
        if (employee.getPhoto() != null ){
            holder.img_contactImage.setImageURI(employee.getPhoto());
        }
    }

    @Override
    public int getItemCount() {
        return itemViewemp.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_contactname;
        TextView txt_contactnumber;
        ImageView img_contactImage;
        Employee emp;
        int position;
        ListeEmployeeFragment fragment;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_contactname = itemView.findViewById(R.id.contactname);
            txt_contactnumber= itemView.findViewById(R.id.contactnumber);
            img_contactImage= itemView.findViewById(R.id.contactImage);
            itemView.setOnClickListener(v->{
                int adapterPosition = this.getAdapterPosition();
                String options[] = {"Modifier", "Supprimer", "Profile","appler","Envoyer un Email","Envoyer un SMS"};
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle(emp.getNom());
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==0){
                            fragment.modification(adapterPosition);
                        } else if (which==1) {
                            fragment.Suppression(adapterPosition, fragment.requireView());
                            Toast.makeText(itemView.getContext(), "Vous Avez supprimer le Contact : "+ emp.getNom(), Toast.LENGTH_SHORT).show();
                        }else if (which==2) {
                            fragment.Profile(adapterPosition);
                        }else if (which==3) {
                            fragment.call(emp.getNumero());
                        }else if (which==4) {
                            Toast.makeText(itemView.getContext()," "+ emp.getEmail(), Toast.LENGTH_SHORT).show();
                        }else if (which==5) {
                            Toast.makeText(itemView.getContext()," "+ emp.getNumero(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).create().show();
                });
        }
    }
}