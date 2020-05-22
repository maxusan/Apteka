package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.ui.ClientUI;
import sample.ui.MedicineUI;
import sample.ui.SalesUI;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        stage.setScene(new Scene(new SalesUI(), 600, 600));
        stage.setTitle("Заказы");
        stage.show();
    }


    public static void main(String[] args) {
        Application.launch(args);
    }
}
