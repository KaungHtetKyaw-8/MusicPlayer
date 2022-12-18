package main;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class Mp3PlayerAlert {

    public static void errorAlert(String s) {
        new Alert(AlertType.ERROR, s,ButtonType.OK).showAndWait();
    }
    public static void infoAlert(String s) {
        new Alert(AlertType.INFORMATION, s,ButtonType.OK).showAndWait();
    }
}
