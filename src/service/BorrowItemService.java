/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.BorrowItemDao;
import dto.BorrowItemDto;
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
         BorrowItemDto borrowItemDto = checkAvaliablity(getItemCountMap(listOfItems),borrowItem.getItemType());
         if(borrowItemDto.isAllowed()) {
                return borrowItemDao.addBorrowItemToDb(borrowItem);
         }else {
            return borrowItemDto.getMessage();
        }
     
    }
    
    public String calculateFineAndReturnItem(BorrowItem borrowItem) {
       BorrowItem borrowItemWithFullDetail = getFullDetailsForBorrowItem(borrowItem);
       
       if(borrowItemWithFullDetail != null) {
       int daysForFine =  numberOfDaysForFine(borrowItemWithFullDetail.getBorrowDate(),borrowItemWithFullDetail.getReturnDate());
       String result = applyFine(daysForFine,borrowItemWithFullDetail);
       borrowItemDao.updateBorrowedItem(borrowItem);
       return result;
       } else {
       return null;
       }
      
       
    }
    
     public BorrowItemDto checkAvaliablity(HashMap<ItemType, Integer> itemCount, ItemType itemType) {
         BorrowItemDto borrowItemDto = new BorrowItemDto(false);
         
         BorrowItemDto bookAllowed = isBookAllowed(itemCount,itemType);
         BorrowItemDto magazineAllowed =  isMagazineAllowed(itemCount,itemType);
         BorrowItemDto typeAllowed = typeAllowedToBorrow(itemType);
         
         if(bookAllowed.isAllowed() && magazineAllowed.isAllowed() && typeAllowed.isAllowed()) {
             borrowItemDto.setAllowed(true);
             borrowItemDto.setMessage("Item Borrowed");
         } else {
             if(bookAllowed.getMessage() != null) {
                 borrowItemDto.setMessage(bookAllowed.getMessage());
             } else if (magazineAllowed.getMessage() != null) {
                  borrowItemDto.setMessage(magazineAllowed.getMessage());
             }else if (typeAllowed.getMessage() != null) {
                  borrowItemDto.setMessage(typeAllowed.getMessage());
             }
              borrowItemDto.setAllowed(false);
         }
         
        return borrowItemDto;
    }
    
    public ArrayList<ItemType> getBorrowedItemTypesForUser(String userId) {
    return  borrowItemDao.getBorrowedItemTypesForUser(userId);
    }
       
     
    private HashMap<ItemType,Integer> getItemCountMap(ArrayList<ItemType> listOfitems) {
        Set<ItemType> unique = new HashSet<>(listOfitems);
        HashMap<ItemType,Integer> userItemMap = new HashMap<>();

        for (ItemType key : unique) {
            userItemMap.put(key, Collections.frequency(listOfitems, key));
        }
        return userItemMap;
    }
    
    private BorrowItemDto typeAllowedToBorrow(ItemType itemType) {
        BorrowItemDto borrowItemDto = new BorrowItemDto(false);
        

        if(itemType == ItemType.NEWSPAPER || itemType == ItemType.JOURNAL) {
             borrowItemDto.setAllowed(false);
             borrowItemDto.setMessage("Can not borrow NEWSPAPER or JOURNAL");
        } else {
              borrowItemDto.setAllowed(true);
        }
      
        return borrowItemDto;
    }
    
     private BorrowItemDto isBookAllowed(HashMap<ItemType, Integer> itemCountMap, ItemType itemType) {
        BorrowItemDto borrowItemDto = new BorrowItemDto(false);
        
        if(itemCountMap.containsKey(ItemType.BOOKS) && itemType == ItemType.BOOKS) {
            if(itemCountMap.get(itemType) < 2) {
                borrowItemDto.setAllowed(true);
            }
            else {
                borrowItemDto.setAllowed(false);
                borrowItemDto.setMessage("Already borrowed  2 BOOKS");
            }
        } else {
             borrowItemDto.setAllowed(true);
        }
        return borrowItemDto;
    }
     
      private BorrowItemDto isMagazineAllowed(HashMap<ItemType, Integer> itemCountMap, ItemType itemType) {
          BorrowItemDto borrowItemDto = new BorrowItemDto(false);
        if(itemCountMap.containsKey(ItemType.MAGAZINE) && itemType == ItemType.MAGAZINE) {
            if(itemCountMap.get(itemType) < 1) {
                 borrowItemDto.setAllowed(true);
            }
            else {
                borrowItemDto.setAllowed(false);
                borrowItemDto.setMessage("Already borrowed 1 MAGAZINE");
            }
        } else {
            borrowItemDto.setAllowed(true);
        }
        return borrowItemDto;
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
    
    private String applyFine(int daysForFine,BorrowItem borrowItem) {
        if(daysForFine > 0) {
            int i = daysForFine * 20;
              borrowItem.setTotalFine(i);
              return i + "RS - Fine Applied and returned";
        } else {
        return "returned without Fine" ;
        }
    }
    
    
    
}
