package com.example.projetdam;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "employ.db";
    public ArrayList<Employee> list = new ArrayList<>();
    private static DataBaseHelper d;

    private DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public static DataBaseHelper GetDataBaseHelper(@Nullable Context context) {
        if( d == null) {
            d = new DataBaseHelper(context);
            d.getAllEmployees();
        }
        return d ;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE EmployeeList (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nom TEXT," +
                "prenom TEXT," +
                "numero TEXT," +
                "email TEXT," +
                "photoUri TEXT" + // Assuming photoUri is the URI for the photo
                ")";
        db.execSQL(createTable);
    }
    public boolean AddOne(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nom", employee.getNom());
        cv.put("prenom", employee.getPrenom());
        cv.put("numero", employee.getNumero());
        cv.put("email", employee.getEmail());
        if (employee.getPhoto() != null) {
            cv.put("photoUri", employee.getPhoto().toString());
        } // Assuming photoUri is a URI object

        long insert = db.insert("EmployeeList", null, cv);
        getAllEmployees();
        return insert != -1;
    }
    public void getAllEmployees() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM EmployeeList", null);
        list.clear();
        if (cursor.moveToFirst()) {
            do {
                // Retrieve data from cursor and create Employee objects
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("ID"));
                @SuppressLint("Range") String nom = cursor.getString(cursor.getColumnIndex("nom"));
                @SuppressLint("Range") String prenom = cursor.getString(cursor.getColumnIndex("prenom"));
                @SuppressLint("Range") String numero = cursor.getString(cursor.getColumnIndex("numero"));
                @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("email"));
                @SuppressLint("Range") String photoUriStr = cursor.getString(cursor.getColumnIndex("photoUri"));

                int defaultImageResourceId = ListeOfEmployee.context.getResources().getIdentifier("baseline_contacts_24", "drawable", ListeOfEmployee.context.getPackageName());
                // Construire l'URI à partir de l'ID de la ressource
                Uri photoUri = Uri.parse("android.resource://" + ListeOfEmployee.context.getPackageName() + "/" + defaultImageResourceId);

                try {
                   // photoUri = Uri.parse(photoUriStr); // Convert String to Uri
                } catch (Exception e ){
                    System.out.println("Erreur Photo");
                } // Convert String to Uri
                Employee employee = new Employee(nom, prenom, numero, id, email, photoUri);
                list.add(employee);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }
    public boolean updateEmployee(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nom", employee.getNom());
        cv.put("prenom", employee.getPrenom());
        cv.put("numero", employee.getNumero());
        cv.put("email", employee.getEmail());
        if (employee.getPhoto() != null) {
            cv.put("photoUri", employee.getPhoto().toString());
        }
        String selection = "ID = ?";
        String[] selectionArgs = {String.valueOf(employee.getId())};
        int updatedRows = db.update("EmployeeList", cv, selection, selectionArgs);
        db.close();
        getAllEmployees();
        return updatedRows > 0;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean deleteEmployee(int employeeId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = "ID = ?";
        String[] selectionArgs = { String.valueOf(employeeId) };
        int deletedRows = db.delete("EmployeeList", selection, selectionArgs);
        getAllEmployees();
        return deletedRows > 0;
    }
}
