package BD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import Java.Administrateur;
import Java.Client;

public class AdministrateurBD {
    private ConnexionMySQL laConnexion;

    public AdministrateurBD(ConnexionMySQL laConnexion) {
        this.laConnexion = laConnexion;
    }

    public Map<String, String> recupererIdEtMotDePasse(int idAdmin) throws SQLException {
        Map<String, String> result = new HashMap<>();

        String sql = "SELECT idAdmin, mdpA FROM ADMINISTRATEUR WHERE idAdmin = ?";
        try (PreparedStatement ps = laConnexion.prepareStatement(sql)) {
            ps.setInt(1, idAdmin);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    result.put("idAdmin", String.valueOf(rs.getInt("idAdmin")));
                    result.put("mdpA", rs.getString("mdpA"));
                }
            }
        }

        return result;
    }

    public boolean verifierConnexion(int idAdmin, String mdp) throws SQLException {
    String sql = "SELECT mdpA FROM ADMINISTRATEUR WHERE idadmin = ?";
    PreparedStatement ps = laConnexion.prepareStatement(sql);
    ps.setInt(1, idAdmin);
    ResultSet rs = ps.executeQuery();

    boolean isValid = false;
    if (rs.next()) {
        String mdpBD = rs.getString("mdpA");
        isValid = mdpBD != null && mdpBD.equals(mdp);
    }

    rs.close();
    ps.close();

    return isValid;
}

public void creerAdmin(Administrateur admin) throws SQLException {
    String sql = "INSERT INTO ADMINISTRATEUR (idAdmin, nomAdmin,prenomAdmin, mdpA) " +
                 "VALUES (?, ?, ?, ?)";
    PreparedStatement ps = laConnexion.prepareStatement(sql);

    ps.setString(1, admin.getNom());
    ps.setString(2, admin.getPrenom());
    ps.setString(6, admin.getMotDePasseAdmin());

    ps.executeUpdate();
    ps.close();
}

}
