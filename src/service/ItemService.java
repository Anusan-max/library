/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.BorrowItemDao;
import dao.ItemDao;
import model.Item;
import model.ItemType;

/**
 *
 * @author kailainathan
 */
public class ItemService {
    
    // declare ItemDao
    private final ItemDao itemDao;
    
   //java constructure  
    public ItemService(ItemDao itemDao) {
        // create new ItemDao object and assign it to the ItemDao variable
        this.itemDao = itemDao; 
    }
    
    public String addItem(Item item) {
        return itemDao.createItem(item);
    }
    
    public Item findItemByTitle(String title) {
        // using the itemDao object and calling it's method
        return itemDao.findByTitle(title);
    }
    
    public ItemType findItemTypeById(String itemId) {
        return itemDao.findItemTypeById(itemId);
    }
    
    public void updateNoOfCopies(String itemCode,boolean itemReturn) {
        itemDao.updateNoOfCopies(itemCode,itemReturn);
    }
    

    public Item findItemById(String id) {
        return itemDao.findById(id);
    }
       
    
}
