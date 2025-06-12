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
            long isbn = 0L;
            String isbnStr = rs.getString("isbn");
            if (isbnStr != null) isbn = Long.parseLong(isbnStr);

            String titre = rs.getString("titre");

            String datepubli = rs.getString("datepubli");

            double prix = 0.0;
            String prixStr = rs.getString("prix");
            if (prixStr != null) prix = Double.parseDouble(prixStr);

            int nbpages = 0;
            String nbpagesStr = rs.getString("nbpages");
            if (nbpagesStr != null) nbpages = Integer.parseInt(nbpagesStr);

            int quantite = 0;
            String qteStr = rs.getString("qte");
            if (qteStr != null) quantite = Integer.parseInt(qteStr);

			if (!(quantite == 0)){
				Livre livre = new Livre(isbn, titre, datepubli, prix, nbpages, null, null, null);
            livresUnMagasin.put(livre, quantite);
			} 
        }

        return livresUnMagasin;
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