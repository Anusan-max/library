/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.BorrowItemDao;
import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;
import model.BorrowItem;

/**
 *
 * @author kailainathan
 */
public class FinanceService {
      BorrowItemDao borrowItemDao;
    public FinanceService(BorrowItemDao borrowItemDao) {
        this.borrowItemDao = borrowItemDao;
    }

public Map<String,Integer> getFinanceReport() {
    return calculateTotalPerItem(getBorrowItems());
}

private Map<String,Integer> calculateTotalPerItem(List<BorrowItem> borrowItems) {
    return groupItemAndGetTotal(borrowItems);
}

 private List<BorrowItem> getBorrowItems() {
    //return borrowItemDao.getAllBorrowItemsPerDate();
    return null;
}
 
    private Map<String,Integer> groupItemAndGetTotal(List<BorrowItem> list) {
        return list.stream()
                .collect(groupingBy(BorrowItem::getMemberId,
                        summingInt(BorrowItem::getTotalFine)));
}
}
