public class Etudiant {
    public Etudiant(String nom, String prenom, int matricule) {
        this.nom = nom;
        Prenom = prenom;
        Matricule = matricule;
    }

    private String nom;
    private String Prenom;
    private int Matricule;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public int getMatricule() {
        return Matricule;
    }

    public void setMatricule(int matricule) {
        Matricule = matricule;
    }
}
