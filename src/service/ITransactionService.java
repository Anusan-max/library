/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.ArrayList;
import model.Transaction;

/**
 *
 * @author kailainathan
 */
public interface ITransactionService {
     public void addTransaction(Transaction transaction);
     public ArrayList<Transaction> getAllTransaction();
}
