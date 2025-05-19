import java.sql.*;
import java.util.Scanner;
import java.io.*;

public class EditerFacture {

    public static void main(String[] args) {
        String serveur = "127.0.0.1";
        String baseDeDonnees = "Librairie";
        String login = "limet";
        String fichierRequete = null;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--serveur": serveur = args[++i]; break;
                case "--bd": baseDeDonnees = args[++i]; break;
                case "--login": login = args[++i]; break;
                case "--requete": fichierRequete = args[++i]; break;
            }
        }

        if (fichierRequete == null) {
            System.out.println("Erreur : veuillez fournir un fichier requête avec --requete.");
            return;
        }

        Console console = System.console();
        String password = new String(console.readPassword("Mot de passe SQL: "));

        try (MySQL db = new MySQL(login, password, serveur, baseDeDonnees)) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Entrez le mois et l'année sous la forme mm/aaaa: ");
            String[] date = scanner.nextLine().split("/");
            int mois = Integer.parseInt(date[0]);
            int annee = Integer.parseInt(date[1]);

            String requete = new String(java.nio.file.Files.readAllBytes(new File(fichierRequete).toPath()));
            String resultat = faireFactures(requete, mois, annee, db);
            System.out.println(resultat);

        } catch (Exception e) {
            System.out.println("Erreur de traitement : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static String faireFactures(String requete, int mois, int annee, MySQL db) throws SQLException {
        PreparedStatement stmt = db.getConnection().prepareStatement(requete);
        stmt.setInt(1, mois);
        stmt.setInt(2, annee);

        ResultSet rs = stmt.executeQuery();

        StringBuilder res = new StringBuilder();
        res.append("Factures du ").append(mois).append("/").append(annee).append("\n");

        String magasinActuel = null, commandeActuelle = null;
        double totalCommande = 0, totalChiffre = 0;
        int nbLivre = 1, nbLivreCommande = 0, nbLivreMagasin = 0, nbFactureMagasin = 0, nbLivreTotal = 0;
        boolean faire = true;

        while (rs.next()) {
            String magasin = rs.getString("nommag");
            String nomcli = rs.getString("nomcli");
            String prenomcli = rs.getString("prenomcli");
            String adressecli = rs.getString("adressecli");
            String codepostal = rs.getString("codepostal");
            String villecli = rs.getString("villecli");
            String numcom = rs.getString("numcom");
            Date datecom = rs.getDate("datecom");
            String isbn = rs.getString("isbn");
            String titre = rs.getString("titre");
            int qte = rs.getInt("qte");
            double prix = rs.getDouble("prixvente");
            double totalLigne = qte * prix;

            if (!magasin.equals(magasinActuel)) {
                if (magasinActuel != null) {
                    res.append("                                                                        --------\n");
                    res.append(String.format("                                                                  Total%9.2f\n", totalCommande));
                    res.append("--------------------------------------------------------------------------------\n");
                    res.append(nbFactureMagasin).append(" factures éditées\n");
                    res.append(nbLivreMagasin).append(" livres vendus\n");
                    res.append("********************************************************************************\n");
                    nbLivreMagasin = 0;
                    nbFactureMagasin = 0;
                }
                res.append("Edition des factures du magasin ").append(magasin).append("\n");
                res.append("--------------------------------------------------------------------------------\n");
                magasinActuel = magasin;
                faire = false;
            }

            if (!numcom.equals(commandeActuelle)) {
                if (commandeActuelle != null && faire) {
                    res.append("                                                                        --------\n");
                    res.append(String.format("                                                                  Total%9.2f\n", totalCommande));
                    res.append("--------------------------------------------------------------------------------\n");
                }

                nbLivre = 1;
                faire = true;
                res.append(nomcli).append(" ").append(prenomcli).append("\n");
                res.append(adressecli).append("\n");
                res.append(codepostal).append(" ").append(villecli).append("\n");
                res.append("                        commande n°").append(numcom).append(" du ").append(datecom).append("\n");
                res.append("      ISBN               Titre                               qte   prix    total\n");

                commandeActuelle = numcom;
                totalCommande = 0;
                nbFactureMagasin++;
            }

            if (titre.length() > 38) {
                titre = titre.substring(0, 38) + " ...";
            }

            res.append(String.format("%d %s  %-43s %2d  %6.2f   %6.2f\n", nbLivre, isbn, titre, qte, prix, totalLigne));
            totalCommande += totalLigne;
            nbLivreCommande += qte;
            nbLivreMagasin += qte;
            nbLivreTotal += qte;
            totalChiffre += totalLigne;
            nbLivre++;
        }

        if (commandeActuelle != null) {
            res.append("                                                                        --------\n");
            res.append(String.format("                                                                  Total%9.2f\n", totalCommande));
            res.append("--------------------------------------------------------------------------------\n");
        }

        if (magasinActuel != null) {
            res.append(nbFactureMagasin).append(" factures éditées\n");
            res.append(nbLivreMagasin).append(" livres vendus\n");
            res.append("********************************************************************************\n");
        }

        res.append("Chiffre d'affaire global: ").append(totalChiffre).append("\n");
        res.append("Nombre de livres vendus: ").append(nbLivreTotal).append("\n");

        rs.close();
        stmt.close();
        return res.toString();
    }
}
