import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    static ArrayList<Etudiant> etd = new ArrayList<Etudiant>();
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "dbtp4";
    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override public void onCreate(SQLiteDatabase db)
    {
        String CREATE_TABLE = "CREATE TABLE etudiant(_id INTEGER, matricule TEXT PRIMARY KEY, nom TEXT, prenom TEXT)";
        db.execSQL(CREATE_TABLE);
    }
    // La méthode onUpgrade permet de mettre à jours la base de données
    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    { db.execSQL("DROP TABLE IF EXISTS ETUDIANT");
        // Drop older table if existed
        onCreate(db);
        // Creating tables again
    }
    public Etudiant getEtudiant(String matricule) {
        SQLiteDatabasedb = this.getReadableDatabase();
        Cursorcursor= db.rawQuery("Select * frometudiantwherematricule=?",new String[]{matricule});
        if (cursor.getCount()==0) return null;
        cursor.moveToFirst();
        Etudiant e = new Etudiant(cursor.getString(0), cursor.getString(1), cursor.getString(2));
        return e;
    }
    public void addEtudiant(Etudiant e) {
        SQLiteDatabasedb= this.getWritableDatabase();
        ContentValuesval = new ContentValues();
        val.put("matricule", e.matricule);val.put("nom", e.nom);val.put("nom", e.prenom);
        db.insert("etudiant",null,val);
        db.close();
    }
    public void updateEtudiant(Etudiant e) {

    }
    public void deleteEtudiant(Etudiant e) {

    }

    public getEtudiants() {

    }


}
