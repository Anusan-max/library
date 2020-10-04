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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ItemTransaction;
import model.FinancialReport;
import model.ItemType;


/**
 *
 * @author kailainathan
 */
public class ItemTransactionDao {
     private static PreparedStatement stmt = null;
     private Connection conn = null;
      
    public String addBorrowItemToDb(ItemTransaction borrowItem) {
         setConnection();
             
       if( conn != null ) {
             try {
                 stmt = conn.prepareStatement("INSERT INTO BORROWITEM(MEMBERID, ITEMID, BORROWDATE, RETURNDATE, PAID,TOTALFINE,ITEMTYPE) values(?, ?, ?, ?, ?,?,?)");
                 stmt.setString(1, borrowItem.getMemberId());
                 stmt.setString(2, borrowItem.getItemId());
                 stmt.setString(3, borrowItem.getBorrowDate());
                 stmt.setString(4, borrowItem.getReturnDate());
                 stmt.setBoolean(5, borrowItem.isPaid());
                 stmt.setInt(6,borrowItem.getTotalFine());
                 stmt.setString(7,borrowItem.getItemType().toString());
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

    public ArrayList<ItemTransaction> getBorrowedItem(ItemTransaction borrowItem) {
        setConnection();
         if( conn != null ) {
             ArrayList<ItemTransaction> borrowItems = new ArrayList<>();
              try {
                  stmt = conn.prepareStatement("select * from BORROWITEM where MEMBERID = ? AND ITEMID = ? AND RETURNDATE IS NULL",ResultSet.TYPE_SCROLL_INSENSITIVE,
                                        ResultSet.CONCUR_UPDATABLE);
                  stmt.setString(1, borrowItem.getMemberId());
                  stmt.setString(2, borrowItem.getItemId());
                  ResultSet rs = stmt.executeQuery();
                  while (rs.next()) {
                    ItemTransaction itemTransaction = new ItemTransaction();
                    itemTransaction.setItemId(borrowItem.getItemId());
                    itemTransaction.setMemberId(borrowItem.getMemberId());
                    itemTransaction.setBorrowDate(rs.getString("BORROWDATE"));
                    itemTransaction.setReturnDate(borrowItem.getReturnDate());
                    itemTransaction.setBorrowId(rs.getString("BORROWID"));
                    borrowItems.add(itemTransaction);
                  }
                  conn.close();
                  return borrowItems;
              } catch (SQLException ex) {
                  Logger.getLogger(ItemDao.class.getName()).log(Level.SEVERE, null, ex);
              }
       }
         return null;
    }
    
    public void updateBorrowedItem(ItemTransaction borrowItem) {
      setConnection();
      
    java.util.Date initDate = null;
    String parsedDate = null;
    
    
         try {
             initDate = new SimpleDateFormat("dd/MM/yyyy").parse(borrowItem.getReturnDate());
             SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
             parsedDate = formatter.format(initDate);
         } catch (ParseException ex) {
             Logger.getLogger(ItemTransactionDao.class.getName()).log(Level.SEVERE, null, ex);
         }
   
           
       Date cDate = Date.valueOf(parsedDate);
       
        if( conn != null ) {
             try {
                 stmt = conn.prepareStatement("UPDATE BORROWITEM SET RETURNDATE = ?,TOTALFINE = ? , CDATE = ? WHERE BORROWID = ?");
                 stmt.setString(1, borrowItem.getReturnDate());
                 stmt.setInt(2, borrowItem.getTotalFine());
                 stmt.setDate(3, cDate);
                 stmt.setString(4, borrowItem.getBorrowId());
                 
                 
                 stmt.executeUpdate();
                 conn.close();
              } catch (SQLException ex) {
                  Logger.getLogger(ItemDao.class.getName()).log(Level.SEVERE, null, ex);
              }
        }
    }
    
    public ArrayList<ItemType> getBorrowedItemTypesForUser(String userId) {
        setConnection();
        ArrayList<ItemType> resultString = new ArrayList<>();

        if( conn != null ) {
            try {
                stmt = conn.prepareStatement("select ITEMTYPE from BORROWITEM where MEMBERID = ? AND RETURNDATE IS NULL");
                stmt.setString(1, userId);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String a = rs.getString("ITEMTYPE");
                    if(a != null) {
                         resultString.add(ItemType.valueOf(rs.getString("ITEMTYPE")));
                    }
                   
                }
                 return resultString;
            } catch (SQLException ex) {
                Logger.getLogger(ItemDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    

    public List<FinancialReport> getAllBorrowItemsPerDate(String reportDate)  {
          setConnection();
          
         DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

         LocalDate fromDate = LocalDate.parse(reportDate,dtf);
         String toDateString = fromDate.plusMonths(1).format(dtf);
         
         Date sFromDate = Date.valueOf(reportDate);
         Date sToDate = Date.valueOf(toDateString);
        
 

          
          
        ArrayList<FinancialReport> resultList = new ArrayList<>();

        if( conn != null ) {
            try {
                stmt = conn.prepareStatement("select MEMBERID,TOTALFINE from BORROWITEM where CDATE BETWEEN ? AND ?");
                stmt.setDate(1, sFromDate);
                stmt.setDate(2, sToDate);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    FinancialReport financialReport = new FinancialReport();
                    financialReport.setMemberId(rs.getString("MEMBERID"));
                    financialReport.setTotalFine(rs.getInt("TOTALFINE"));
                    resultList.add(financialReport);
                }
                
                 return resultList;
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
