/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import databaseconfig.DbConnection;
import static databaseconfig.DbConnection.getConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Item;
import model.Language;
import model.LibraryMember;
import model.RentType;
import model.User;
import model.UserRole;
import model.MemberStatus;
import model.MemberType;

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
                        
                    return member;
                    
                  }
                    conn.close();
                    
                  }
              } catch (SQLException ex) {
                  Logger.getLogger(ItemDao.class.getName()).log(Level.SEVERE, null, ex);
              }
       }
       return null;
    }
    
    
      public ArrayList<LibraryMember> findByType(String type) {
          ArrayList memberList = new ArrayList();
        try {
              conn = DbConnection.getConnection();
          } catch (SQLException ex) {
              Logger.getLogger(ItemDao.class.getName()).log(Level.SEVERE, null, ex);
          }
        if( conn != null ) {
              try {
                  //preparing the statement
                  stmt = conn.prepareStatement("select * from LIBUSER",ResultSet.TYPE_SCROLL_INSENSITIVE,
                                        ResultSet.CONCUR_UPDATABLE);
                
                  //executing the statement and it will return a resultset
                  ResultSet rs = stmt.executeQuery();

                  //if no results found 
                  while (rs.next()) { 
                      if(UserRole.valueOf(rs.getString("UROLE")) == UserRole.MEMBER) {
                        LibraryMember member = new LibraryMember();
                        member.setName(rs.getString("NAME"));
                        member.setContactNo(rs.getString("CONTACTNO"));
                        member.setMemberStatus(MemberStatus.valueOf(rs.getString("STATUS")));
                        member.setAddress(rs.getString("ADDRESS"));
                        member.setDob(rs.getString("DATEOFBIRTH"));
                        System.out.println("member.getMemberType() " + member.getMemberType());
                        System.out.println("type paras" + type);
                        System.out.println("thisis " + (member.getMemberType().toString() == type));
                        if(member.getMemberType() == MemberType.valueOf(type)) {
                             System.out.println(" inside the if  " + member.getMemberType());
                             memberList.add(member);
                        }
                    }
                  }
                  
                  conn.close();
                  return memberList;
                  
               
              } catch (SQLException ex) {
                  Logger.getLogger(ItemDao.class.getName()).log(Level.SEVERE, null, ex);
              }
             
       }
       
       return null;
    }
    
      public String login(String userName, String password) throws SQLException {
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
                      if(UserRole.valueOf(rs.getString("UROLE")) == UserRole.LIBRARIAN){
                                       // show librarian window 
                                      // new LibraryForm().setVisible(true);
                                      return UserRole.LIBRARIAN.toString();
                                   } else{
                                        // new MemberForm().setVisible(true);
                                       // show memer windows
                                       return UserRole.MEMBER.toString();
                                   }
                        } while (rs.next());
                    }
       }
           
        else {
            return "not connected to databae";
       }
   } 
}


