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
public class TransactionService implements ITransactionService{
    
    private TransactionDao transactionDao;
    
    public TransactionService(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }
    
    @Override
    public void addTransaction(Transaction transaction) {
        transactionDao.addTransaction(transaction);
    }
    
    @Override
    public ArrayList<Transaction> getAllTransaction() {
        return transactionDao.getAllTransaction();
    }
}
