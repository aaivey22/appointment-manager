package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import helper.LoginQuery;
import helper.JDBC;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddCustomers implements Initializable{
    public TextField custNameField;
    public TextField custAddressField;
    public TextField custPostalField;
    public TextField custPhoneField;
    public MenuButton selectContact;
    public MenuButton selectDivision;
    public MenuButton selectCountry;
    public String contact = "";
    public String country = "";
    public String division = "";
    public String divisionID = "";
    public String name;
    public String streetAddress;
    public String phoneNum;
    public String postalCode;

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
        java.util.List<MenuItem> contactsMenuItems = new ArrayList<MenuItem>();
        for (int i = 0; i < listofContacts.size(); i++) {
            contactsMenuItems.add(new MenuItem(listofContacts.get(i)));
            contactsMenuItems.get(i).setOnAction(selectContactAction);
            selectContact.getItems().add(contactsMenuItems.get(i));
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
    private void populateDivisions(String countryName) throws SQLException {
        JDBC.openConnection();
        selectDivision.setText("Select Division");
        selectDivision.getItems().clear();
        selectDivision.getItems().removeAll();
        selectDivision.setDisable(false);
        String countryID = LoginQuery.getcountryID(countryName);
        System.out.println(countryID);
        java.util.List<String> listofDivisions = new ArrayList<String>();
        listofDivisions = LoginQuery.getDivisions(countryID);
        java.util.List<MenuItem> divisionsMenuItems = new ArrayList<MenuItem>();
        for(int i = 0; i < listofDivisions.size(); i++)
        {
            divisionsMenuItems.add(new MenuItem(listofDivisions.get(i)));
            divisionsMenuItems.get(i).setOnAction(selectDivisionAction);
            selectDivision.getItems().add(divisionsMenuItems.get(i));
        }
            JDBC.closeConnection();
    }

    /** @param actionEvent saveChangesAction function fires when the user clicks save changes. The data is then stored in the database table.*/
    public void saveChangesAction(ActionEvent actionEvent) throws SQLException {
        name = custNameField.getText();
        streetAddress = custAddressField.getText();
        postalCode = custPostalField.getText();
        phoneNum = custPhoneField.getText();
        int rowsModified = 0;

        JDBC.openConnection();
        try {
            divisionID = LoginQuery.getDivisionID(division);
        } catch (SQLException e) {
            System.out.println("failed to get division id");
        }
        JDBC.closeConnection();

        if(name.length() > 0 && streetAddress.length() > 0 && postalCode.length() > 0 && phoneNum.length() > 0 && country.length() > 0 && contact.length() > 0 && division.length() > 0 && divisionID.length() > 0)
        {
            JDBC.openConnection();
            try {
                rowsModified = LoginQuery.addCustomer(name, streetAddress, postalCode, phoneNum, divisionID);
            } catch (SQLException e) {
                System.out.println("failed to get division id");
            }
            JDBC.closeConnection();

            if( rowsModified > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setResizable(true);
                alert.setTitle("Success");
                alert.setContentText("New customer added");
                Optional<ButtonType> result = alert.showAndWait();

            } else
            {
                System.out.println("customer could not be added");
            }

        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setResizable(true);
            alert.setTitle("Missing Information");
            alert.setContentText("All fields are required.");
            Optional<ButtonType> result = alert.showAndWait();
        }
    }

    /** @param actionEvent clearFieldsAction function fires when the user clicks clear. This resets all the form fields to their default state.*/
    public void clearFieldsAction(ActionEvent actionEvent) {
        selectContact.setText("Select Contact");
        custNameField.setText("");
        custAddressField.setText("");
        selectCountry.setText("Select Country");
        selectDivision.setText("Select Division");
        selectDivision.getItems().clear();
        selectDivision.getItems().removeAll();
        selectDivision.setDisable(true);
        custPostalField.setText("");
        custPhoneField.setText("");
        contact = "";
        country = "";
        division = "";


    }

    /** @param actionEvent selectContactAction function fires when the user selects a Contact from the menu list.*/
    public EventHandler<ActionEvent> selectContactAction =
            new EventHandler<ActionEvent>() {
                public void handle(ActionEvent ae) {
                    contact = ((MenuItem) ae.getSource()).getText();
                    selectContact.setText(contact);
                }
            };


    /** @param actionEvent selectCountryAction function fires when the user selects a Country from the menu list. It then runs the populateDivisions function with country as a parameter.*/
    /** The division variable is also reset to default state to avoid potential bugs when user changes their country selection.**/
    public EventHandler<ActionEvent> selectCountryAction =
            new EventHandler<ActionEvent>() {
                public void handle(ActionEvent ae) {
                    country = ((MenuItem) ae.getSource()).getText();
                    selectCountry.setText(country);
                    division = "";
                    try {
                        populateDivisions(country);
                    } catch (SQLException e) {
                        System.out.println("cannot load Divisions");
                    }
                }
            };

    /** @param actionEvent selectDivisionAction function fires when the user selects a Division from the menu list.*/
    public EventHandler<ActionEvent> selectDivisionAction =
            new EventHandler<ActionEvent>() {
                public void handle(ActionEvent ae) {
                    division = ((MenuItem) ae.getSource()).getText();
                    selectDivision.setText(((MenuItem) ae.getSource()).getText());
                }
            };



}
