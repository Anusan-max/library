/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.TransactionDao;
import java.util.ArrayList;
import model.Transaction;

/**
 *
 * @author kailainathan
 */
public class TransactionService {
    
    TransactionDao transactionDao;
    
    TransactionService(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }
    
    public void enterTransaction(Transaction transaction) {
        transactionDao.enterTransaction(transaction);
    }
    
    public ArrayList<Transaction> getAllTransaction() {
        transactionDao.enterTransaction();
        return null;
    }
}
