/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import model.Item;
import model.ItemType;

/**
 *
 * @author kailainathan
 */
public interface IItemService {
     public String addItem(Item item);
     public Item findItemByTitle(String title);
      public ItemType findItemTypeById(String itemId);
       public void updateNoOfCopies(String itemCode,boolean itemReturn);
        public Item findItemById(String id);
}
