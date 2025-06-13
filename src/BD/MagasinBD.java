package BD; 
import Java.*; 
import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class MagasinBD {
	ConnexionMySQL laConnexion;
	Statement st;
	public MagasinBD(ConnexionMySQL laConnexion){
		this.laConnexion=laConnexion;
	}

	public int maxNumMagasin() throws SQLException{
		int maxNum=0;
		this.st=this.laConnexion.createStatement();
		ResultSet resultat=st.executeQuery("SELECT MAX(idmag) maxn FROM MAGASIN;");
		if(resultat.next()){
			maxNum=resultat.getInt("maxn");

		}
		return maxNum;

	}


	public int insererMagasin( Magasin m) throws  SQLException{
		PreparedStatement ps = this.laConnexion.prepareStatement("insert into MAGASIN values (?,?,?)"); 
		
		int numJoueur = this.maxNumMagasin()+1; 
		ps.setInt(1,numJoueur);
		ps.setString(2, m.getNom());
		ps.setString(3, m.getVille());
		ps.executeUpdate();	
 
		return numJoueur; 

	}

	
	public void effacerMagasin(int num) throws SQLException {
    PreparedStatement ps = this.laConnexion.prepareStatement("DELETE FROM MAGASIN WHERE idmag = ?");
    ps.setInt(1, num);
    ps.executeUpdate();

 }


	public ArrayList<Magasin> listeDesMagasins() throws SQLException{
		try(PreparedStatement ps = laConnexion.prepareStatement("select * from MAGASIN;")){
			ResultSet rs = ps.executeQuery();
			ArrayList<Magasin> entreprise=new ArrayList<>();
			while (rs.next()) {
				Magasin magasin = new Magasin(rs.getString("nommag"),rs.getString("villemag"),rs.getInt("idmag"));
				entreprise.add(magasin);		
			}
			return entreprise;
		}
	}
	
	public Map<Livre, Integer> listeLivreUnMagasin(long id) throws SQLException {
    String requete = "SELECT DISTINCT isbn, titre, datepubli, prix, nbpages, qte FROM LIVRE NATURAL JOIN POSSEDER WHERE idmag = ?";
    
    try (PreparedStatement ps = laConnexion.prepareStatement(requete)){
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();
        Map<Livre, Integer> livresUnMagasin = new HashMap<>();

        while (rs.next()){
            int quantite = rs.getInt("qte");
			if (!(quantite == 0)){
				Livre livre = new Livre(rs.getLong("isbn"), 
										rs.getString("titre"), 
										rs.getString("datepubli"), 
										rs.getDouble("prix"),
										rs.getInt("nbpages"),
										null,
										null,
										null);
            livresUnMagasin.put(livre, quantite);
			} 
        }

        return livresUnMagasin;
    	}
	}

	public void modifierStockEtPrixLivre(long isbn, int idMagasin, int nouvelleQuantite, double nouveauPrix) throws SQLException {
    // Mise à jour de la quantité dans la table POSSEDER
    String updateQte = "UPDATE POSSEDER SET qte = ? WHERE isbn = ? AND idmag = ?";
    try (PreparedStatement psQte = laConnexion.prepareStatement(updateQte)) {
        psQte.setInt(1, nouvelleQuantite);
        psQte.setLong(2, isbn);
        psQte.setInt(3, idMagasin);
        int rowsUpdated = psQte.executeUpdate();
        if (rowsUpdated == 0) {
            throw new SQLException("Aucune ligne modifiée dans POSSEDER : l'association livre-magasin est introuvable.");
        }
    }

    // Mise à jour du prix dans la table LIVRE (si le prix change globalement)
    String updatePrix = "UPDATE LIVRE SET prix = ? WHERE isbn = ?";
    try (PreparedStatement psPrix = laConnexion.prepareStatement(updatePrix)) {
        psPrix.setDouble(1, nouveauPrix);
        psPrix.setLong(2, isbn);
        psPrix.executeUpdate();
    }
}

}