package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import helper.LoginQuery;
import helper.JDBC;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddCustomers implements Initializable{
    public TextField custNameField;
    public TextField custAddressField;
    public TextField custPostalField;
    public TextField custPhoneField;
    public MenuButton selectContact;
    public MenuButton selectDivision;
    public MenuButton selectCountry;

    /** @param url,resourceBundle used to initialize the populateContacts, populateCountries, and populateDivisions methods.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            populateContacts();
        } catch (SQLException e) {
            System.out.println("cannot load contacts");
        }
        try {
            populateCountries();
        } catch (SQLException e) {
            System.out.println("cannot load countries");
        }
        try {
            populateDivisions();
        } catch (SQLException e) {
            System.out.println("cannot load Divisions");
        }
    }

    /** @param actionEvent directToCustomers function used to redirect user to Customers form.*/
    public void directToCustomers(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Customers");
        stage.setScene(new Scene(root, 1100, 590));
        stage.show();
    }

    /** The populateContacts method opens a connection to the database and with the help of an imported function, retrieves the contact name column data and assigns the user selection to the menu button label.*/
    private void populateContacts() throws SQLException {
        JDBC.openConnection();
        java.util.List<String> listofContacts = LoginQuery.getContacts();
        for (int i = 0; i < listofContacts.size(); i++) {
            selectContact.getItems().add(new MenuItem(listofContacts.get(i)));
        }
        JDBC.closeConnection();
    }

    /** The populateCountries method opens a connection to the database and with the help of an imported function, retrieves the country column data and assigns the user selection to the menu button label.*/
    private void populateCountries() throws SQLException {
        JDBC.openConnection();
        java.util.List<String> listofCountries = LoginQuery.getCountries();
        java.util.List<MenuItem> countriesMenuItems = new ArrayList<MenuItem>();
        for (int i = 0; i < listofCountries.size(); i++) {
            countriesMenuItems.add(new MenuItem(listofCountries.get(i)));
            countriesMenuItems.get(i).setOnAction(selectCountryAction);
            selectCountry.getItems().add(countriesMenuItems.get(i));
        }
        JDBC.closeConnection();
    }

    /** The populateDivisions method opens a connection to the database and with the help of an imported function, retrieves the division column data and assigns the user selection to the menu button label.*/
    private void populateDivisions() throws SQLException {
        JDBC.openConnection();
        java.util.List<String> listofDivisions = LoginQuery.getDivisions();
        java.util.List<MenuItem> divisionsMenuItems = new ArrayList<MenuItem>();
        for(int i = 0; i < listofDivisions.size(); i++)
        {
            divisionsMenuItems.add(new MenuItem(listofDivisions.get(i)));
            selectDivision.getItems().add(divisionsMenuItems.get(i));
        }

            JDBC.closeConnection();
    }

    public void saveChangesAction(ActionEvent actionEvent) throws SQLException {
    }

    public void clearFieldsAction(ActionEvent actionEvent) {
    }

    /** @param actionEvent selectCountryAction function fires when the user selects a Country from the menu list.*/
    public EventHandler<ActionEvent> selectCountryAction =
            new EventHandler<ActionEvent>() {
                public void handle(ActionEvent ae) {
                    MenuItem selected = (MenuItem) ae.getSource();
                    String country = selected.getText();
                    selectCountry.setText(country);
                    System.out.println(country);
                }
            };

}
