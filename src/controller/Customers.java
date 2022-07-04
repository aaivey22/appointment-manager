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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;

import model.customer;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import helper.LoginQuery;

public class Customers implements Initializable {
    public Button toDashboard;
    public ResultSet allCustomers;
    public TextField searchCustField;
    public TableColumn custIDCol;
    public TableColumn custContactCol;
    public TableColumn custNameCol;
    public TableColumn custAddressCol;
    public TableColumn custStateCol;
    public TableColumn custCountryCol;
    public TableColumn custPostCol;
    public TableColumn custPhoneCol;
    public TableView customersTable;

    /** @param url,resourceBundle used to initialize the allCustomers method.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        JDBC.openConnection();

        try {
            allCustomers = LoginQuery.getAllCustomers();
            ObservableList<customer> allCustomersList = FXCollections.observableArrayList();

            customer Ruby = new customer("10", "Ruby Sinke", "123 Dog Drive", "34645", "434-656-5745", 60);
            allCustomersList.add(Ruby);
            while(allCustomers.next()) {
                customer new_customer = new customer(allCustomers.getString("Customer_ID"),
                        allCustomers.getString("Customer_Name"),
                        allCustomers.getString("Address"),
                        allCustomers.getString("Postal_Code"),
                        allCustomers.getString("Phone"),
                        allCustomers.getInt("Division_ID"));
                allCustomersList.add(new_customer);
                //System.out.println(new_customer.getCustomerID());
            }
            customersTable.setItems(allCustomersList);
            custIDCol.setCellValueFactory(new PropertyValueFactory<customer, String>("customerID"));
            System.out.println(allCustomersList.size());

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

    public void deleteCustomerAction(ActionEvent actionEvent) {
    }

    public void modifyCustomerAction(ActionEvent actionEvent) {
    }

    public void searchCustomerAction(ActionEvent actionEvent) {
    }
}
