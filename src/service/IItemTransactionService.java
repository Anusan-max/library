/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import model.ItemTransaction;

/**
 *
 * @author kailainathan
 */
public interface IItemTransactionService {
    public String borrowItem(ItemTransaction borrowItem);
     public String calculateFineAndReturnItem(ItemTransaction borrowItem);
}
