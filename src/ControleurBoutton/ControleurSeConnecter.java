package IHM;

import javafx.stage.Stage;

public class ControleurSeConnecter {

    public void seConnecter(Stage fenetreActuelle, String username, String motDePasse) {
        // 🔐 Ici, tu peux faire une vraie vérification plus tard
        if (username.isEmpty() || motDePasse.isEmpty()) {
            System.out.println("Veuillez remplir tous les champs !");
            return;
        }

        try {
            // ✅ Ouvrir la fenêtre des magasins
            FenetreMagasins fenetreMagasins = new FenetreMagasins();
            Stage nouvelleFenetre = new Stage();
            fenetreMagasins.start(nouvelleFenetre);

            // ❌ Fermer la fenêtre de connexion
            fenetreActuelle.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
