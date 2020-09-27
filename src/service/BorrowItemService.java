/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.BorrowItemDao;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import model.BorrowItem;
import model.ItemType;

/**
 *
 * @author kailainathan
 */
public class BorrowItemService {
    
      // declare ItemDao
    public final BorrowItemDao borrowItemDao;
    
   //java constructure  
    public BorrowItemService(BorrowItemDao borrowItemDao) {
        // create new ItemDao object and assign it to the ItemDao variable
        this.borrowItemDao = borrowItemDao; 
    }
    
    public String borrowItem(BorrowItem borrowItem) {
         ArrayList<ItemType> listOfItems = getBorrowedItemTypesForUser(borrowItem.getMemberId());
         System.out.println("list size is " + listOfItems.size());
         if(checkAvaliablity(getItemCountMap(listOfItems),borrowItem.getItemType())) {
                return borrowItemDao.addBorrowItemToDb(borrowItem);
         }else {
             System.out.println("User can't borrow it ");
            return "user can not borrow item";
        }
     
    }
    
    public boolean calculateFineAndReturnItem(BorrowItem borrowItem) {
       boolean fileApplied = false;
       BorrowItem borrowItemWithFullDetail = getFullDetailsForBorrowItem(borrowItem);
       int daysForFine =  numberOfDaysForFine(borrowItemWithFullDetail.getBorrowDate(),borrowItemWithFullDetail.getReturnDate());
       fileApplied = applyFine(daysForFine,borrowItemWithFullDetail);
       borrowItemDao.updateBorrowedItem(borrowItem);
       return fileApplied;
       
    }
    
     public boolean checkAvaliablity(HashMap<ItemType, Integer> itemCount, ItemType itemType) {
        boolean a =  isBookAllowed(itemCount,itemType) && isMagazineAllowed(itemCount,itemType) && typeAllowedToBorrow(itemType);
        System.out.println(a);
        return a;
    }
    
    public ArrayList<ItemType> getBorrowedItemTypesForUser(String userId) {
    return  borrowItemDao.getBorrowedItemTypesForUser(userId);
    }
       
     private boolean typeAllowedToBorrow(ItemType itemType) {
        if(itemType == ItemType.NEWSPAPER || itemType == ItemType.JOURNAL) {
            return false;
        }
        return true;
    }
     
     
    private HashMap<ItemType,Integer> getItemCountMap(ArrayList<ItemType> listOfitems) {
        Set<ItemType> unique = new HashSet<>(listOfitems);
        HashMap<ItemType,Integer> userItemMap = new HashMap<>();

        for (ItemType key : unique) {
            userItemMap.put(key, Collections.frequency(listOfitems, key));
        }
        return userItemMap;
    }
    
     private boolean isBookAllowed(HashMap<ItemType, Integer> itemCountMap, ItemType itemType) {
         System.out.println("item count map" + itemCountMap);
        if(itemCountMap.containsKey(ItemType.BOOKS) && itemType == ItemType.BOOKS) {
            if(itemCountMap.get(itemType) < 2) {
                return true;
            }
            else {
                return false;
            }
        } else {
            return true;
        }
    }
     
      private boolean isMagazineAllowed(HashMap<ItemType, Integer> itemCountMap, ItemType itemType) {
        if(itemCountMap.containsKey(ItemType.MAGAZINE) && itemType == ItemType.MAGAZINE) {
            if(itemCountMap.get(itemType) < 1) {
                return true;
            }
            else {
                return false;
            }
        } else {
            return true;
        }
    }
    
    private BorrowItem getFullDetailsForBorrowItem(BorrowItem borrowItem) {
         return borrowItemDao.getBorrowedItem(borrowItem);
    }
    
    private int numberOfDaysForFine(String borrowDate, String returnDate) {
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
         LocalDate borrowDateFromDb = LocalDate.parse(borrowDate,formatter);
         LocalDate userReturnDate = LocalDate.parse(returnDate,formatter);
        // int days = Period.between(userReturnDate, borrowDateFromDb,PeriodUnits.Days).getDays();
         long days =borrowDateFromDb.until(userReturnDate,ChronoUnit.DAYS);
         System.out.println("borrow " + borrowDateFromDb);
          System.out.println("return " + userReturnDate);
         System.out.println("sdfsdf " + days);
         int daysForFine = 0;
         
         if(days > 14) {
         daysForFine = (int) (days - 14);
         } 
         System.out.println("days are " + daysForFine);
         return daysForFine;
         
    }
    
    private boolean applyFine(int daysForFine,BorrowItem borrowItem) {
        if(daysForFine > 0) {
              borrowItem.setTotalFine(daysForFine * 20);
              return true;
        } else {
        return false ;
        }
    }
    
    
    
}
