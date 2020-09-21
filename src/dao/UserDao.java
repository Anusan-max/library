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
import model.LibraryMember;
import model.RentType;
import model.User;
import model.UserRole;
import model.MemberStatus;

/**
 *
 * @author kailainathan
 */
public class UserDao {

      private static PreparedStatement stmt = null;
      private Connection conn = null;
      
    

    public LibraryMember findById(String id) {
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
                    if(UserRole.valueOf(rs.getString("UROLE")) == UserRole.MEMBER) {
                        LibraryMember member = new LibraryMember();
                        member.setName(rs.getString("NAME"));
                        member.setContactNo(rs.getString("CONTACTNO"));
                        member.setMemberStatus(MemberStatus.valueOf(rs.getString("STATUS")));
                        member.setAddress(rs.getString("ADDRESS"));
                        member.setDob(rs.getString("DATEOFBIRTH"));
                        System.out.println("Member is " + member);
                    return member;
                    
                  }
                    
                  }
              } catch (SQLException ex) {
                  Logger.getLogger(ItemDao.class.getName()).log(Level.SEVERE, null, ex);
              }
       }
       return null;
    }
    
    
      public LibraryMember findByType(String type) {
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
                  stmt.setString(1, type);
                  
                  //executing the statement and it will return a resultset
                  ResultSet rs = stmt.executeQuery();

                  //if no results found 
                  if (!rs.next()) {
                      //no user so return
                      return null;
                  } else {
                    if(UserRole.valueOf(rs.getString("UROLE")) == UserRole.MEMBER) {
                        LibraryMember member = new LibraryMember();
                        member.setName(rs.getString("NAME"));
                        member.setContactNo(rs.getString("CONTACTNO"));
                        member.setMemberStatus(MemberStatus.valueOf(rs.getString("STATUS")));
                        member.setAddress(rs.getString("ADDRESS"));
                        member.setDob(rs.getString("DATEOFBIRTH"));
                        System.out.println("Member is " + member);
                    return member;
                    
                  }
                    
                  }
              } catch (SQLException ex) {
                  Logger.getLogger(ItemDao.class.getName()).log(Level.SEVERE, null, ex);
              }
       }
       return null;
    }
    
}
