package BD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StatistiqueBD {
    private ConnexionMySQL laConnexion;

    public StatistiqueBD(ConnexionMySQL laConnexion) {
        this.laConnexion = laConnexion;
    }
    
    public ConnexionMySQL getLaConnexion() {
        return this.laConnexion;
    }

    public List<List<String>> premier(String id) throws SQLException {
        try (PreparedStatement ps = laConnexion.prepareStatement(
                "SELECT nommag as Magasin, YEAR(datecom) as annee, COUNT(numcom) as qte FROM MAGASIN NATURAL JOIN COMMANDE WHERE idmag = ? GROUP BY nommag, annee ORDER BY annee")) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            List<List<String>> tableau = new ArrayList<>();

            while (rs.next()) {
                List<String> ligne = new ArrayList<>();

                ligne.add(rs.getString("Magasin"));
                ligne.add(rs.getString("annee"));
                ligne.add(rs.getString("qte"));
                tableau.add(ligne);
            }
            return tableau;
        }
    }

    public List<List<String>> deuxieme(String id) throws SQLException {
        try (PreparedStatement ps = laConnexion.prepareStatement(
                "SELECT nommag as Magasin, YEAR(datecom) as annee, SUM(prixvente*qte) as Montant FROM MAGASIN NATURAL JOIN COMMANDE NATURAL JOIN DETAILCOMMANDE WHERE idmag = ? GROUP BY nommag, annee ORDER BY annee")) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            List<List<String>> tableau = new ArrayList<>();

            while (rs.next()) {
                List<String> ligne = new ArrayList<>();

                ligne.add(rs.getString("Magasin"));
                ligne.add(rs.getString("annee"));
                ligne.add(rs.getString("Montant"));
                tableau.add(ligne);
            }
            return tableau;
        }
    }

    public List<List<String>> troisieme(String annee) throws SQLException {
        try (PreparedStatement ps = laConnexion.prepareStatement(
                "SELECT MONTH(datecom) as mois, nommag as Magasin, SUM(prixvente) as CA " +
                        "FROM MAGASIN NATURAL JOIN COMMANDE NATURAL JOIN DETAILCOMMANDE " +
                        "WHERE YEAR(datecom) = ? " +
                        "GROUP BY nommag, mois ORDER BY mois, nommag")) {
            ps.setString(1, annee);
            ResultSet rs = ps.executeQuery();

            List<List<String>> tableau = new ArrayList<>();

            while (rs.next()) {
                List<String> ligne = new ArrayList<>();

                ligne.add(rs.getString("mois"));
                ligne.add(rs.getString("Magasin"));
                ligne.add(rs.getString("CA"));
                tableau.add(ligne);
            }
            return tableau;
        }
    }

}