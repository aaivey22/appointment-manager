package controller;

import helper.JDBC;
import helper.LoginQuery;

import helper.TimeFunctions;
import javafx.event.ActionEvent;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.stage.Stage;
import model.Appointments;

import java.io.IOException;

import java.net.URL;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class ModifyAppointments implements Initializable {
    public TextField apptID;
    public TextField appTypeField;
    public TextField locationField;
    public TextField titleField;
    public TextField descriptionField;
    public MenuButton selectCustomer;
    public MenuButton selectUser;
    public MenuButton selectContact;
    public DatePicker apptStartDate;
    public ComboBox appStartTime;
    public ComboBox appEndTime;
    public Label appEndDate;

    private static Appointments modifiedAppt = null;
    private Integer appointmentID;
    private Integer customerID;
    private Integer userID;
    private Integer contactID;
    private String customerName;
    private String userName;
    private String contactName;
    private ResultSet resultSet;

    private String customer = "";
    private String contact = "";
    private String user = "";

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateTimes();
        try {
            populateCustomers();
            populateContact();
            populateUsers();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        modifiedAppt = Dashboard.getModifiedAppt();
        appointmentID = Integer.valueOf(modifiedAppt.getAppointmentID());
        setUpAction(null);
    }

    /**
     * @param actionEvent setUpAction function that retrieves customer data and populates the fields with it.
     */
    public void setUpAction(ActionEvent actionEvent) {
        JDBC.openConnection();

        try {
            appointmentID = Integer.valueOf(modifiedAppt.getAppointmentID());
            resultSet = LoginQuery.getAppointment(appointmentID);
            while (resultSet.next()) {
                apptID.setText(resultSet.getString("Appointment_ID"));
                appTypeField.setText(resultSet.getString("Type"));
                locationField.setText(resultSet.getString("Location"));
                titleField.setText(resultSet.getString("Title"));
                descriptionField.setText(resultSet.getString("Description"));
                customerID = resultSet.getInt("Customer_ID");
                userID = resultSet.getInt("User_ID");
                contactID = resultSet.getInt("Contact_ID");
                customerName = LoginQuery.getCustomerName(customerID);
                userName = LoginQuery.getUserName(userID);
                contactName = LoginQuery.getContact(contactID);
                selectCustomer.setText(customerName);
                selectUser.setText(userName);
                selectContact.setText(contactName);
                startDateTime = LocalDateTime.from(TimeFunctions.convertLocal(LocalDateTime.from(LoginQuery.getStartUTC(appointmentID)))); //Converts from a UTC ZonedDateTime to the LocalDateTime in the current system timezone
                endDateTime = LocalDateTime.from(TimeFunctions.convertLocal(LocalDateTime.from(LoginQuery.getEndUTC(appointmentID)))); //Converts from a UTC ZonedDateTime to the LocalDateTime in the current system timezone
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("could not find appointment");
        }
        JDBC.closeConnection();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm");

        appStartTime.getSelectionModel().select(startDateTime.format(formatter));
        appEndTime.getSelectionModel().select(endDateTime.format(formatter));
        apptStartDate.setValue(startDateTime.toLocalDate());

    }


    public void directToDashboard(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Dashboard");
        stage.setScene(new Scene(root, 1100, 590));
        stage.show();
    }

    public void saveApptChanges(ActionEvent actionEvent) {
    }

    /**
     * The populateTimes method populates combo boxes with 10 minute increments beginning at 8:00 and ending at 10:00.
     */
    private void populateTimes() {
        LocalTime start = LocalTime.of(8, 00);
        LocalTime end = LocalTime.of(22, 0);
        while (start.isBefore(end.plusSeconds(1))) {
            appStartTime.getItems().add(start);
            appEndTime.getItems().add(start);
            start = start.plusMinutes(10);
        }
    }

    /**
     * The populateCustomers method opens a database connection and retrieves the customer column data.
     */
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

    /**
     * @param actionEvent selectCustAction function fires when the user selects a customer from the menu list, assigning their selection to the menu button label.
     */
    public EventHandler<ActionEvent> selectCustAction =
            new EventHandler<ActionEvent>() {
                public void handle(ActionEvent ae) {
                    customer = ((MenuItem) ae.getSource()).getText();
                    selectCustomer.setText(((MenuItem) ae.getSource()).getText());
                }
            };

    /**
     * The populateContact method opens a database connection and retrieves the user contact data.
     */
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

    /**
     * @param actionEvent selectContactAction function fires when the user selects a contact from the menu list, assigning their selection to the menu button label.
     */
    public EventHandler<ActionEvent> selectContactAction =
            new EventHandler<ActionEvent>() {
                public void handle(ActionEvent ae) {
                    contact = ((MenuItem) ae.getSource()).getText();
                    selectContact.setText(((MenuItem) ae.getSource()).getText());
                }
            };

    /**
     * The populateUsers method opens a database connection and retrieves the user column data.
     */
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

    /**
     * @param actionEvent selectUserAction function fires when the user selects a 'user' from the menu list, assigning their selection to the menu button label.
     */
    public EventHandler<ActionEvent> selectUserAction =
            new EventHandler<ActionEvent>() {
                public void handle(ActionEvent ae) {
                    user = ((MenuItem) ae.getSource()).getText();
                    selectUser.setText(((MenuItem) ae.getSource()).getText());
                }
            };

    public void resetAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyAppointments.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Modify Appointments");
        stage.setScene(new Scene(root, 1100, 590));
        stage.show();
    }
}
