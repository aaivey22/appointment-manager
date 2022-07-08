package helper;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public abstract class Message {

    public static void error(String title, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setResizable(true);
        alert.setTitle(title);
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
    }

    public static void information(String title, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setResizable(true);
        alert.setTitle(title);
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
    }

    public static void warning(String title, String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setResizable(true);
        alert.setTitle(title);
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
    }

    public static Optional<ButtonType> confirmation(String title, String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
        return result;
    }

}
