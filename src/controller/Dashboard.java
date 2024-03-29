package controller;

import helper.JDBC;
import helper.LoginQuery;
import helper.Message;
import helper.TimeFunctions;
import helper.Testing;

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

import model.Appointments;

import java.io.IOException;

import java.net.URL;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.*;
import java.time.format.DateTimeFormatter;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class controls the Dashboard page.
 */
public class Dashboard implements Initializable {
    public Label appCount5;
    public Label appCount6;
    public Label appCount4;
    public Label appCount2;
    public Label appCount3;
    public Label appCount1;
    public Label appCount0;
    public Label dateLabel0;
    public Label dateLabel1;
    public Label dateLabel2;
    public Label dateLabel3;
    public Label dateLabel4;
    public Label dateLabel5;
    public Label dateLabel6;
    public Label dateLabelMonthText;
    public Label dateLabelNumText;

    public TextField apptSearchField;

    public TableView manageApptTable;

    public TableColumn appIDCol;
    public TableColumn titleCol;
    public TableColumn descriptionCol;
    public TableColumn locationCol;
    public TableColumn typeCol;
    public TableColumn startCol;
    public TableColumn endCol;
    public TableColumn custIDCol;
    public TableColumn contactCol;
    public TableColumn userIDCol;
    public RadioButton weekRB;
    public RadioButton monthRB;
    public RadioButton allRB;

    private ResultSet allApps;

    private Integer appointmentCount = 0;
    private Boolean noAppmnts;

    private ObservableList<Appointments> allAppsList = FXCollections.observableArrayList();
    private ObservableList<Appointments> monthList = FXCollections.observableArrayList();
    private ObservableList<Appointments> weekList = FXCollections.observableArrayList();
    private static Appointments modifiedAppt = null;

    private Testing UnitTest = new Testing();

    /**
     * @param url,resourceBundle used to initialize setDates() method.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Locale.setDefault(new Locale("en"));
        JDBC.openConnection();
        try {
            allApps = LoginQuery.getAllApps();
            allAppsList.removeAll();
            ZonedDateTime crntDate = ZonedDateTime.now();
            while (allApps.next()) {
                ZonedDateTime start = TimeFunctions.convertLocal(allApps.getTimestamp("Start").toLocalDateTime());
                ZonedDateTime end = TimeFunctions.convertLocal(allApps.getTimestamp("End").toLocalDateTime());
                Appointments new_appointment = new Appointments(allApps.getInt("Appointment_ID"),
                        allApps.getString("Title"),
                        allApps.getString("Description"),
                        allApps.getString("Location"),
                        allApps.getString("Type"),
                        start, end,
                        allApps.getInt("Customer_ID"),
                        allApps.getInt("User_ID"),
                        allApps.getInt("Contact_ID")
                );
                new_appointment.setContact(new_appointment.getContactID());
                UnitTest.ObjectTest(new_appointment);
                allAppsList.add(new_appointment);

                if (start.getMonth().equals(crntDate.getMonth())) {
                    monthList.add(new_appointment);
                }

                for (int i = 0; i < 7; i++) {
                    int startDay = start.getDayOfMonth();
                    int crntday = crntDate.plusDays(i).getDayOfMonth();
                    if (start.getMonth().equals(crntDate.getMonth()) && (startDay == crntday)) {
                        weekList.add(new_appointment);
                    }
                }
            }
            manageApptTable.setItems(allAppsList);
            appIDCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("appointmentID"));
            titleCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("title"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("description"));
            locationCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("location"));
            typeCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("type"));
            startCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("formattedStart"));
            endCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("formattedEnd"));
            custIDCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("customerID"));
            userIDCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("userID"));
            contactCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("contact"));

        } catch (SQLException e) {
            System.out.println("could not load customers");
        }
        JDBC.closeConnection();

        setDates();
        setAptCount();
        timeAlert();
    }

    /**
     * The timeAlert method contains a lambda expression with an if statement to alert the user of any upcoming appointments within a 15 min window.
     * This does not query the database, instead it checks an observable list.
     */
    public void timeAlert() {
        ZonedDateTime currentDateTime = ZonedDateTime.now().withSecond(0).withNano(0);
        noAppmnts = true;
        allAppsList.forEach((appts) -> { //lambda expression
            if (appts.timeDateStart.isBefore(currentDateTime.plusMinutes(15)) && (appts.timeDateStart.isAfter(currentDateTime)) || appts.timeDateStart.withSecond(0).isEqual(currentDateTime.withSecond(0).withNano(0))) {
                Message.informationDash("Appointment Alert", "Upcoming appointment within 15 minutes");
                noAppmnts = false;
            }
        });
        if (noAppmnts) {
            Message.informationDash("Appointment Alert", "You have no upcoming appointments");
        }
    }

    /**
     * The setDates method contains a for loop that adds 1 day to each label in listOfDates object.
     */
    private void setDates() {
        Label[] listOfDates = {dateLabel0, dateLabel1, dateLabel2, dateLabel3, dateLabel4, dateLabel5, dateLabel6};
        for (int i = 0; i < 7; i++) {
            listOfDates[i].setText(DateTimeFormatter.ofPattern("EEEE").format(ZonedDateTime.now().plusDays(i))
                    + " " + DateTimeFormatter.ofPattern("d").format(ZonedDateTime.now().plusDays(i)));
        }

        /** The dateLabelMonthText and dateLabelNumText are assigned the current month and date via the now() method and displayed in the "mini date display".*/
        dateLabelMonthText.setText(DateTimeFormatter.ofPattern("LLLL").format(ZonedDateTime.now()));
        dateLabelNumText.setText(DateTimeFormatter.ofPattern("d").format(ZonedDateTime.now()));
    }

    /**
     * The setAptCount counts the number of appointments by comparing the month and date of each appointment in the list of appointments using a lambda expression, with the current day of the month.
     */
    private void setAptCount() {
        JDBC.openConnection();
        Label[] listOfApts = {appCount0, appCount1, appCount2, appCount3, appCount4, appCount5, appCount6};
        for (int i = 0; i < 7; i++) {
            ZonedDateTime crntDate = ZonedDateTime.now().plusDays(i);
            appointmentCount = 0;
            allAppsList.forEach((appts) -> {
                int crntDay = crntDate.getDayOfMonth();
                int crntMonth = crntDate.getMonthValue();
                int day2 = appts.getTimeDateStart().getDayOfMonth();
                int month2 = appts.getTimeDateStart().getMonthValue();
                if (crntDay == day2 && crntMonth == month2) {
                    appointmentCount++;
                }
            });
            listOfApts[i].setText(String.valueOf(appointmentCount));
        }
        JDBC.closeConnection();
    }

    /**
     * @param actionEvent directToCustomers function used to redirect user to Customers form.
     */
    public void directToCustomers(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Customers");
        stage.setScene(new Scene(root, 1100, 590));
        stage.show();
    }

    /**
     * @param actionEvent directToReports function used to redirect user to Reports form.
     */
    public void directToReports(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Reports.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Reports");
        stage.setScene(new Scene(root, 1100, 590));
        stage.show();
    }

    /**
     * @param actionEvent cancelAction function used to redirect user to login form.
     */
    public void cancelAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Login");
        stage.setScene(new Scene(root, 810, 470));
        stage.show();
    }


    /**
     * @param actionEvent deleteAppmtAction function used to remove an appointment from database.
     */
    public void deleteAppmtAction(ActionEvent actionEvent) throws SQLException {
        model.Appointments selectedAppmt = (Appointments) manageApptTable.getSelectionModel().getSelectedItem();
        Integer appmtID = selectedAppmt.getAppointmentID();
        String type = selectedAppmt.getType();
        String deleteMessage = "Appointment ID: " + appmtID + " Type: " + type + " deleted.";
        if (selectedAppmt != null) {
            Optional<ButtonType> result = Message.confirmation("Delete Appointment", "Are you sure you want to delete this appointment?");
            if (result.isPresent() && result.get() == ButtonType.OK) {
                JDBC.openConnection();
                allAppsList.remove(selectedAppmt);
                monthList.remove(selectedAppmt);
                weekList.remove(selectedAppmt);
                LoginQuery.deleteAppointment(appmtID);
                JDBC.closeConnection();

                Message.information("Appointment Deleted", deleteMessage);
            }
        }
    }

    /**
     * @return getModifiedAppt method is used to retrieve appointment data to be modified.
     */
    public static Appointments getModifiedAppt() {
        return modifiedAppt;
    }

    /**
     * @param actionEvent modifyAppmtAction function used to redirect user to ModifyAppointments form.
     */
    public void modifyAppmtAction(ActionEvent actionEvent) throws IOException {
        modifiedAppt = (Appointments) manageApptTable.getSelectionModel().getSelectedItem();
        if (modifiedAppt != null) { // if a customer is not clicked it will == null
            Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyAppointments.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Modify Appointments");
            stage.setScene(new Scene(root, 1100, 590));
            stage.show();
        }
    }

    /**
     * @param actionEvent addAppmtAction function used to redirect user to AddAppointments form.
     */
    public void addAppmtAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddAppointments.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Add Appointments");
        stage.setScene(new Scene(root, 1100, 590));
        stage.show();
    }

    /**
     * @param actionEvent searchAppAction function used to search for a specific appointment first by title, then by ID via a button actionEvent.
     */
    public void searchAppAction(ActionEvent actionEvent) {
        String Q = apptSearchField.getText();
        ObservableList<Appointments> apptData = searchApptTitle(Q);
        if (apptData.size() == 0) {
            try {
                int queryID = Integer.parseInt(Q);
                Appointments appointment = searchApptID(queryID);
                if (appointment != null) {
                    apptData.add(appointment);
                    manageApptTable.setItems(apptData);
                    apptSearchField.setText("");
                } else {
                    manageApptTable.setItems(null);
                    apptSearchField.setText("");
                }
            } catch (Exception e) {
                manageApptTable.setItems(null);
                apptSearchField.setText("");
            }
        } else {
            manageApptTable.setItems(apptData);
            apptSearchField.setText("");
        }
    }

    /** @param apptTitle used to search for a specific appointment by title in the allAppts list via a button actionEvent.*/
    /**
     * @return nameResults returns a list of appointments matching the search criteria.
     */
    private ObservableList<Appointments> searchApptTitle(String apptTitle) {
        ObservableList<Appointments> nameResults = FXCollections.observableArrayList();
        ObservableList<Appointments> allAppts = allAppsList;

        for (Appointments names : allAppts) {
            if (names.getTitle().toLowerCase(Locale.ROOT).contains(apptTitle.toLowerCase(Locale.ROOT))) {
                nameResults.add(names);
            }
        }
        return nameResults;
    }

    /** @param apptID the appointment ID to find in the allAppts list.*/
    /** @return singleAppt the specific appointment from the list allAppts.*/
    /**
     * @return null if there is not an ID match in the allAppts list.
     */
    private Appointments searchApptID(Integer apptID) {
        ObservableList<Appointments> allAppts = allAppsList;
        for (int i = 0; i < allAppts.size(); i++) {
            Appointments singleAppt = allAppts.get(i);
            if (singleAppt.getAppointmentID() == apptID) {
                System.out.println("Match Found");
                return singleAppt;
            }
        }
        return null;
    }

    /**
     * @param actionEvent setTable sets the data in their specified fields in the allAppsList table.
     */
    public void setTable(ActionEvent actionEvent) {
        if (allRB.isSelected()) {
            manageApptTable.setItems(allAppsList);
            appIDCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("appointmentID"));
            titleCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("title"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("description"));
            locationCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("location"));
            typeCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("type"));
            startCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("formattedStart"));
            endCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("formattedEnd"));
            custIDCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("customerID"));
            userIDCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("userID"));
            contactCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("contact"));
        }
        if (monthRB.isSelected()) {
            manageApptTable.setItems(monthList);
            appIDCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("appointmentID"));
            titleCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("title"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("description"));
            locationCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("location"));
            typeCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("type"));
            startCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("formattedStart"));
            endCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("formattedEnd"));
            custIDCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("customerID"));
            userIDCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("userID"));
            contactCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("contact"));
        }
        if (weekRB.isSelected()) {
            manageApptTable.setItems(weekList);
            appIDCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("appointmentID"));
            titleCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("title"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("description"));
            locationCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("location"));
            typeCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("type"));
            startCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("formattedStart"));
            endCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("formattedEnd"));
            custIDCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("customerID"));
            userIDCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("userID"));
            contactCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("contact"));
        }
    }
}
