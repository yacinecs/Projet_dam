package com.example.projetdam;

import android.net.Uri;

public class Employee {
    private String nom;
    private String prenom;
    private String numero;
    private int id;
    private String email;
    private Uri photo;

    public Employee(String nom, String prenom, String numero, int id, String email, Uri photo) {
        this.nom = nom;
        this.prenom = prenom;
        this.numero = numero;
        this.id = id;
        this.email = email;
        this.photo = photo;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Uri getPhoto() {
        return photo;
    }
    public void setPhoto(Uri photo) {
        this.photo = photo;
    }
}
