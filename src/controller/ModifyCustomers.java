package controller;

import helper.JDBC;
import helper.Message;
import helper.LoginQuery;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.stage.Stage;
import model.customer;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;



public class ModifyCustomers implements Initializable {
    public TextField custIDField;
    public TextField custNameField;
    public TextField custAddressField;
    public TextField custPostalField;
    public TextField custPhoneField;

    public MenuButton selectDivision;
    public MenuButton selectCountry;

    private customer modifiedCustomer = null;

    private ResultSet resultSet;

    private Integer customerID;

    private String divisionName;
    private String countryName;
    private String country = "";
    private String division = "";
    private String divisionID = "";
    private String name;
    private String streetAddress;
    private String phoneNum;
    private String postalCode;


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

    /** The populateCountries method opens a connection to the database, retrieves the country column data  to set as menu items. The menu item selected by the user is then assigned to the menu button label.*/
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

    /** @param actionEvent saveChangesAction function fires when the user clicks save changes. The data is then stored in the database table.*/
    public void saveChangesAction(ActionEvent actionEvent) throws IOException {
        name = custNameField.getText();
        streetAddress = custAddressField.getText();
        postalCode = custPostalField.getText();
        phoneNum = custPhoneField.getText();
        customerID = Integer.valueOf(custIDField.getText());
        division = selectDivision.getText();
        country = selectCountry.getText();
        int rowsModified = 0;

        JDBC.openConnection();
        try {
            divisionID = LoginQuery.getDivisionID(division);
        } catch (SQLException e) {
            System.out.println("failed to get division id");
        }
        JDBC.closeConnection();

        if(name.length() > 0 && streetAddress.length() > 0 && postalCode.length() > 0 && phoneNum.length() > 0 && country.length() > 0 && division.length() > 0 && divisionID.length() > 0)
        {
            JDBC.openConnection();
            try {
                rowsModified = LoginQuery.modifyCustomer(name, streetAddress, postalCode, phoneNum, divisionID, customerID);
            } catch (SQLException e) {
                System.out.println("failed to get division id");
            }
            JDBC.closeConnection();

            if( rowsModified > 0) {
                Message.information("Success", "Customer Updated");
                Parent root = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setTitle("Customers");
                stage.setScene(new Scene(root, 1100, 590));
                stage.show();
            } else
            {
                System.out.println("customer could not be modified");
            }

        }else{
            Message.information("Missing Information", "All fields are required");
        }

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

    public void directToCustomers(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Customers");
        stage.setScene(new Scene(root, 1100, 590));
        stage.show();
    }
}
