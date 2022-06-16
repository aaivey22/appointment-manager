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

    /**  @param actionEvent registerAction function used to execute warning alert to user if username is < 8 chars long. */
    public void registerAction(ActionEvent actionEvent) {
        newUserName = userNameField.getText();
        newUserPassword = passwordField.getText();
        confirmUserPassword = confirmField.getText();

        if (userNameField.getText().length() < 8 || userNameField.getText().length() > 12) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setResizable(true);
            alert.setTitle("Invalid User Name");
            alert.setContentText("User name must be between 8 - 12 characters long.");
            Optional<ButtonType> result = alert.showAndWait();
        }
        else {
            if (passwordField.getText().length() < 8 || passwordField.getText().length() > 12) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setResizable(true);
                alert.setTitle("Invalid Password");
                alert.setContentText("Password must be between 8 - 12 characters long.");
                Optional<ButtonType> result = alert.showAndWait();
            }
            else {
                if (newUserPassword.equals(confirmUserPassword)) {
                    System.out.println("reishi");
                }
                else {
                    System.out.println("no match");
                }
            }
        }
    }

    /**  @param actionEvent cancelAction function used to redirect user to login form. */
    public void cancelAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Register User");
        stage.setScene(new Scene(root, 810, 470));
        stage.show();
    }
}
