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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Transaction;
import model.TransactionType;

/**
 *
 * @author kailainathan
 */
public class TransactionDao {
    
       private static PreparedStatement stmt = null;
      Connection conn = null;

    public void addTransaction(Transaction transaction) {
        setConnection();
       
       if( conn != null ) {
             try {
                 stmt = conn.prepareStatement("insert into LIBTRANSACTION values(?,?)");
                 stmt.setString(1, transaction.getLocalDate());
                 stmt.setString(2, transaction.getTransactionType().toString());
                 int i = stmt.executeUpdate();
                 System.out.println("rows added " + i );
                 conn.close();
             } catch (SQLException ex) {
                 Logger.getLogger(ItemDao.class.getName()).log(Level.SEVERE, null, ex);
             }
       }
    }

    public ArrayList<Transaction> getAllTransaction() {
         
        setConnection();
        ArrayList<Transaction> result = new ArrayList<>();

        if( conn != null ) {
            try {
                stmt = conn.prepareStatement("select * from LIBTRANSACTION");
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Transaction transaction = new Transaction();
                    transaction.setLocalDate(rs.getString("TRANSACTIONDATE"));
                    transaction.setTransactionType(TransactionType.valueOf(rs.getString("TRANSACTIONTYPE")));
                    result.add(transaction);
                }
                 return result;
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
