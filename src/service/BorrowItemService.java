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
import model.BorrowItem;

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
        return borrowItemDao.addBorrowItemToDb(borrowItem);
    }
    
    public void calculateFineAndReturnItem(BorrowItem borrowItem) {
       BorrowItem borrowItemWithFullDetail = getFullDetailsForBorrowItem(borrowItem);
       int daysForFine =  numberOfDaysForFine(borrowItemWithFullDetail.getBorrowDate(),borrowItemWithFullDetail.getReturnDate());
       applyFine(daysForFine,borrowItemWithFullDetail);
       borrowItemDao.updateBorrowedItem(borrowItem);
       
    }
    
    private BorrowItem getFullDetailsForBorrowItem(BorrowItem borrowItem) {
         return borrowItemDao.getBorrowedItem(borrowItem);
    }
    
    private int numberOfDaysForFine(String borrowDate, String returnDate) {
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
         LocalDate borrowDateFromDb = LocalDate.parse(borrowDate,formatter);
         LocalDate userReturnDate = LocalDate.parse(returnDate,formatter);
         int days = Period.between(borrowDateFromDb, userReturnDate).getDays();
         int daysForFine = 0;
         
         if(days > 14) {
         daysForFine = days - 14;
         } 
         System.out.println("days are " + daysForFine);
         return daysForFine;
         
    }
    
    private void applyFine(int daysForFine,BorrowItem borrowItem) {
        borrowItem.setTotalFine(daysForFine * 20);
    }
    
    
    
}
