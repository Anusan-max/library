/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.ItemDao;
import model.Item;

/**
 *
 * @author kailainathan
 */
public class ItemService {
    
    // declare ItemDao
    public final ItemDao itemDao;
    
   //java constructure  
    public ItemService() {
        // create new ItemDao object and assign it to the ItemDao variable
        itemDao = new ItemDao(); 
    }
    
    public void addItem(Item item) {
        itemDao.createItem(item);
    }
    
    public Item findItemByTitle(String title) {
        // using the itemDao object and calling it's method
        return itemDao.findByTitle(title);
    }
    
    public void IncreaseBorrowItemNumberAndDecreaseAvailableItemNumber(String itemCode) {
        itemDao.IncreaseBorrowItemNumberAndDecreaseAvailableItemNumber(itemCode);
    }
       
    
}
