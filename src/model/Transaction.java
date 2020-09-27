/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author kailainathan
 */
public class Transaction {
    private String localDate;
    private TransactionType transactionType;
    
    public  Transaction() {
    }
    
    public Transaction(TransactionType transactionType) {
        this.transactionType = transactionType;
        LocalDate today = LocalDate.now();
        String formattedDate = today.format( DateTimeFormatter.ofPattern("d/MM/yyyy"));
        this.localDate = formattedDate;
    }

    public String getLocalDate() {
        return localDate;
    }

    public void setLocalDate(String localDate) {
        this.localDate = localDate;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
  
}
