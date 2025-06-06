package BD; 

import java.sql.*;

public class MySQL implements AutoCloseable {
    private Connection connection;

    public MySQL(String user, String password, String host, String database) throws SQLException {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Pilote MariaDB introuvable.");
        }

        String url = "jdbc:mariadb://" + host + "/" + database;
        this.connection = DriverManager.getConnection(url, user, password);
        System.out.println("Connexion réussie !");
    }

    public Connection getConnection() {
        return this.connection;
    }

    @Override
    public void close() throws SQLException {
        if (this.connection != null) {
            this.connection.close();
            System.out.println("Connexion fermée.");
        }
    }
}
