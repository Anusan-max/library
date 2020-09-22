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
import model.BorrowItem;
import model.Item;
import model.Language;
import model.RentType;

/**
 *
 * @author kailainathan
 */
public class BorrowItemDao {
     private static PreparedStatement stmt = null;
     Connection conn = null;
      
    public String addBorrowItemToDb(BorrowItem borrowItem){
         setConnection();
       
       if( conn != null ) {
             try {
                 stmt = conn.prepareStatement("INSERT INTO BORROWITEM(MEMBERID, ITEMID, BORROWDATE, RETURNDATE, PAID) values(?, ?, ?, ?, ?,?)");
                 stmt.setString(1, borrowItem.getMemberId());
                 stmt.setString(2, borrowItem.getItemId());
                 stmt.setString(3, borrowItem.getBorrowDate());
                 stmt.setString(4, borrowItem.getReturnDate());
                 stmt.setBoolean(5, borrowItem.isPaid());
                 stmt.setInt(6,borrowItem.getTotalFine());
                 int i = stmt.executeUpdate();
                 if(i == 1 ) {
                     return "Item Borrowed successfully";
                 }
                 
                 conn.close();
             } catch (SQLException ex) {
                 Logger.getLogger(ItemDao.class.getName()).log(Level.SEVERE, null, ex);
             }
       }
        return null;
    }

    public BorrowItem getBorrowedItem(BorrowItem borrowItem) {
        setConnection();
         if( conn != null ) {
              try {
                  stmt = conn.prepareStatement("select * from BORROWITEM where MEMBERID = ? AND ITEMID = ?",ResultSet.TYPE_SCROLL_INSENSITIVE,
                                        ResultSet.CONCUR_UPDATABLE);
                  stmt.setString(1, borrowItem.getMemberId());
                  stmt.setString(2, borrowItem.getItemId());
                  ResultSet rs = stmt.executeQuery();
                  if (!rs.next()) {
                  } else {
                    rs.first();
                    borrowItem.setBorrowDate(rs.getString("BORROWDATE"));
                    borrowItem.setBorrowId(rs.getString("BORROWID"));
                    conn.close();
                    return borrowItem;
                  }
              } catch (SQLException ex) {
                  Logger.getLogger(ItemDao.class.getName()).log(Level.SEVERE, null, ex);
              }
       }
         return null;
    }
    
    public String updateBorrowedItem(BorrowItem borrowItem) {
      setConnection();
        
        if( conn != null ) {
             try {
      
                    
                 stmt = conn.prepareStatement("UPDATE BORROWITEM SET RETURNDATE = ?,TOTALFINE = ? WHERE MEMBERID = ? AND ITEMID = ? ");
                 stmt.setString(1, borrowItem.getReturnDate());
                 stmt.setInt(2, borrowItem.getTotalFine());
                 stmt.setString(3, borrowItem.getMemberId());
                 stmt.setString(4, borrowItem.getItemId());
                 
                 int i = stmt.executeUpdate();
                 conn.close();
                 return "Item Returned successfully";
              } catch (SQLException ex) {
                  Logger.getLogger(ItemDao.class.getName()).log(Level.SEVERE, null, ex);
              }
        }
        return null;
    }
    
      private void setConnection() {
         try {
              conn = DbConnection.getConnection();
          } catch (SQLException ex) {
              Logger.getLogger(ItemDao.class.getName()).log(Level.SEVERE, null, ex);
          }
    }
}
