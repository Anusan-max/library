/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.ItemTransactionDao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;
import model.FinancialReport;

/**
 *
 * @author kailainathan
 */
public class FinanceService implements IFinanceService{
      ItemTransactionDao borrowItemDao;
    
      
      public FinanceService(ItemTransactionDao borrowItemDao) {
        this.borrowItemDao = borrowItemDao;
    }

public Map<String,Integer> getFinanceReportForDate(String reportDate) {
    return calculateTotalPerItem(getBorrowItems(reportDate));
}

private Map<String,Integer> calculateTotalPerItem(List<FinancialReport> borrowItems) {
    return groupItemAndGetTotal(borrowItems);
    }

 private List<FinancialReport> getBorrowItems(String reportDate) {
    return borrowItemDao.getAllBorrowItemsPerDate(reportDate);
}
 
    private Map<String,Integer> groupItemAndGetTotal(List<FinancialReport> list) {
        for (FinancialReport r : list ) {
        }
        return list.stream()
                .collect(groupingBy(FinancialReport::getMemberId,
                        summingInt(FinancialReport::getTotalFine)));
}
}
