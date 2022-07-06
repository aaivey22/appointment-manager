package controller;
import helper.JDBC;
import helper.LoginQuery;
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

import helper.LoginQuery;

public class Customers implements Initializable {
    public Button toDashboard;
    public ResultSet allCustomers;
    public TextField searchCustField;
    public TableColumn custIDCol;
    public TableColumn custNameCol;
    public TableColumn custAddressCol;
    public TableColumn custStateCol;
    public TableColumn custCountryCol;
    public TableColumn custPostCol;
    public TableColumn custPhoneCol;
    public TableView customersTable;
    public ObservableList<customer> allCustomersList = FXCollections.observableArrayList();

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
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Customer");
            alert.setContentText("Are you sure you want to delete this customer?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                JDBC.openConnection();

                allCustomersList.remove(selectedCustomer);
                LoginQuery.deleteCustomer(customerID);

                JDBC.closeConnection();
            }
        }
    }

    public void modifyCustomerAction(ActionEvent actionEvent) {
    }

    /** RUNTIME ERROR after running program, name search did not work, but id search did, was missing second else statement to set partNames.
     *If no names are found when searching and the query is not a number, an error is thrown when converting query into an integer because it's trying to convert a character into an int.
     *The solution is to add the try catch exception.
     *@param actionEvent used to search for a specific part first by name, then by ID in the allParts list via a button actionEvent.*/
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
    /** @param partName used to search for a specific part by name in the allParts list via a button actionEvent.*/
    /** @return nameResults returns a list of parts matching the search criteria.*/
    private ObservableList<customer> searchCustName(String customerName) {
        ObservableList<customer> nameResults = FXCollections.observableArrayList();
        ObservableList<customer> allCustomers = allCustomersList;

        for (customer names : allCustomers) {
            if (names.getCustomerName().contains(customerName)) {
                nameResults.add(names);
            }
        }
        return nameResults;
    }

    /** @param partID the part ID to find in the allParts list.*/
    /** @return partid the specific part from the list allParts.*/
    /** @return null if there is not an ID match in the allParts list.*/
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

