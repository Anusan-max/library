/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.ItemDao;
import java.util.ArrayList;
import java.util.List;
import model.InventoryReport;
import model.Item;

/**
 *
 * @author kailainathan
 */
public class InventoryService implements IInventoryService{
        //TODO 2
    private ItemDao itemDao;

    public InventoryService(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    @Override
    public ArrayList<InventoryReport> getInventoryReport() {
        ArrayList<InventoryReport> itemList = new ArrayList();
        for(InventoryReport inventoryReport : itemDao.findAllItemsForReport()) {
            itemList.add(inventoryReport);
        }
        return itemList;
    }
    
    
}
