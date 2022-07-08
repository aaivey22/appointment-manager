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
    public MenuButton selectCustomer;
    public TextField titleField;
    public TextField apptID;
    public MenuButton selectType;
    public MenuButton selectUser;
    public MenuButton selectContact;
    public TextField locationField;
    public DatePicker apptStartDate;
    public TextField appNameField;
    public MenuButton appStartTime;
    public MenuButton appEndTime;
    public Label appEndDate;
    public String customer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            populateCustomers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** The populateCountries method opens a connection to the database and with the help of an imported function, retrieves the country column data and assigns the user selection to the menu button label.*/
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

    /** @param actionEvent selectDivisionAction function fires when the user selects a Division from the menu list.*/
    public EventHandler<ActionEvent> selectCustAction =
            new EventHandler<ActionEvent>() {
                public void handle(ActionEvent ae) {
                    customer = ((MenuItem) ae.getSource()).getText();
                    selectCustomer.setText(((MenuItem) ae.getSource()).getText());
                }
            };


    public void saveApptChanges(ActionEvent actionEvent) {
    }

    /** The populateContacts method opens a connection to the database and with the help of an imported function, retrieves the contact name column data and assigns the user selection to the menu button label.*/
      /*  private void populateContacts() throws SQLException {
            JDBC.openConnection();
            java.util.List<String> listofContacts = LoginQuery.getContacts();
            java.util.List<MenuItem> contactsMenuItems = new ArrayList<MenuItem>();
            for (int i = 0; i < listofContacts.size(); i++) {
                contactsMenuItems.add(new MenuItem(listofContacts.get(i)));
                contactsMenuItems.get(i).setOnAction(selectContactAction);
                selectContact.getItems().add(contactsMenuItems.get(i));
            }
            JDBC.closeConnection();
        } */

    /** @param actionEvent selectContactAction function fires when the user selects a Contact from the menu list.*/
    /* public EventHandler<ActionEvent> selectContactAction =
            new EventHandler<ActionEvent>() {
                public void handle(ActionEvent ae) {
                    contact = ((MenuItem) ae.getSource()).getText();
                    selectContact.setText(contact);
                }
            }; */


    public void directToDashboard(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Dashboard");
        stage.setScene(new Scene(root, 1100, 590));
        stage.show();
    }

    public void clearApptChanges(ActionEvent actionEvent) {
    }
}
