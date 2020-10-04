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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.InventoryReport;
import model.Item;
import model.ItemType;
import model.Language;
import model.RentType;

/**
 *
 * @author kailainathan
 */
public class ItemDao {
      private static PreparedStatement stmt = null;
      private Connection conn = null;
    
    public void updateNoOfCopies(String itemCode,boolean itemReturn) {
        setConnection();
        
        if( conn != null ) {
             try {
                  stmt = conn.prepareStatement("select NOOFCOPIESAVAILABLE,NOOFCOPIESBORROWED from ITEM where ID = ?",ResultSet.TYPE_SCROLL_INSENSITIVE,
                                        ResultSet.CONCUR_UPDATABLE);
                  stmt.setString(1, itemCode);
                  ResultSet rs = stmt.executeQuery();

                   if (!rs.next()) {
                  } else {
                    rs.first();
                    int noOfCopiesAvaliable = rs.getInt("NOOFCOPIESAVAILABLE");
                    int noOfCopiesBorrowed = rs.getInt("NOOFCOPIESBORROWED");
                     if(itemReturn) {
                        noOfCopiesAvaliable++;
                        noOfCopiesBorrowed--;
                    } else  {
                        noOfCopiesAvaliable--;
                        noOfCopiesBorrowed++;
                    }
                    
                 stmt = conn.prepareStatement("UPDATE Item SET NOOFCOPIESAVAILABLE = ?, NOOFCOPIESBORROWED = ? WHERE ID = ?");
                 stmt.setInt(1, noOfCopiesAvaliable);
                 stmt.setInt(2, noOfCopiesBorrowed);
                 stmt.setString(3, itemCode);
                 
                 int i = stmt.executeUpdate();
                    conn.close();
                  }
              } catch (SQLException ex) {
                  Logger.getLogger(ItemDao.class.getName()).log(Level.SEVERE, null, ex);
              }
        }
        
        
        
      }
    
    public String createItem(Item item)  {
        // sql store this in database 
         
         setConnection();
       
       if( conn != null ) {
             try {
                 stmt = conn.prepareStatement("insert into Item values(?,?,?,?,?,?,?,?,?,?,?)");
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
                 stmt.setString(11, item.getItemType().toString());
                 
                 int i = stmt.executeUpdate();
                 conn.close();
                 if(i == 1) {
                     return "Item Added";
                 } 
                 
             } catch (SQLException ex) {
                 Logger.getLogger(ItemDao.class.getName()).log(Level.SEVERE, null, ex);
                 return "Item already exist";
             }
       }
       return null;
    }
    
    public Item findByTitle(String title) {
            
         setConnection();
          
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
                    item.setItemType(ItemType.valueOf(rs.getString("ITEMTYPE")));
                    conn.close();
                    return item;
                  }
              } catch (SQLException ex) {
                  Logger.getLogger(ItemDao.class.getName()).log(Level.SEVERE, null, ex);
              }
       }
       return null;
    }
    
        public Item findById(String id) {
            
         setConnection();
          
        if( conn != null ) {
              try {
                  stmt = conn.prepareStatement("select * from ITEM where ID = ?",ResultSet.TYPE_SCROLL_INSENSITIVE,
                                        ResultSet.CONCUR_UPDATABLE);
                  stmt.setString(1, id);
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
                    item.setItemType(ItemType.valueOf(rs.getString("ITEMTYPE")));

                    conn.close();
                    return item;
                  }
              } catch (SQLException ex) {
                  Logger.getLogger(ItemDao.class.getName()).log(Level.SEVERE, null, ex);
              }
       }
       return null;
    }
    
    public ItemType findItemTypeById(String id) {
        setConnection();
        if( conn != null ) {
            try {
                stmt = conn.prepareStatement("select ITEMTYPE from ITEM where ID = ?",ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
                stmt.setString(1, id);
                ResultSet rs = stmt.executeQuery();

                if (!rs.next()) {
                    return null;
                } else {

                    rs.first();
                    ItemType itemType = ItemType.valueOf(rs.getString("ITEMTYPE"));
                    conn.close();
                    return itemType;
                }
            } catch (SQLException ex) {
                Logger.getLogger(ItemDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
     public ArrayList<Item> findAllItems() {
        setConnection();
        ArrayList<Item> resultString = new ArrayList<>();

        if( conn != null ) {
            try {
                stmt = conn.prepareStatement("select * from ITEM");
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Item item = new Item();
                    item.setCode(rs.getString("ID"));
                    item.setTitle(rs.getString("TITLE"));
                    item.setNoOfCopiesToBorrow(Integer.parseInt(rs.getString("NOOFCOPIESAVAILABLE")));
                    resultString.add(item);
                }
                 return resultString;
            } catch (SQLException ex) {
                Logger.getLogger(ItemDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
     
    public ArrayList<InventoryReport> findAllItemsForReport() {
        setConnection();
        ArrayList<InventoryReport> result = new ArrayList<>();

        if( conn != null ) {
            try {
                stmt = conn.prepareStatement("select * from ITEM");
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    InventoryReport inventoryReport = new InventoryReport();
                    inventoryReport.setItemCode(rs.getString("ID"));
                    inventoryReport.setItemTitle(rs.getString("TITLE"));
                    inventoryReport.setNoOfCopies(Integer.parseInt(rs.getString("NOOFCOPIESAVAILABLE")));
                    result.add(inventoryReport);
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
