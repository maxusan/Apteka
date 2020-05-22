package sample.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.ConnectionFactory;
import sample.domain.Client;
import sample.domain.Medicine;
import sample.domain.Sales;

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.zip.CheckedOutputStream;

public class SalesDAOImpl implements SalesDAO {
    @Override
    public Sales getFirstSale() {
        try(Connection connection = ConnectionFactory.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM sales ORDER BY id limit 1")){
            if(rs.next()){
                return extractSaleFromResultSet(rs);
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Sales getSaleById(long id) {
        try(Connection connection = ConnectionFactory.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM sales WHERE id = " + id)){
            if(rs.next()){
                return extractSaleFromResultSet(rs);
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public ObservableList<Sales> getAllSales() {
        try(Connection connection = ConnectionFactory.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM sales");)
        {
            ObservableList<Sales> sale = FXCollections.observableArrayList();
            while(rs.next()){
                Sales sales = extractSaleFromResultSet(rs);
                sale.add(sales);
            }
            return sale;
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insertSale(Sales sales) {
        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO sales(medicineId, clientId, salePrice) VALUES (?, ?, ?)");){
            ps.setLong(1, sales.getMedicine().getId());
            ps.setLong(2, sales.getClient().getId());
            ps.setString(3, sales.getSalePrice());

            int i = ps.executeUpdate();
            if(i == 1){
                return true;
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateSale(Sales sales) {
        try(Connection connection = ConnectionFactory.getConnection();
        PreparedStatement ps = connection.prepareStatement("UPDATE sales set medicineId = ?, clientId = ?, salePrice = ? WHERE id = ?");){
            ps.setLong(1, sales.getMedicine().getId());
            ps.setLong(2, sales.getClient().getId());
            ps.setString(3, sales.getSalePrice());
            ps.setDouble(4, sales.getId());
            int i = ps.executeUpdate();
            if(i == 1){
                return true;
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteSale(long id) {
        try(Connection connection = ConnectionFactory.getConnection();
        PreparedStatement ps = connection.prepareStatement("DELETE FROM sales WHERE id = ?");){
            ps.setLong(1, id);
            int i = ps.executeUpdate();
            if(i == 1){
                return true;
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }

    private Sales extractSaleFromResultSet(ResultSet rs) throws SQLException {
        MedicineDAO medicineDAO = new MedicineDAOImpl();
        ClientDAO clientDAO = new ClientDAOImpl();

        Medicine medicine = medicineDAO.getMedicineById(rs.getLong("medicineId"));
        Client client = clientDAO.getClientById(rs.getLong("clientId"));

        Sales sales = new Sales();
        sales.setId(rs.getLong("id"));
        sales.setMedicine(medicine);
        sales.setClient(client);
        sales.setSalePrice(rs.getString("salePrice"));
        return sales;
    }
}
