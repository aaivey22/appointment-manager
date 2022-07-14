package controller;

import helper.JDBC;
import helper.LoginQuery;
import helper.Message;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.Stage;

import java.io.IOException;

import model.customer;

import java.net.URL;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;


/** This class controls the Customers page.*/
public class Customers implements Initializable {
    private ResultSet allCustomers;
    public TextField searchCustField;
    public TableColumn custIDCol;
    public TableColumn custNameCol;
    public TableColumn custAddressCol;
    public TableColumn custStateCol;
    public TableColumn custCountryCol;
    public TableColumn custPostCol;
    public TableColumn custPhoneCol;
    public TableView customersTable;
    private ObservableList<customer> allCustomersList = FXCollections.observableArrayList();
    private static customer modifiedCustomer = null;

    /** @param url,resourceBundle used to initialize the allCustomers method.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        JDBC.openConnection();

        try {
            allCustomers = LoginQuery.getAllCustomers();
            allCustomersList.removeAll();
            while(allCustomers.next()) {
                customer new_customer = new customer(allCustomers.getString("Customer_ID"),
                        allCustomers.getString("Customer_Name"),
                        allCustomers.getString("Address"),
                        allCustomers.getString("Postal_Code"),
                        allCustomers.getString("Phone"),
                        allCustomers.getInt("Division_ID"));
                new_customer.setCountryName();
                new_customer.setDivisionName();

                allCustomersList.add(new_customer);
            }

            customersTable.setItems(allCustomersList);
            custIDCol.setCellValueFactory(new PropertyValueFactory<customer, String>("customerID"));
            custNameCol.setCellValueFactory(new PropertyValueFactory<customer, String>("customerName"));
            custAddressCol.setCellValueFactory(new PropertyValueFactory<customer, String>("address"));
            custStateCol.setCellValueFactory(new PropertyValueFactory<customer, String>("divisionName"));
            custCountryCol.setCellValueFactory(new PropertyValueFactory<customer, String>("countryName"));
            custPostCol.setCellValueFactory(new PropertyValueFactory<customer, String>("postalCode"));
            custPhoneCol.setCellValueFactory(new PropertyValueFactory<customer, String>("phone"));


        } catch (SQLException e) {
            System.out.println("could not load customers");
        }
        JDBC.closeConnection();

    }
    /** @param actionEvent directToDashboard function used to redirect user to Dashboard form.*/
    public void directToDashboard(ActionEvent actionEvent) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(root, 1100, 590));
            stage.show();
    }

    /** @param actionEvent addCustomerAction function used to redirect user to Add Customers form.*/
    public void addCustomerAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddCustomers.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Add Customers");
        stage.setScene(new Scene(root, 1100, 590));
        stage.show();
    }

    /** @param actionEvent deleteCustomerAction function used to remove a customer from database.*/
    public void deleteCustomerAction(ActionEvent actionEvent) throws SQLException {
        customer selectedCustomer = (customer)customersTable.getSelectionModel().getSelectedItem();
        String customerID = selectedCustomer.getCustomerID();
        if (selectedCustomer != null) {
            Optional<ButtonType> result = Message.confirmation("Delete Customer", "Are you sure you want to delete this customer?");
            if (result.isPresent() && result.get() == ButtonType.OK) {
                JDBC.openConnection();
                allCustomersList.remove(selectedCustomer);
                LoginQuery.deleteCustomer(customerID);
                JDBC.closeConnection();
            }
        }
    }

    /** @return modifiedCustomer used to retrieve customer data to be modified.*/
    public static customer  getModifiedCustomer() {
        return modifiedCustomer;
    }

    /** @param actionEvent modifyCustomerAction function used to redirect user to Modify Customers form whenever a customer row is clicked.*/
    public void modifyCustomerAction(ActionEvent actionEvent) throws IOException {
        modifiedCustomer = (customer) customersTable.getSelectionModel().getSelectedItem();
        if (modifiedCustomer != null) { // if a customer is not clicked it will == null
            Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyCustomers.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Modify Customers");
            stage.setScene(new Scene(root, 1100, 590));
            stage.show();
        }
    }

    /** @param actionEvent searchCustomerAction function used to search for a specific customer first by name, then by ID via a button actionEvent.*/
    public void searchCustomerAction(ActionEvent actionEvent) {
            String Q = searchCustField.getText();
            ObservableList<customer> customerData = searchCustName(Q);
            if (customerData.size() == 0) {
                try {
                    int queryID = Integer.parseInt(Q);
                    customer Customer = searchCustomerID(queryID);
                    if (Customer != null) {
                        customerData.add(Customer);
                        customersTable.setItems(customerData);
                        searchCustField.setText("");
                    } else {
                        customersTable.setItems(null);
                        searchCustField.setText("");
                    }
                } catch (Exception e) {
                    customersTable.setItems(null);
                    searchCustField.setText("");
                }
            } else {
                customersTable.setItems(customerData);
                searchCustField.setText("");
            }
        }

    /** @param customerName used to search for a specific customer by name in the allCustomers list via a button actionEvent.*/
    /** @return nameResults returns a list of customers matching the search criteria.*/
    private ObservableList<customer> searchCustName(String customerName) {
        ObservableList<customer> nameResults = FXCollections.observableArrayList();
        ObservableList<customer> allCustomers = allCustomersList;

        for (customer names : allCustomers) {
            if (names.getCustomerName().toLowerCase(Locale.ROOT).contains(customerName.toLowerCase(Locale.ROOT))) {
                nameResults.add(names);
            }
        }
        return nameResults;
    }

    /** @param searchCustomerID the customer ID to find in the allCustomers list.*/
    /** @return singleCustomer the specific customer from the list allCustomers.*/
    /** @return null if there is not an ID match in the allCustomers list.*/
    private customer searchCustomerID(Integer customerID) {
        ObservableList<customer> allCustomers = allCustomersList;
        for (int i = 0; i < allCustomers.size(); i++) {
            customer singleCustomer = allCustomers.get(i);
            if (singleCustomer.getCustomerIDint() == customerID) {
                System.out.println("Match Found");
                return singleCustomer;
            }
        }
        return null;
    }
}

