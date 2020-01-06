package webtextfilescompressor.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Piotr Matras
 * @version 1.0
 */
public class DatabaseConnector {
    
    private final String databaseUsername = "dev";
    private final String databasePassword = "dev";
    private final String databaseURL = "jdbc:derby://localhost:1527/OperationsHistory";
    private final String tableName = "OperationsHistory";
    
    private static Connection connection = null;
    
    public DatabaseConnector() {
         if(connection == null) {
             connectWithDatabase();         
         }        
    }
    
    private boolean loadJDBCDriver() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver"); 
            return true;
        } catch(ClassNotFoundException e) {
            System.err.println("Failed to load JDBC driver, reason: " + e.getMessage());
        }
        
        return false;               
    }
    
    private void connectWithDatabase() {
        if(!loadJDBCDriver()) {
            System.err.println("Failed to load JDBC driver!");
            return;
        }
        
        try {
            connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
        } catch(SQLException e) {
            System.err.println("Failed to get connection with database, reason: " + e.getMessage());
        }   
    }
    
    public boolean insertData(final String mode, final String inputFile, final String outputFile) {
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement("INSERT INTO " + this.tableName + " VALUES (?, ?, ?)");
            preparedStatement.setString(1, mode);
            preparedStatement.setString(2, inputFile);
            preparedStatement.setString(3, outputFile);
            preparedStatement.executeUpdate();
            
            System.out.println("Data inserted!");            
            return true;
        } catch(SQLException e) {
            System.err.println("Failed to insert data into database, reason: " + e.getMessage());
        }
        
        return false;
    }
    
}