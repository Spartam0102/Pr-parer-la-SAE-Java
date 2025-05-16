import java.sql.*;

public class ConnexionMySQL {
	private Connection mysql=null;
	private boolean connecte=false;
	public ConnexionMySQL(){

	
	}

	public void connecter() throws SQLException {
		// si tout s'est bien passé la connexion n'est plus nulle
        String nomServeur = "DBtrojnar";
        String nomBase = "servinfo-maria";
        String nomLogin = "trojnar";
        String motDePasse = "trojnar";
		this.mysql=DriverManager.getConnection("jdbc:mysql://"+nomServeur+":3306/"+nomBase,nomLogin,motDePasse);
        System.out.println(this.mysql);
		this.connecte=this.mysql!=null;
	}
	public void close() throws SQLException {
		this.mysql.close();
		// fermer la connexion
		this.connecte=false;
	}

    	public boolean isConnecte() { return this.connecte;}
	public Statement createStatement() throws SQLException {
		return this.mysql.createStatement();
	}

	public PreparedStatement prepareStatement(String requete) throws SQLException{
		return this.mysql.prepareStatement(requete);
	}
	
}
