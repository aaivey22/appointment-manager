package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class RegisterUser {

    public TextField userNameField;
    public TextField passwordField;
    public TextField confirmField;
    public String newUserName;
    public String newUserPassword;
    public String confirmUserPassword;


    public void registerAction(ActionEvent actionEvent) {
        if (userNameField.getText().length() < 8) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setResizable(true);
            alert.setTitle("Invalid User Name");
            alert.setContentText("User name must be between 8 - 12 characters long.");
            Optional<ButtonType> result = alert.showAndWait();

        }
    }

    public void cancelAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Register User");
        stage.setScene(new Scene(root, 810, 470));
        stage.show();
    }
}
