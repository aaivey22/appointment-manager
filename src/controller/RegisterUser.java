package controller;

import helper.JDBC;
import helper.Message;
import helper.LoginQuery;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
//import java.util.Optional;

/**
 * This class controls the RegisterUser page.
 */
public class RegisterUser {

    public TextField userNameField;
    public TextField passwordField;
    public TextField confirmField;
    private String newUserName;
    private String newUserPassword;
    private String confirmUserPassword;

    /**
     * @param actionEvent registerAction function used to execute if statement for warning alert if username is < 8 or >12 chars long.
     *                    The nested if statements contain multiple username and  password checks and alerts to guid customers.
     */
    public void registerAction(ActionEvent actionEvent) throws SQLException, IOException {
        newUserName = userNameField.getText();
        newUserPassword = passwordField.getText();
        confirmUserPassword = confirmField.getText();

        if (userNameField.getText().length() < 8 || userNameField.getText().length() > 12) {
            Message.warning("Invalid User Name", "User name must be between 8 - 12 characters long.");
        } else {
            if (passwordField.getText().length() < 8 || passwordField.getText().length() > 12) {
                Message.warning("Invalid Password", "Password must be between 8 - 12 characters long.");
            } else {
                if (newUserPassword.equals(confirmUserPassword)) {
                    try {
                        JDBC.openConnection();
                        int users = LoginQuery.registerUser(newUserName, newUserPassword);
                        JDBC.closeConnection();
                        if (users < 1) {
                            Message.warning("Registration Error", "No Users Added");
                        } else {
                            Message.information("Success", "User successfully registered");
                            Parent root = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
                            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                            stage.setTitle("Register User");
                            stage.setScene(new Scene(root, 810, 470));
                            stage.show();
                        }
                    } catch (Exception e) {
                        Message.warning("Registration Error", "User already exists");
                    }
                } else {
                    System.out.println("no match");
                }
            }
        }
    }

    /**
     * @param actionEvent cancelAction function used to redirect user to login form.
     */
    public void cancelAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Login");
        stage.setScene(new Scene(root, 810, 470));
        stage.show();
    }
}
