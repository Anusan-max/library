/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ItemDao;
import dao.ItemTransactionDao;
import dao.TransactionDao;
import dao.UserDao;
import service.FinanceService;
import service.IFinanceService;
import service.IInventoryService;
import service.IItemService;
import service.IItemTransactionService;
import service.ITransactionService;
import service.IUserService;
import service.InventoryService;
import service.ItemService;
import service.ItemTransactionService;
import service.TransactionService;
import service.UserService;

/**
 *
 * @author kailainathan
 */
public class MainController {
    
    private final IItemTransactionService itemTransactionService;
    private final IItemService itemService;
    private final ITransactionService transactionService;
    private final IUserService userService;
    private final IInventoryService inventoryService;
    private final IFinanceService financeService;

    public IItemTransactionService getItemTransactionService() {
        return itemTransactionService;
    }

    public IItemService getItemService() {
        return itemService;
    }

    public ITransactionService getTransactionService() {
        return transactionService;
    }

    public IUserService getUserService() {
        return userService;
    }

    public IInventoryService getInventoryService() {
        return inventoryService;
    }

    public IFinanceService getFinanceService() {
        return financeService;
    }
    
    public MainController() {
       ItemDao itemDao = new ItemDao();
       ItemTransactionDao itemTransactionDao = new ItemTransactionDao();
       
        this.itemTransactionService = new ItemTransactionService(itemTransactionDao,itemDao);
        this.itemService = new ItemService(itemDao);
        this.transactionService = new TransactionService(new TransactionDao());
        this.userService = new UserService(new UserDao()); 
        this.inventoryService = new InventoryService(itemDao);
        this.financeService = new FinanceService(itemTransactionDao);
    }
    
}
