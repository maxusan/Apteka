package sample.dao;

import javafx.collections.ObservableList;
import sample.domain.Sales;

public interface SalesDAO {
    Sales getFirstSale();
    Sales getSaleById(long id);
    ObservableList<Sales> getAllSales();
    boolean insertSale(Sales sales);
    boolean updateSale(Sales sales);
    boolean deleteSale(long id);

}
