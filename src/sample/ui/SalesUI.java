package sample.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import sample.dao.*;
import sample.domain.Client;
import sample.domain.Medicine;
import sample.domain.Sales;

public class SalesUI extends BorderPane {
    private ComboBox<Client> clientComboBox = new ComboBox<Client>();

    private Label msgLabel = new Label();
    private TextField idField = new TextField();

    private ComboBox<Medicine> medicineComboBox = new ComboBox<Medicine>();
    private TextField salesTextField = new TextField();

    private Button createButton = new Button("Создать");
    private Button updateButton = new Button("Обновить");
    private Button deleteButton = new Button("Удалить");
    private Button refreshButton = new Button("Refresh");

    private Button clientButton = new Button("Клиенты");
    private Button medicineButton = new Button("Препараты");

    private TableView<Sales> salesTable = new TableView<>();
    private TableColumn<Sales, Long> idColumn = new TableColumn<Sales, Long>("Номер");
    private TableColumn<Sales, Medicine> medicineColumn = new TableColumn<Sales, Medicine>("Препарат");
    private TableColumn<Sales, Client> clientColumn = new TableColumn<Sales, Client>("Клиент");
    private TableColumn<Sales, String > salePriceColumn = new TableColumn<Sales, String>("Цена");

    private ObservableList<Sales> masterData = FXCollections.observableArrayList();

    private SalesDAO salesDAO = new SalesDAOImpl();
    private MedicineDAO medicineDAO = new MedicineDAOImpl();
    private ClientDAO clientDAO = new ClientDAOImpl();

    public SalesUI(){
        setPadding(new Insets(10, 10, 10, 10));
        setTop(msgLabel);
        setRight(initButtons());
        setCenter(initFields());
        setBottom(initTable());
        initListeners();
        setFieldData(salesDAO.getFirstSale());
        setTabledData();
    }
    private Pane initButtons(){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setPadding(new Insets(10, 10, 10 ,10));
        grid.setHgap(20);
        grid.setVgap(20);
        grid.add(createButton, 0, 0);
        createButton.setOnAction(new ButtonHandler());
        grid.add(updateButton,1,0);
        updateButton.setOnAction(new ButtonHandler());
        grid.add(deleteButton, 2, 0);
        deleteButton.setOnAction(new ButtonHandler());
        grid.add(medicineButton, 0, 1 );
        medicineButton.setOnAction(new ButtonHandler());
        grid.add(clientButton, 1, 1);
        clientButton.setOnAction(new ButtonHandler());
        grid.add(refreshButton, 2, 1);
        refreshButton.setOnAction(new ButtonHandler());
        return grid;
    }
    private Pane initFields(){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setPadding(new Insets(10,10,10,10));
        grid.setHgap(20);
        grid.setVgap(2);
        medicineComboBox.getItems().addAll(medicineDAO.getAllMedicines());
        medicineComboBox.setMaxWidth(350);
        clientComboBox.getItems().addAll(clientDAO.getAllClients());
        clientComboBox.setMaxWidth(350);
        grid.add(new Label("Препарат"), 1, 0);
        grid.add(medicineComboBox,2, 0);
        grid.add(new Label("Клиент"), 1,2 );
        grid.add(clientComboBox, 2, 2);
        grid.add(new Label("Цена"), 1, 4);
        grid.add(salesTextField, 2, 4);


        return grid;
    }
    private Pane initTable(){
        VBox pane = new VBox();
        pane.setPadding(new Insets(10, 10, 10, 10));
        salesTable.setMinHeight(600);
        PropertyValueFactory<Sales, Long> idCellValueFactory = new PropertyValueFactory<>("id");
        idColumn.setCellValueFactory(idCellValueFactory);
        PropertyValueFactory<Sales, Medicine> medicineCellValueFactory = new PropertyValueFactory<>("medicine");
        medicineColumn.setCellValueFactory(medicineCellValueFactory);
        PropertyValueFactory<Sales, Client> clientCellValueFactory = new PropertyValueFactory<>("client");
        clientColumn.setCellValueFactory(clientCellValueFactory);
        PropertyValueFactory<Sales, String> salePriceCellValueFactory = new PropertyValueFactory<>("salePrice");
        salePriceColumn.setCellValueFactory(salePriceCellValueFactory);

        salesTable.getColumns().addAll(idColumn, medicineColumn, clientColumn, salePriceColumn);
        pane.getChildren().add(salesTable);
        return pane;
    }
    private void setTabledData(){
        masterData = salesDAO.getAllSales();
        salesTable.setItems(masterData);
    }
    private void setFieldData(Sales sales){
        idField.setText(String.valueOf(sales.getId()));
        medicineComboBox.setValue(sales.getMedicine());
        clientComboBox.setValue(sales.getClient());
        salesTextField.setText(sales.getSalePrice());
    }
    private void initListeners(){
        salesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection)->{
            if(newSelection != null){
                Sales sales = newSelection;
                setFieldData(sales);
            }
        });
    }
    private Sales getFieldData(){
        Sales sales = new Sales();
        Medicine medicine = (Medicine)medicineComboBox.getValue();
        Client client = (Client)clientComboBox.getValue();
        sales.setId(Integer.parseInt(idField.getText()));
        sales.setMedicine(medicine);
        sales.setClient(client);
        sales.setSalePrice(salesTextField.getText());
        return sales;
    }
    private boolean isEmptyFieldData(){
            if(medicineComboBox.getValue() == null || clientComboBox.getValue() == null){
                return true;
            }
            else{
                return false;
            }
    }
private void refreshTable(){
        TableView.TableViewFocusModel<Sales> salesModelFocused = salesTable.getFocusModel();
        setTabledData();
        salesTable.setFocusModel(salesModelFocused);
}
    private class ButtonHandler implements EventHandler<ActionEvent>{
        @Override
                public void handle(ActionEvent e){
                    Sales sales = getFieldData();
                    if(e.getSource().equals(createButton)){
                        if(isEmptyFieldData()){
                            msgLabel.setText("Невозможно создать пустую запись");
                        }
                        if(salesDAO.insertSale(sales)){
                            msgLabel.setText("Успешно");
                            refreshTable();
                        }
                    }
                    if(e.getSource().equals(updateButton)){
                        if(isEmptyFieldData()){
                            msgLabel.setText("Невозможно обновить пустую запись");
                        }
                        if(salesDAO.updateSale(sales)) {
                            msgLabel.setText("Заказ с номером - " + String.valueOf(sales.getId()) + " успешно обновлен");
                            refreshTable();
                        }
                    }
                    if(e.getSource().equals(deleteButton)){
                        if(isEmptyFieldData()){
                            msgLabel.setText("Error");
                        }
                        sales = salesDAO.getSaleById(sales.getId());
                        salesDAO.deleteSale(sales.getId());
                        msgLabel.setText("Заказ с номером - " + String.valueOf(sales.getId()) + " успешно удален");
                        refreshTable();
                    }
                    if(e.getSource().equals(medicineButton)){
                        Modal.display("Препараты", new MedicineUI());
                    }
                    if(e.getSource().equals(clientButton)){
                        Modal.display("Клиенты", new ClientUI());
                    }
                    if(e.getSource().equals(refreshButton)){
                        medicineComboBox.getItems().clear();
                        medicineComboBox.getItems().addAll(medicineDAO.getAllMedicines());
                        clientComboBox.getItems().clear();
                        clientComboBox.getItems().addAll(clientDAO.getAllClients());
                        refreshTable();
                    }
        }
    }
}
