package controller;

import helper.JDBC;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

import model.customer;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import helper.LoginQuery;

public class ModifyCustomers implements Initializable {
    public TextField custIDField;
    public TextField custNameField;
    public TextField custAddressField;
    public TextField custPostalField;
    public TextField custPhoneField;
    public MenuButton selectDivision;
    public MenuButton selectCountry;
    public customer modifiedCustomer = null;
    public ResultSet resultSet;
    public String divisionName;
    public String countryName;
    public Integer customerID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            populateCountries();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        modifiedCustomer = Customers.getModifiedCustomer();
        customerID = Integer.valueOf(modifiedCustomer.getCustomerID());
        setUpAction(null);
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

    /** @param actionEvent selectCountryAction function fires when the user selects a Country from the menu list. It then runs the populateDivisions function with country as a parameter.*/
    /** The division variable is also reset to default state to avoid potential bugs when user changes their country selection.**/
    public EventHandler<ActionEvent> selectCountryAction =
            new EventHandler<ActionEvent>() {
                public void handle(ActionEvent ae) {
                    countryName = ((MenuItem) ae.getSource()).getText();
                    selectCountry.setText(countryName);
                    divisionName = "";
                    try {
                        populateDivisions(countryName);
                    } catch (SQLException e) {
                        System.out.println("cannot load Divisions");
                    }
                }
            };

    /** @param actionEvent selectDivisionAction function fires when the user selects a Division from the menu list.*/
    public EventHandler<ActionEvent> selectDivisionAction =
            new EventHandler<ActionEvent>() {
                public void handle(ActionEvent ae) {
                    divisionName = ((MenuItem) ae.getSource()).getText();
                    selectDivision.setText(((MenuItem) ae.getSource()).getText());
                }
            };

    /** The populateDivisions method opens a connection to the database and with the help of an imported function, retrieves the division column data and assigns the user selection to the menu button label.*/
    private void populateDivisions(String countryName) throws SQLException {
        JDBC.openConnection();
        selectDivision.setText(divisionName);
        selectDivision.getItems().clear();
        selectDivision.getItems().removeAll();
        selectDivision.setDisable(false);
        String countryID = LoginQuery.getcountryID(countryName);
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


    public void saveChangesAction(ActionEvent actionEvent) {
    }

    public void setUpAction(ActionEvent actionEvent) {
        JDBC.openConnection();
        try {
            resultSet = LoginQuery.getCustomer(customerID);
            while(resultSet.next()){
                custIDField.setText(resultSet.getString("Customer_ID"));
                custNameField.setText(resultSet.getString("Customer_Name"));
                custAddressField.setText(resultSet.getString("Address"));
                custPostalField.setText(resultSet.getString("Postal_Code"));
                custPhoneField.setText(resultSet.getString("Phone"));
                Integer divisionID = resultSet.getInt("Division_ID");
                String countryID = LoginQuery.getCountryID(divisionID);
                divisionName = LoginQuery.getDivisionName(divisionID);
                countryName = LoginQuery.getcountryName(countryID);
                selectDivision.setText(divisionName);
                selectCountry.setText(countryName);

                populateDivisions(countryName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("could not find customer");
        }
        JDBC.closeConnection();
    }

    public void directToCustomers(ActionEvent actionEvent) {
    }
}
