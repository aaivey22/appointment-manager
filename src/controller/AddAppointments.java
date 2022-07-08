package controller;

import helper.JDBC;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class AddAppointments implements Initializable {
    public TextField descriptionField;
    public TextField titleField;
    public TextField apptID;
    public TextField locationField;
    public TextField appNameField;

    public MenuButton selectCustomer;
    public MenuButton selectUser;
    public MenuButton selectContact;
    public MenuButton appStartTime;
    public MenuButton appEndTime;

    public DatePicker apptStartDate;

    public Label appEndDate;

    public String customer;
    public String contact;
    public String user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            populateCustomers();
            populateContact();
            populateUsers();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** The populateCustomers method opens a connection to the database and with the help of an imported function, retrieves the user column data.*/
    private void populateCustomers() throws SQLException {
        JDBC.openConnection();
        java.util.List<String> listofCustomers = LoginQuery.getCustNames();
        java.util.List<MenuItem> countriesMenuItems = new ArrayList<MenuItem>();
        for (int i = 0; i < listofCustomers.size(); i++) {
            countriesMenuItems.add(new MenuItem(listofCustomers.get(i)));
            countriesMenuItems.get(i).setOnAction(selectCustAction);
            selectCustomer.getItems().add(countriesMenuItems.get(i));
        }
        JDBC.closeConnection();
    }

    /** @param actionEvent selectCustAction function fires when the user selects a customer from the menu list and assigns their selection to the menu button label.*/
    public EventHandler<ActionEvent> selectCustAction =
            new EventHandler<ActionEvent>() {
                public void handle(ActionEvent ae) {
                    customer = ((MenuItem) ae.getSource()).getText();
                    selectCustomer.setText(((MenuItem) ae.getSource()).getText());
                }
            };

    /** The populateCountries method opens a connection to the database and with the help of an imported function, retrieves the user column data.*/
    private void populateContact() throws SQLException {
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

    /** @param actionEvent selectContactAction function fires when the user selects a contact from the menu list and assigns their selection to the menu button label.*/
    public EventHandler<ActionEvent> selectContactAction =
            new EventHandler<ActionEvent>() {
                public void handle(ActionEvent ae) {
                    contact = ((MenuItem) ae.getSource()).getText();
                    selectContact.setText(((MenuItem) ae.getSource()).getText());
                }
            };

    /** The populateUsers method opens a connection to the database and with the help of an imported function, retrieves the user column data.*/
    private void populateUsers() throws SQLException {
        JDBC.openConnection();
        java.util.List<String> listofUsers = LoginQuery.getUsers();
        java.util.List<MenuItem> usersMenuItems = new ArrayList<MenuItem>();
        for (int i = 0; i < listofUsers.size(); i++) {
            usersMenuItems.add(new MenuItem(listofUsers.get(i)));
            usersMenuItems.get(i).setOnAction(selectUserAction);
            selectUser.getItems().add(usersMenuItems.get(i));
        }
        JDBC.closeConnection();
    }

    /** @param actionEvent selectUserAction function fires when the user selects a user from the menu list and assigns their selection to the menu button label.*/
    public EventHandler<ActionEvent> selectUserAction =
            new EventHandler<ActionEvent>() {
                public void handle(ActionEvent ae) {
                    user = ((MenuItem) ae.getSource()).getText();
                    selectUser.setText(((MenuItem) ae.getSource()).getText());
                }
            };

    public void saveApptChanges(ActionEvent actionEvent) {
    }

    public void directToDashboard(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Dashboard");
        stage.setScene(new Scene(root, 1100, 590));
        stage.show();
    }

    public void clearApptChanges(ActionEvent actionEvent) {
        descriptionField.setText("");
        titleField.setText("");
        apptID.setText("");
        locationField.setText("");
        appNameField.setText("");
        selectCustomer.setText("Select customer");
        selectCustomer.getItems().clear();
        selectCustomer.getItems().removeAll();
        selectUser.setText("Select user");
        selectUser.getItems().clear();
        selectUser.getItems().removeAll();
        selectContact.setText("Select contact");
        selectContact.getItems().clear();
        selectContact.getItems().removeAll();
        customer = "";
        user = "";
        contact = "";

    }
}
