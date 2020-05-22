package sample.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import sample.dao.MedicineDAO;
import sample.dao.MedicineDAOImpl;
import sample.domain.Medicine;

import java.util.PropertyPermission;

public class MedicineUI extends BorderPane {
    private Label msgLabel = new Label("Препараты");
    private TextField idField = new TextField();
    private TextField titleField = new TextField();
    private TextField manufacturerField = new TextField();
    private TextField typeField = new TextField();
    private TextField amountField = new TextField();
    private TextField priceField = new TextField();

    private Button createButton = new Button("Создать");
    private Button updateButton = new Button("Обновить");
    private Button deleteButton = new Button("Удалить");

    private TableView<Medicine> medicineTable = new TableView<>();
    private TableColumn<Medicine, Long> idColumn = new TableColumn<Medicine, Long>("Номер");
    private TableColumn<Medicine, String> titleColumn = new TableColumn<Medicine, String>("Название");
    private TableColumn<Medicine, String> manufacturerColumn = new TableColumn<Medicine, String>("Производитель");
    private TableColumn<Medicine, String > typeColumn = new TableColumn<Medicine, String>("Тип");
    private TableColumn<Medicine, Integer> amountColumn = new TableColumn<Medicine, Integer>("Кол-во");
    private TableColumn<Medicine, Integer> priceColumn = new TableColumn<Medicine, Integer>("Цена");
    private ObservableList<Medicine> masterData = FXCollections.observableArrayList();

    private MedicineDAO medicineDAO = new MedicineDAOImpl();

    public MedicineUI(){
        setPadding(new Insets(10, 10, 10, 10));
        setTop(msgLabel);
        setRight(initButtons());
        setCenter(initFields());
        setBottom(initTable());
        initListeners();

        setFieldData(medicineDAO.getFirstMedicine());
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
        grid.add(new Label("Название"), 1, 0);
        grid.add(titleField, 2, 0);
        grid.add(new Label("Производитель"), 1, 1);
        grid.add(manufacturerField, 2, 1);
        grid.add(new Label("Тип"), 1, 2);
        grid.add(typeField, 2, 2);
        grid.add(new Label("Кол-во"), 1, 3);
        grid.add(amountField, 2, 3);
        grid.add(new Label("Цена"), 1, 4);
        grid.add(priceField,2,4 );
        return grid;
    }
    private Pane initTable(){
        VBox pane = new VBox();
        pane.setPadding(new Insets(10, 10, 10, 10));
        medicineTable.setMinHeight(300);
        PropertyValueFactory<Medicine, Long> idCellValueFactory =  new PropertyValueFactory<>("id");
        idColumn.setCellValueFactory(idCellValueFactory);
        PropertyValueFactory<Medicine, String> titleCellValueFactory = new PropertyValueFactory<>("title");
        titleColumn.setCellValueFactory(titleCellValueFactory);
        PropertyValueFactory<Medicine, String> manufacturerCellValueFactory = new PropertyValueFactory<>("manufacturer");
        manufacturerColumn.setCellValueFactory(manufacturerCellValueFactory);
        PropertyValueFactory<Medicine, String> typeCellValueFactory = new PropertyValueFactory<>("type");
        typeColumn.setCellValueFactory(typeCellValueFactory);
        PropertyValueFactory<Medicine, Integer> amountCellValueFactory = new PropertyValueFactory<>("amount");
        amountColumn.setCellValueFactory(amountCellValueFactory);
        PropertyValueFactory<Medicine, Integer> priceCellValueFactory = new PropertyValueFactory<>("price");
        priceColumn.setCellValueFactory(priceCellValueFactory);

        medicineTable.getColumns().addAll(idColumn, titleColumn, manufacturerColumn, typeColumn, amountColumn, priceColumn);
        pane.getChildren().add(medicineTable);
        return pane;
    }
    private Medicine getFieldData(){
        Medicine medicine = new Medicine();
        medicine.setId(Integer.parseInt(idField.getText()));
        medicine.setTitle(titleField.getText());
        medicine.setManufacturer(manufacturerField.getText());
        medicine.setType(typeField.getText());
        medicine.setAmount(Integer.parseInt(amountField.getText()));
        medicine.setPrice(Integer.parseInt(priceField.getText()));
        return medicine;
    }
    private void setFieldData(Medicine medicine){
        idField.setText(String.valueOf(medicine.getId()));
        titleField.setText(String.valueOf(medicine.getTitle()));
        manufacturerField.setText(String.valueOf(medicine.getManufacturer()));
        typeField.setText(String.valueOf(medicine.getType()));
        amountField.setText(String.valueOf(medicine.getAmount()));
        priceField.setText(String.valueOf(medicine.getPrice()));
    }
    private void setTabledData(){
        masterData = medicineDAO.getAllMedicines();
        medicineTable.setItems(masterData);
    }
    private boolean isEmptyFieldData(){
        if(titleField.getText().isEmpty() || manufacturerField.getText().isEmpty()){
            return true;
        }
        else{
            return false;
        }
    }
    private void initListeners(){
        medicineTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection)->{
            if(newSelection != null){
                Medicine medicine = newSelection;
                setFieldData(medicine);
            }
        });
    }
    private class ButtonHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent e){
            Medicine medicine = getFieldData();
            if(e.getSource().equals(createButton)){
                if(isEmptyFieldData()){
                    msgLabel.setText("Невозможно создать пустую запись");
                    return;
                }
                if(medicineDAO.insertMedicine(medicine)){
                    msgLabel.setText("Успешно");
                }
                createButton.setText("Создать");
                refreshTable();
            }
            if(e.getSource().equals(updateButton)){
                if(isEmptyFieldData()){
                    msgLabel.setText("Невозможно обновить пустую запись");
                    return;
                }
                if(medicineDAO.updateMedicine(medicine)){
                    msgLabel.setText("Препарат с номером - " + String.valueOf(medicine.getId()) + " обновлен успешно");
                    refreshTable();
                }
            }
            if(e.getSource().equals(deleteButton)){
                if(isEmptyFieldData()){
                    msgLabel.setText("Невозможно удалить пустую запись");
                    return;
                }
                medicine = medicineDAO.getMedicineById(medicine.getId());
                medicineDAO.deleteMedicine(medicine.getId());
                msgLabel.setText("Препарат с номером - " + String.valueOf(medicine.getId()) + " успешно удален");
                refreshTable();
            }

        }

        private void refreshTable(){
            TableView.TableViewFocusModel<Medicine> medicineModelFocused = medicineTable.getFocusModel();
            setTabledData();
            medicineTable.setFocusModel(medicineModelFocused);
        }
    }

}

