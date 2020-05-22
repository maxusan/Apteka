package sample.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.ConnectionFactory;
import sample.domain.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.zip.CheckedOutputStream;

public class ClientDAOImpl implements ClientDAO {
    @Override
    public Client getClientById(long id) {
        try(Connection connection = ConnectionFactory.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM client WHERE id=" + id);){
            if(rs.next()){
                return extractClientFromResultSet(rs);
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }


    @Override
    public boolean insertClient(Client client) {
        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO client(firstName, lastName, phone, card) VALUES (?, ?, ?, ?)");){
            ps.setString(1, client.getFirstName());
            ps.setString(2, client.getLastName());
            ps.setString(3, client.getPhone());
            ps.setLong(4, client.getCard());
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
    public boolean deleteClient(long id) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement("DELETE FROM client WHERE id=?");) {
            ps.setLong(1, id);
            int i = ps.executeUpdate();
            if (i == 1) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateClient(Client client) {
        try(Connection connection = ConnectionFactory.getConnection();
        PreparedStatement ps = connection.prepareStatement("UPDATE client set firstName = ?, lastName = ?, phone = ?, card = ? WHERE id=?");){
            ps.setString(1, client.getFirstName());
            ps.setString(2, client.getLastName());
            ps.setString(3, client.getPhone());
            ps.setLong(4, client.getCard());
            ps.setLong(5, client.getId());

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
    public Client getFirstClient() {
        try(Connection connection = ConnectionFactory.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM client ORDER BY id limit 1");){
            if(rs.next()) {
                return extractClientFromResultSet(rs);
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public ObservableList<Client> getAllClients() {
        try(Connection connection = ConnectionFactory.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM client");) {
            ObservableList<Client> clients = FXCollections.observableArrayList();
            while (rs.next()) {
                Client client = extractClientFromResultSet(rs);
                clients.add(client);
            }
            return clients;
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }




    private Client extractClientFromResultSet(ResultSet rs) throws SQLException {
        Client client = new Client();
        client.setId(rs.getLong("id"));
        client.setFirstName(rs.getString("firstName"));
        client.setLastName(rs.getString("lastName"));
        client.setPhone(rs.getString("phone"));
        client.setCard(rs.getLong("card"));
        return client;
    }
}
