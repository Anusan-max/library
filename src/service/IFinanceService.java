/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.List;
import java.util.Map;
import model.FinancialReport;

/**
 *
 * @author kailainathan
 */
public interface IFinanceService {
    public Map<String,Integer> getFinanceReportForDate(String reportDate);

}
