package App; 

public abstract class Personne{
    
    private String nom;
    private String prenom;
    private String dateDeNaissance; 

    public Personne (String nom, String prenom, String dateDeNaissance){
        this.nom = nom;
        this.prenom = prenom;
        this.dateDeNaissance = dateDeNaissance;
    }

    public String getDateDeNaissance() {
        return this.dateDeNaissance;
    }

    public String getNom() {
        return this.nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (obj == null){
            return false;
        }
        if(!(obj instanceof Personne)){
            return false;
        }
        Personne tmp = (Personne) obj;
        return this.nom.equals(tmp.nom) && this.prenom.equals(tmp.prenom )&& this.dateDeNaissance.equals(tmp.dateDeNaissance);
    }
    
    @Override
    public String toString(){
        return "La personne nommée " + this.prenom +" "+ this.nom + " est née le " + this.dateDeNaissance;
    }
}