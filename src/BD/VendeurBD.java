package BD; 
import Java.*; 
import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

public class VendeurBD {
	ConnexionMySQL laConnexion;
	Statement st;
	public VendeurBD(ConnexionMySQL laConnexion){
		this.laConnexion=laConnexion;
	}

	int maxMagasin() throws SQLException{
		int maxNum=0;
		this.st=this.laConnexion.createStatement();
		ResultSet resultat=st.executeQuery("SELECT MAX(idmag) maxn FROM MAGASIN;");
		if(resultat.next()){
			maxNum=resultat.getInt("maxn");

		}
		return maxNum;

	}

	public void creerVendeur(Vendeur vendeur) throws SQLException {
    int nextId = getNextIdVendeur(); // Génére l'ID manuellement
    String sql = "INSERT INTO VENDEUR (idVen, nomVen, prenomVen, idmag) VALUES (?, ?, ?, ?)";
    try (PreparedStatement ps = laConnexion.prepareStatement(sql)) {
        ps.setInt(1, nextId);
        ps.setString(2, vendeur.getNom());
        ps.setString(3, vendeur.getPrenom());
        if (vendeur.getMagasin() != null) {
            ps.setInt(4, vendeur.getMagasin().getIdMagasin());
        } else {
            ps.setNull(4, Types.INTEGER);
        }
        ps.executeUpdate();
    }
}


	private int getNextIdVendeur() throws SQLException {
    String sql = "SELECT MAX(idVen) AS maxId FROM VENDEUR";
    try (PreparedStatement ps = laConnexion.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
            return rs.getInt("maxId") + 1;
        }
    }
    return 1; // Si aucun vendeur n’existe encore
}


	

public Vendeur recupererVendeur(int id) throws SQLException {
    String sql = "SELECT * FROM VENDEUR WHERE idVen = ?";
    try (PreparedStatement ps = laConnexion.prepareStatement(sql)) {
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int idMagasin = rs.getInt("idmag");  

            Magasin magasin = null;
            if (idMagasin > 0) {
                magasin = new Magasin(null, null, idMagasin);  
            }

            return new Vendeur(
                rs.getString("nomVen"),
                rs.getString("prenomVen"),
                null,
                id,
                magasin
            );
        }
    }
    return null;
}


	
	ArrayList<Magasin> listeDesMagasins() throws SQLException{
		try(PreparedStatement ps = laConnexion.prepareStatement("select * from MAGASIN;")){
			ResultSet rs = ps.executeQuery();
			ArrayList<Magasin> entreprise=new ArrayList<>();
			while (rs.next()) {
				entreprise.add(new Magasin(rs.getString("nommag"),rs.getString("villemag"),rs.getInt("idmag")));				
			}
			return entreprise;
		}
	}
	
	String rapportMessage() throws SQLException{
		return "rapportMessage A faire";
	}
	
	String rapportMessageComplet() throws SQLException{
		return "rapportMessageComplet A faire";	
	}

	ArrayList<Map.Entry<String, Integer>> nbMsgParJour() throws SQLException{
		// Pour créer une valeur pouvant être ajoutée à l'ArrayList<Map.Entry<String, Integer>>
		// faire un new AbstractMap.SimpleEntry<>("coucou",45)
		throw new SQLException("méthode nbMsgParJour à implémenter");
	}
	ArrayList<Map.Entry<String, Integer>> nbMain() throws SQLException{
		// Pour créer une valeur pouvant être ajoutée à l'ArrayList<Map.Entry<String, Integer>>
		// faire un new AbstractMap.SimpleEntry<>("coucou",45)
		throw new SQLException("méthode nbMain à implémenter");
	}	
}