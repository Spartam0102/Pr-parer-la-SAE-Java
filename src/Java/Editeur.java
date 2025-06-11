package Java; 

public class Editeur {

    private int idEdit;
    private String nomEdit;

    public Editeur(int idEdit, String nomEdit) {
        this.idEdit = idEdit;
        this.nomEdit = nomEdit;
    }

    public int getIdEdit() {
        return this.idEdit;
    }

    public String getNomEdit() {
        return this.nomEdit;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Editeur)) {
            return false;
        }
        Editeur tmp = (Editeur) obj;
        return this.nomEdit.equals(tmp.nomEdit) && this.idEdit == tmp.idEdit;
    }

    @Override
    public String toString() {
        return "L'éditeur " + this.nomEdit + " possède l'id " + this.idEdit;
    }
}