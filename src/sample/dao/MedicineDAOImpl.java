package sample.dao;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.ConnectionFactory;
import sample.domain.Medicine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MedicineDAOImpl implements MedicineDAO{
    @Override
    public Medicine getMedicineById(long id) {
        try(Connection connection = ConnectionFactory.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM medicine WHERE id=" + id);){
            if(rs.next())
            {
                return extractMedicineFromResultSet(rs);
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insertMedicine(Medicine medicine) {
        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO medicine(title, manufacturer, amount, price, type) VALUES (?, ?, ?, ?, ?)");){
                ps.setString(1, medicine.getTitle());
                ps.setString(2, medicine.getManufacturer());
                ps.setInt(3, medicine.getAmount());
                ps.setInt(4, medicine.getPrice());
                ps.setString(5, medicine.getType());
                 int i = ps.executeUpdate();
                 if(i == 1){
                     return true;
                 }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteMedicine(long id) {
        try(Connection connection = ConnectionFactory.getConnection();
        PreparedStatement ps = connection.prepareStatement("DELETE FROM medicine WHERE id=?");){
            ps.setLong(1, id);
            int i = ps.executeUpdate();
            if(i == 1) {
                return true;
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateMedicine(Medicine medicine) {
        try(Connection connection = ConnectionFactory.getConnection();
        PreparedStatement ps = connection.prepareStatement("UPDATE medicine set title = ?, manufacturer = ?, amount = ?, price = ?, type = ? WHERE id=?")){
            ps.setString(1, medicine.getTitle());
            ps.setString(2, medicine.getManufacturer());
            ps.setInt(3, medicine.getAmount());
            ps.setInt(4, medicine.getPrice());
            ps.setString(5, medicine.getType());
            ps.setLong(6, medicine.getId());

            int i = ps.executeUpdate();
            if(i == 1) {
                return true;
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public Medicine getFirstMedicine() {
        try(Connection connection = ConnectionFactory.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM medicine ORDER BY id limit 1 ");){
            if(rs.next()){
                return extractMedicineFromResultSet(rs);
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public ObservableList<Medicine> getAllMedicines() {
        try(Connection connection = ConnectionFactory.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM medicine");){
            ObservableList<Medicine> medicines = FXCollections.observableArrayList();
            while(rs.next()){
                Medicine medicine = extractMedicineFromResultSet(rs);
                medicines.add(medicine);
            }
            return medicines;
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    private Medicine extractMedicineFromResultSet(ResultSet rs) throws SQLException{
        Medicine medicine = new Medicine();
        medicine.setId(rs.getLong("id"));
        medicine.setTitle(rs.getString("title"));
        medicine.setManufacturer(rs.getString("manufacturer"));
        medicine.setType(rs.getString("type"));
        medicine.setAmount(rs.getInt("amount"));
        medicine.setPrice(rs.getInt("price"));

        return medicine;
    }
}
