/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import databaseconfig.DbConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Item;
import model.Language;
import model.RentType;

/**
 *
 * @author kailainathan
 */
public class ItemDao {
      private static PreparedStatement stmt = null;
      Connection conn = null;
    
    public void createItem(Item item)  {
        // sql store this in database 
         
          try {
              conn = DbConnection.getConnection();
          } catch (SQLException ex) {
              Logger.getLogger(ItemDao.class.getName()).log(Level.SEVERE, null, ex);
          }
       
       if( conn != null ) {
             try {
                 stmt = conn.prepareStatement("insert into Item values(?,?,?,?,?,?,?,?,?,?)");
                 stmt.setString(1, item.getCode());
                 stmt.setString(2, item.getTitle());
                 stmt.setString(3, item.getAuthor());
                 stmt.setString(4, item.getPublishedDate());
                 stmt.setString(5, item.getIsbn());
                 stmt.setString(6, item.getPublisher());
                 stmt.setString(7, item.getRentType().toString());                 
                 stmt.setInt(8, item.getNoOfCopiesToBorrow());
                 stmt.setInt(9, 0);
                 stmt.setString(10, item.getLanguage().toString());
                 
                 int i = stmt.executeUpdate();
                 System.out.println("rows added " + i );
             } catch (SQLException ex) {
                 Logger.getLogger(ItemDao.class.getName()).log(Level.SEVERE, null, ex);
             }
       }
    }
    
    public Item findByTitle(String title) {
            
          try {
              conn = DbConnection.getConnection();
          } catch (SQLException ex) {
              Logger.getLogger(ItemDao.class.getName()).log(Level.SEVERE, null, ex);
          }
        if( conn != null ) {
              try {
                  stmt = conn.prepareStatement("select * from ITEM where TITLE = ?",ResultSet.TYPE_SCROLL_INSENSITIVE,
                                        ResultSet.CONCUR_UPDATABLE);
                  stmt.setString(1, title);
                  ResultSet rs = stmt.executeQuery();

                  if (!rs.next()) {
                      return null;
                  } else {
                    Item item = new Item();
                    rs.first();
                    item.setCode(rs.getString("ID"));
                    item.setTitle(rs.getString("TITLE"));
                    item.setAuthor(rs.getString("AUTHOR"));
                    item.setPublishedDate(rs.getString("PUBLISHEDDATE"));
                    item.setIsbn(rs.getString("ISBN"));
                    item.setPublisher(rs.getString("PUBLISHER"));
                    item.setRentType(RentType.valueOf(rs.getString("TYPE")));
                    item.setNoOfCopiesToBorrow(rs.getInt("NOOFCOPIESAVAILABLE"));
                    item.setNoOfCopiesCurrentlyBorrowed(rs.getInt("NOOFCOPIESBORROWED"));
                    item.setLanguage(Language.valueOf(rs.getString("LANGUAGE")));
                    return item;
                  }
              } catch (SQLException ex) {
                  Logger.getLogger(ItemDao.class.getName()).log(Level.SEVERE, null, ex);
              }
       }
       return null;
    }
}