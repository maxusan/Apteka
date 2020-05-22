package sample.dao;

import javafx.collections.ObservableList;
import sample.domain.Client;

public interface ClientDAO {
    Client getClientById(long id);
    boolean insertClient(Client client);
    boolean deleteClient(long id);
    boolean updateClient(Client client);
    Client getFirstClient();
    ObservableList<Client> getAllClients();
}
