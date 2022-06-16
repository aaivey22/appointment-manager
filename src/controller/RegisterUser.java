package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterUser {

    public TextField userNameField;
    public TextField passwordField;
    public TextField confirmField;

    public void registerAction(ActionEvent actionEvent) {
    }

    public void cancelAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Register User");
        stage.setScene(new Scene(root, 810, 470));
        stage.show();
    }
}
