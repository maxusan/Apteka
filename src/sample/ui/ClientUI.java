package sample.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import sample.dao.ClientDAO;
import sample.dao.ClientDAOImpl;
import sample.domain.Client;
import sample.domain.Medicine;

public class ClientUI extends BorderPane {
    private Label msgLabel = new Label("Клиенты");
    private TextField idField = new TextField();
    private TextField firstNameField = new TextField();
    private TextField lastNameField = new TextField();
    private TextField phoneField = new TextField();
    private TextField cardField = new TextField();

    private Button createButton = new Button("Создать");
    private Button updateButton = new Button("Обновить");
    private Button deleteButton = new Button("Удалить");

    private TableView<Client> clientTable = new TableView<>();
    private TableColumn<Client, Long> idColumn = new TableColumn<Client, Long>("Номер");
    private TableColumn<Client, String> firstNameColumn = new TableColumn<Client, String>("Имя");
    private TableColumn<Client, String> lastNameColumn = new TableColumn<Client, String>("Фамилия");
    private TableColumn<Client, String > phoneColumn = new TableColumn<Client, String>("Телефон");
    private TableColumn<Client, Long> cardColumn = new TableColumn<Client, Long>("Карта");

    private ObservableList<Client> masterData = FXCollections.observableArrayList();

    private ClientDAO clientDAO = new ClientDAOImpl();

    public ClientUI(){
        setPadding(new Insets(10, 10, 10, 10));
        setTop(msgLabel);
        setRight(initButtons());
        setCenter(initFields());
        setBottom(initTable());

        initListeners();

        setFieldData(clientDAO.getFirstClient());
        setTabledData();
    }
    private Pane initButtons(){
        HBox box = new HBox();
        box.setAlignment(Pos.TOP_CENTER);
        box.setSpacing(5);
        box.getChildren().add(createButton);
        createButton.setOnAction(new ButtonHandler());
        box.getChildren().add(updateButton);
        updateButton.setOnAction(new ButtonHandler());
        box.getChildren().add(deleteButton);
        deleteButton.setOnAction(new ButtonHandler());
        return box;
    }
    private Pane initFields(){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(20);
        grid.setVgap(2);
        grid.add(new Label("Имя"), 1, 0);
        grid.add(firstNameField,2,0);
        grid.add(new Label("Фамилия"), 1, 1);
        grid.add(lastNameField,2,1);
        grid.add(new Label("Телефон"), 1, 2);
        grid.add(phoneField,2,2);
        grid.add(new Label("Карта"), 1, 3);
        grid.add(cardField,2,3);
        return grid;
    }
    private Pane initTable(){
        VBox pane = new VBox();
        pane.setPadding(new Insets(10, 10, 10, 10));
        clientTable.setMinHeight(300);
        PropertyValueFactory<Client, Long> idCellValueFactory = new PropertyValueFactory<>("id");
        idColumn.setCellValueFactory(idCellValueFactory);
        PropertyValueFactory<Client, String> firstNameCellValueFactory = new PropertyValueFactory<>("firstName");
        firstNameColumn.setCellValueFactory(firstNameCellValueFactory);
        PropertyValueFactory<Client, String> lastNameCellValueFactory = new PropertyValueFactory<>("lastName");
        lastNameColumn.setCellValueFactory(lastNameCellValueFactory);
        PropertyValueFactory<Client, String> phoneCellValueFactory = new PropertyValueFactory<>("phone");
        phoneColumn.setCellValueFactory(phoneCellValueFactory);
        PropertyValueFactory<Client, Long> cardCellValueFactory = new PropertyValueFactory<>("card");
        cardColumn.setCellValueFactory(cardCellValueFactory);

        clientTable.getColumns().addAll(idColumn,firstNameColumn, lastNameColumn, phoneColumn, cardColumn);
        pane.getChildren().add(clientTable);
        return pane;
    }
    private void setTabledData(){
        masterData = clientDAO.getAllClients();
        clientTable.setItems(masterData);
    }
    private Client getFieldData(){
        Client client = new Client();
        client.setId(Integer.parseInt(idField.getText()));
        client.setFirstName(firstNameField.getText());
        client.setLastName(lastNameField.getText());
        client.setPhone(phoneField.getText());
        client.setCard(Long.parseLong(cardField.getText()));
        return client;
    }
    private void setFieldData(Client client){
        idField.setText(String.valueOf(client.getId()));
        firstNameField.setText(String.valueOf(client.getFirstName()));
        lastNameField.setText(String.valueOf(client.getLastName()));
        phoneField.setText(String.valueOf(client.getPhone()));
        cardField.setText(String.valueOf(client.getCard()));
    }
    private boolean isEmptyFieldData(){
        if(firstNameField.getText().isEmpty() && lastNameField.getText().isEmpty()){
            return true;
        }
        else{
            return false;
        }
    }
    private void initListeners(){
        clientTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection)->{
                if(newSelection != null) {
                    Client client = newSelection;
                    setFieldData(client);
                }
        });
    }

    private void refreshTable(){
        TableView.TableViewFocusModel<Client> clientModelFocused = clientTable.getFocusModel();
        setTabledData();
        clientTable.setFocusModel(clientModelFocused);
    }
    private class ButtonHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent e){
            Client client = getFieldData();
            if(e.getSource().equals(createButton))
            {
                if(isEmptyFieldData()){
                    msgLabel.setText("Невозможно создать пустую запись");
                    return;
                }
                if(clientDAO.insertClient(client)){
                    msgLabel.setText("Успешно");
                    refreshTable();
                }

            }
            if(e.getSource().equals(updateButton)){
                if(isEmptyFieldData()){
                    msgLabel.setText("Невозможно обновить пустую запись");
                    return;
                }
                if(clientDAO.updateClient(client)){
                    msgLabel.setText("Клиент с id-" + String.valueOf(client.getId()) + " обновлен успешно");
                    refreshTable();
                }
            }
            if(e.getSource().equals(deleteButton)){
                if(isEmptyFieldData()){
                    msgLabel.setText("Невозможно удалить пустую запись");
                    return;
                }
                client = clientDAO.getClientById(client.getId());
                clientDAO.deleteClient(client.getId());
                msgLabel.setText("Клиент с id-" + String.valueOf(client.getId()) + " успешно удален");
                refreshTable();
            }
            }
        }

}
