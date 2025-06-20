package IHM.Controleur;

import BD.MagasinBD;
import Java.Livre;
import Java.Magasin;

import java.sql.SQLException;

public class ControleurSupprimerLivre {

    private MagasinBD magasinBD;
    private Magasin magasin;
    private Livre livre;

    public ControleurSupprimerLivre(MagasinBD magasinBD, Magasin magasin, Livre livre) {
        this.magasinBD = magasinBD;
        this.magasin = magasin;
        this.livre = livre;
    }

    public void handleSupprimerLivre() throws SQLException {
        magasinBD.supprimerLivreDuMagasin(livre.getIdLivre(), magasin.getIdMagasin());

    }
}
