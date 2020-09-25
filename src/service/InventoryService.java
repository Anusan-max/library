/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.ItemDao;
import java.util.List;
import model.Item;

/**
 *
 * @author kailainathan
 */
public class InventoryService {
        //TODO 2
    ItemDao itemDao;

    public InventoryService(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    public List<Item> getInventoryReport() {
        for(Item item : itemDao.findAllItems()) {
            System.out.println("I " + item.getCode());
            System.out.println("I " + item.getTitle());
            System.out.println("I " + item.getNoOfCopiesToBorrow());
        }
        return null;
    }
}
