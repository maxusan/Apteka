package sample.ui;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Modal {

    public static void display(String title, Pane pane) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setMinWidth(500);
        stage.setMinHeight(500);
//dsadwfw
        stage.setScene(new Scene(pane, stage.getMinWidth(), stage.getMinHeight()));

        stage.show();

    }


}