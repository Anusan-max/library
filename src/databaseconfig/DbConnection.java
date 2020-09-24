/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseconfig;

import static com.sun.javafx.fxml.expression.Expression.and;
import forms.LibraryForm;
import forms.MemberForm;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static javafx.beans.binding.Bindings.and;
import model.UserRole;

/**
 *
 * @author kailainathan
 */
public class DbConnection {
    
    private static String dbURL = "jdbc:derby://localhost:1527/Library;create=true";
    private static String tableName = "libuser";
    private static Connection conn = null;
    private static PreparedStatement stmt = null;
    
    public static Connection getConnection() throws SQLException {
       return DriverManager.getConnection(dbURL, "libadmin", "admin");
    }

  
}
