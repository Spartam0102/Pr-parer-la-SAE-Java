package IHM.Controleur;

import java.time.LocalDate;
import java.util.Map;
import BD.*;
import Java.*;
import IHM.FenetreModifierStock;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.time.LocalDate;

public class ControleurCommander implements EventHandler<MouseEvent> {

    private Magasin magasin;
    private Client client;
    private CommandeBD commandeBD;
    private String modeDeReception;

    public ControleurCommander(ConnexionMySQL connexion, Magasin magasin, Client client, String modeDeReception) {
        this.magasin =magasin;
        this.client = client;
        this.commandeBD = new CommandeBD(connexion);
        this.modeDeReception = modeDeReception;
    }

    @Override
    public void handle(MouseEvent event) {
        int idCommande = commandeBD.genererNouvelIdCommande();

        LocalDate dateDeCommande = LocalDate.now();
        String dateStr = dateDeCommande.toString();



        Commande commande = new Commande(idCommande, dateStr, modeDeReception, client, this.magasin);

        Map<Livre, Integer> panier = client.getPanier();
        for (Map.Entry<Livre, Integer> entry : panier.entrySet()) {
            commande.ajouterLivre(entry.getKey(), entry.getValue());
        }

        commandeBD.enregistrerCommande(commande);
        livreBD.majQteApresCommande(client, bonMagasin);
        client.reinitialiserPanier();
        clientBD.sauvegardePanierBD(client);

    }
}