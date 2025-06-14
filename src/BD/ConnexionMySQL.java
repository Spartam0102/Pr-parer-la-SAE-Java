package BD; 
import java.sql.*;


public class ConnexionMySQL {
	private Connection mysql=null;
	private boolean connecte=false;


	public ConnexionMySQL() throws ClassNotFoundException{
		Class.forName("org.mariadb.jdbc.Driver");

	
	}

	public void connecter() throws SQLException {

        String nomServeur="localhost";
        String nomBase="DBtrojnar";
        String nomLogin ="root"; 
        String motDePasse="xefef";
		// si tout s'est bien passé la connexion n'est plus nulle
		this.mysql=DriverManager.getConnection("jdbc:mysql://"+nomServeur+":3306/"+nomBase,nomLogin,motDePasse);
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