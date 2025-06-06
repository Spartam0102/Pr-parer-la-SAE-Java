package App; 


import java.util.ArrayList;
import java.util.List;

public class Entreprise {

    private String nom;
    private String adresse;
    private List<Magasin> listeMagasins;

    public Entreprise(String nom, String adresse) {
        this.nom = nom;
        this.adresse = adresse;
        this.listeMagasins = new ArrayList<>();
    }

    public String getAdresse() {
        return adresse;
    }

    public List<Magasin> getListeMagasins() {
        return listeMagasins;
    }

    public String getNom() {
        return nom;
    }

    public void ajouterMagasin(Magasin magasin) {
        this.listeMagasins.add(magasin);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Entreprise)) {
            return false;
        }
        Entreprise tmp = (Entreprise) obj;
        return this.nom.equals(tmp.nom) && this.adresse.equals(tmp.adresse);
    }

    @Override
    public String toString() {
        String res = "L'entreprise nommée " + this.nom + " se situe à " + this.adresse + " et a comme magasins : \n";
        for (Magasin magasin : this.listeMagasins) {
            res += magasin.toString() + "\n";
        }
        return res;
    }
}