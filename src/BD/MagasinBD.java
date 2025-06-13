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
}