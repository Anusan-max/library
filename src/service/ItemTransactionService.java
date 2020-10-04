/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.ItemDao;
import dao.ItemTransactionDao;
import dto.ItemTransactionDto;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import model.ItemTransaction;
import model.ItemType;

/**
 *
 * @author kailainathan
 */
public class ItemTransactionService implements IItemTransactionService{
    
      // declare ItemDao
    private final ItemTransactionDao borrowItemDao;
    private final ItemDao itemDao;
    
   //java constructure  
    public ItemTransactionService(ItemTransactionDao borrowItemDao,ItemDao itemDao) {
        // create new ItemDao object and assign it to the ItemDao variable
        this.borrowItemDao = borrowItemDao; 
        this.itemDao = itemDao;
    }
    
    @Override
    public String borrowItem(ItemTransaction borrowItem) {
         ArrayList<ItemType> listOfItems = getBorrowedItemTypesForUser(borrowItem.getMemberId());
         if(listOfItems != null) {
              ItemTransactionDto borrowItemDto = checkAvaliablity(getItemCountMap(listOfItems),borrowItem.getItemType());
         if(borrowItemDto.isAllowed()) {
                return borrowItemDao.addBorrowItemToDb(borrowItem);
         }else {
            return borrowItemDto.getMessage();
        }
        } else {
             if(typeAllowedToBorrow(borrowItem.getItemType()).isAllowed()) {
                 return borrowItemDao.addBorrowItemToDb(borrowItem);
             } else {
                 return "Item type not allowed";
             }
              
         }
        
     
    }
    
    @Override
    public String calculateFineAndReturnItem(ItemTransaction borrowItem) {
      ArrayList<ItemTransaction> borrowItemWithFullDetailList = getFullDetailsForBorrowItem(borrowItem);

        if(borrowItemWithFullDetailList.size() < 1) {
            return  null;
        } else {
            int i = applyFineForAllItems(borrowItemWithFullDetailList);
            if(i > 0) {
                return  "Items returned :  fine is RS: " + i;
            } else {
                return "Returned : no fine";
            }
        }
      
       
    }
    
     public ItemTransactionDto checkAvaliablity(HashMap<ItemType, Integer> itemCount, ItemType itemType) {
         ItemTransactionDto borrowItemDto = new ItemTransactionDto(false);
         
         ItemTransactionDto bookAllowed = isBookAllowed(itemCount,itemType);
         ItemTransactionDto magazineAllowed =  isMagazineAllowed(itemCount,itemType);
         ItemTransactionDto typeAllowed = typeAllowedToBorrow(itemType);
         
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
    
    private ItemTransactionDto typeAllowedToBorrow(ItemType itemType) {
        ItemTransactionDto borrowItemDto = new ItemTransactionDto(false);
        

        if(itemType == ItemType.NEWSPAPER || itemType == ItemType.JOURNAL) {
             borrowItemDto.setAllowed(false);
             borrowItemDto.setMessage("Can not borrow NEWSPAPER or JOURNAL");
        } else {
              borrowItemDto.setAllowed(true);
        }
      
        return borrowItemDto;
    }
    
     private ItemTransactionDto isBookAllowed(HashMap<ItemType, Integer> itemCountMap, ItemType itemType) {
        ItemTransactionDto borrowItemDto = new ItemTransactionDto(false);
        
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
     
      private ItemTransactionDto isMagazineAllowed(HashMap<ItemType, Integer> itemCountMap, ItemType itemType) {
          ItemTransactionDto borrowItemDto = new ItemTransactionDto(false);
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
    
    private ArrayList<ItemTransaction>  getFullDetailsForBorrowItem(ItemTransaction borrowItem) {
         return borrowItemDao.getBorrowedItem(borrowItem);
    }
    
    private int numberOfDaysForFine(String borrowDate, String returnDate) {
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
         LocalDate borrowDateFromDb = LocalDate.parse(borrowDate,formatter);
         LocalDate userReturnDate = LocalDate.parse(returnDate,formatter);
        // int days = Period.between(userReturnDate, borrowDateFromDb,PeriodUnits.Days).getDays();
         long days =borrowDateFromDb.until(userReturnDate,ChronoUnit.DAYS);
         int daysForFine = 0;
         
         if(days > 14) {
         daysForFine = (int) (days - 14);
         } 
         return daysForFine;
         
    }
    
    public int applyFineForAllItems(ArrayList<ItemTransaction> borrowItemWithFullDetailList ) {
        int i = 0;
        
        for (ItemTransaction borrowItemWithFullDetail :  borrowItemWithFullDetailList) {
            if(borrowItemWithFullDetail != null) {
                int daysForFine = numberOfDaysForFine(borrowItemWithFullDetail.getBorrowDate(), borrowItemWithFullDetail.getReturnDate());
                i = i +  applyFine(daysForFine, borrowItemWithFullDetail);
                
                borrowItemDao.updateBorrowedItem(borrowItemWithFullDetail);
                itemDao.updateNoOfCopies(borrowItemWithFullDetail.getItemId(),true);
            }
        }
        return i;
    }
    
       private int applyFine(int daysForFine, ItemTransaction borrowItem) {
        if(daysForFine > 0) {
            int i = daysForFine * 20;
              borrowItem.setTotalFine(i);
              
              borrowItem.setPaid(true);
              return i;
        } else {
        return 0;
        }
    }
    
    
    
}
