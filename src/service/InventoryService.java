/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.ItemDao;
import java.util.ArrayList;
import java.util.List;
import model.Item;

/**
 *
 * @author kailainathan
 */
public class InventoryService implements IInventoryService{
        //TODO 2
    ItemDao itemDao;

    public InventoryService(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    @Override
    public ArrayList<Item> getInventoryReport() {
        ArrayList<Item> itemList = new ArrayList();
        for(Item item : itemDao.findAllItems()) {
            itemList.add(item);
            System.out.println("I " + item.getCode());
            System.out.println("I " + item.getTitle());
            System.out.println("I " + item.getNoOfCopiesToBorrow());
        }
        return itemList;
    }
    
    
}
