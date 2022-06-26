package controller;
import helper.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import helper.LoginQuery;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class AddCustomers implements Initializable{
    public TextField custNameField;
    public TextField custAddressField;
    public TextField custPostalField;
    public TextField custPhoneField;
    public MenuButton selectContact;
    public MenuButton selectDivision;
    public MenuButton selectCountry;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            populateContacts();
        } catch (SQLException e) {
            System.out.println("cannot load contacts");
        }

    }

    public void directToCustomers(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Customers");
        stage.setScene(new Scene(root, 1100, 590));
        stage.show();
    }

    private void populateContacts() throws SQLException {
        JDBC.openConnection();
        java.util.List<String> listofContacts = LoginQuery.getContacts();
        for (int i = 0; i < listofContacts.size(); i++) {
            selectContact.getItems().add(new MenuItem(listofContacts.get(i)));
        }
        JDBC.closeConnection();
    }

    private void populateCountries(){

    }

    private void populateDivisions(){

    }

    public void saveChangesAction(ActionEvent actionEvent) throws SQLException {
    }

    public void clearFieldsAction(ActionEvent actionEvent) {
    }
}
