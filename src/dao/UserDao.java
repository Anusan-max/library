/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import databaseconfig.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Item;
import model.Language;
import model.RentType;
import model.User;
import model.UserRole;

/**
 *
 * @author kailainathan
 */
public class UserDao {

      private static PreparedStatement stmt = null;
      private Connection conn = null;
    

    public User findById(String id) {
        try {
              conn = DbConnection.getConnection();
          } catch (SQLException ex) {
              Logger.getLogger(ItemDao.class.getName()).log(Level.SEVERE, null, ex);
          }
        if( conn != null ) {
              try {
                  //preparing the statement
                  stmt = conn.prepareStatement("select * from LIBUSER where ID = ?",ResultSet.TYPE_SCROLL_INSENSITIVE,
                                        ResultSet.CONCUR_UPDATABLE);
                  
                  //setting the value for the question mark
                  stmt.setString(1, id);
                  
                  //executing the statement and it will return a resultset
                  ResultSet rs = stmt.executeQuery();

                  //if no results found 
                  if (!rs.next()) {
                      //no user so return
                      return null;
                  } else {
                    User user = new User();
                    rs.first();
                    user.setUserId(rs.getString("ID"));
                    user.setFirstName(rs.getString("FIRSTNAME"));
                    user.setUserName(rs.getString("USERNAME"));
                    user.setUserRole(UserRole.valueOf(rs.getString("UROLE")));
                    user.setDob(rs.getString("DATEOFBIRTH"));
                    
                    return user;
                    
                  }
              } catch (SQLException ex) {
                  Logger.getLogger(ItemDao.class.getName()).log(Level.SEVERE, null, ex);
              }
       }
       return null;
    }
    
}
