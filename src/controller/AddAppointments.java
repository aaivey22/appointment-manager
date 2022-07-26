package controller;

import helper.JDBC;
import helper.LoginQuery;
import helper.Message;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;


/**
 * This class controls the AddAppointments page.
 */
public class AddAppointments implements Initializable {
    public TextField descriptionField;
    public TextField titleField;
    public TextField apptID;
    public TextField locationField;
    public TextField appTypeField;

    public MenuButton selectCustomer;
    public MenuButton selectUser;
    public MenuButton selectContact;

    public ComboBox appStartTime;
    public ComboBox appEndTime;

    public DatePicker apptStartDate;

    public Label appEndDate;

    private String customer = "";
    private String contact = "";
    private String user = "";

    private String type;
    private String location;
    private String title;
    private String description;
    private String customerID;
    private String contactID;
    private String userID;

    private LocalDate datefield;
    private LocalTime timeStartField;
    private LocalTime timeEndField;
    private ZonedDateTime StartDateTime;
    private LocalDateTime UTCStart;
    private ZonedDateTime EndDateTime;
    private LocalDateTime UTCEnd;

    /**
     * @param url,resourceBundle used to initialize the populateCustomers, populateContact, populateUsers methods.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        apptStartDate.setValue(LocalDate.now());
        populateTimes();
        try {
            populateCustomers();
            populateContact();
            populateUsers();

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
     * The populateCustomers method opens a database connection and retrieves the customer data.
     */
    private void populateCustomers() throws SQLException {
        JDBC.openConnection();
        java.util.List<String> listofCustomers = LoginQuery.getCustNames();
        java.util.List<MenuItem> customersMenuItems = new ArrayList<MenuItem>();
        for (int i = 0; i < listofCustomers.size(); i++) {
            customersMenuItems.add(new MenuItem(listofCustomers.get(i)));
            customersMenuItems.get(i).setOnAction(selectCustAction);
            selectCustomer.getItems().add(customersMenuItems.get(i));
        }
        JDBC.closeConnection();
    }

    /**
     * @param ae selectCustAction function fires when the user selects a customer from the menu list, assigning their selection to the menu button label.
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
     * @param ae selectContactAction function fires when the user selects a contact from the menu list, assigning their selection to the menu button label.
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
     * @param ae selectUserAction function fires when the user selects a 'user' from the menu list, assigning their selection to the menu button label.
     */
    public EventHandler<ActionEvent> selectUserAction =
            new EventHandler<ActionEvent>() {
                public void handle(ActionEvent ae) {
                    user = ((MenuItem) ae.getSource()).getText();
                    selectUser.setText(((MenuItem) ae.getSource()).getText());
                }
            };

    /**
     * @param actionEvent saveApptChanges function fires when the user clicks save changes. The data is then stored in the database table.
     */
    public void saveApptChanges(ActionEvent actionEvent) throws SQLException {
        Integer rowsModified = 0;
        type = appTypeField.getText();
        location = locationField.getText();
        title = titleField.getText();
        description = descriptionField.getText();
        datefield = apptStartDate.getValue();
        timeStartField = (LocalTime) appStartTime.getValue();
        timeEndField = (LocalTime) appEndTime.getValue();
        if (apptStartDate.getValue() != null && timeStartField != null && timeEndField != null) {
            if (timeStartField.isBefore(timeEndField)) {
                StartDateTime = TimeFunctions.combineDateTime(datefield, timeStartField);
                UTCStart = LocalDateTime.from(TimeFunctions.convertUTC(StartDateTime));

                //ZoneId UTC = ZoneId.of("UTC");
                EndDateTime = TimeFunctions.combineDateTime(datefield, timeEndField);
                UTCEnd = LocalDateTime.from(TimeFunctions.convertUTC(EndDateTime));

                JDBC.openConnection();
                Boolean overlap = TimeFunctions.isOverlap(UTCStart, UTCEnd);
                JDBC.closeConnection();

                if (overlap) {
                    Message.information("Appointment Conflict", "This appointment time overlaps with another");
                } else {

                    if (customer.length() > 0 && contact.length() > 0 && user.length() > 0) {
                        JDBC.openConnection();
                        try {
                            customerID = LoginQuery.getCustomerID(customer);
                            contactID = LoginQuery.getContactID(contact);
                            userID = LoginQuery.getUserID(user);
                        } catch (SQLException e) {
                            System.out.println("failed");
                        }
                        if (customerID.length() > 0 && contactID.length() > 0 && userID.length() > 0 && type.length() > 0 && location.length() > 0
                                && title.length() > 0 && description.length() > 0) {
                            try {
                                rowsModified = LoginQuery.addAppointment(title, description, location, type, UTCStart, UTCEnd, customerID, userID, contactID);
                            } catch (SQLException e) {
                                e.printStackTrace();
                                Message.error("Failed", "Appointment could not be added");
                            }
                            if (rowsModified > 0) {
                                Message.information("Success", "New appointment added");
                                clearApptChanges(null); // using null because the function is being directly called without an action event such as a button click.
                            } else {
                                Message.information("Failed", "Appointment could not be added");
                            }

                        } else {
                            Message.information("Missing Information", "All fields are required.");
                        }
                    } else {
                        Message.error("Missing Information", "All fields are required.");
                    }
                }

            } else {
                Message.information("Time Issue", "Start time must be before end time.");
            }
        } else {
            Message.error("Missing Information", "Missing Date or Times");
        }
        JDBC.closeConnection();

    }

    /**
     * @param actionEvent directToDashboard function used to redirect user to Dashboard form.
     */
    public void directToDashboard(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Dashboard");
        stage.setScene(new Scene(root, 1100, 590));
        stage.show();
    }

    /**
     * @param actionEvent clearApptChanges function fires when the user clicks clear. This resets all the form fields to their original state.
     */
    public void clearApptChanges(ActionEvent actionEvent) {
        descriptionField.setText("");
        titleField.setText("");
        apptID.setText("");
        locationField.setText("");
        appTypeField.setText("");
        selectCustomer.setText("Select customer");
        selectUser.setText("Select user");
        selectContact.setText("Select contact");
        customer = "";
        user = "";
        contact = "";
        appStartTime.getSelectionModel().clearSelection();
        appStartTime.setPromptText("Select Start Time");
        appEndTime.getSelectionModel().clearSelection();
        appEndTime.setPromptText("Select End Time");
        apptStartDate.setValue(LocalDate.now());
    }
}
