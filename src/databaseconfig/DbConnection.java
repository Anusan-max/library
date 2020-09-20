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

    
     public static String Login(String userName, String password) throws SQLException {
       conn = getConnection();
       
       if( conn != null ) {
           stmt = conn.prepareStatement("select * from libuser where USERNAME = ? AND PASSWORD = ?");
           stmt.setString(1, userName);
           stmt.setString(2, password);
           ResultSet rs = stmt.executeQuery();
           
          
           if (!rs.next()) {
            return "wrong username or password";
            } else {
                        do {
                      if(rs.getString("UROLE").equals("lib")){
                                       System.out.println(rs.getString("FIRSTNAME") + " is a librarian");
                                       // show librarian window 
                                       new LibraryForm().setVisible(true);

                                   } else{
                                       System.out.println(rs.getString("FIRSTNAME") + " is a member");
                                         new MemberForm().setVisible(true);
                                       // show memer windows
                                   }
                        } while (rs.next());
                    }
       }
           
        else {
            return "not connected to databae";
       }
        return null;
   } 
}
