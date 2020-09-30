/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.ItemTransactionDao;
import dao.ItemDao;
import model.Item;
import model.ItemType;

/**
 *
 * @author kailainathan
 */
public class ItemService implements IItemService{
    
    // declare ItemDao
    private final ItemDao itemDao;
    
   //java constructure  
    public ItemService(ItemDao itemDao) {
        // create new ItemDao object and assign it to the ItemDao variable
        this.itemDao = itemDao; 
    }
    
    @Override
    public String addItem(Item item) {
        return itemDao.createItem(item);
    }
    
    @Override
    public Item findItemByTitle(String title) {
        // using the itemDao object and calling it's method
        return itemDao.findByTitle(title);
    }
    
    @Override
    public ItemType findItemTypeById(String itemId) {
        return itemDao.findItemTypeById(itemId);
    }
    
    @Override
    public void updateNoOfCopies(String itemCode,boolean itemReturn) {
        itemDao.updateNoOfCopies(itemCode,itemReturn);
    }
    

    @Override
    public Item findItemById(String id) {
        return itemDao.findById(id);
    }
       
    
}
