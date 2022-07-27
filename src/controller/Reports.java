package controller;

import helper.JDBC;
import helper.LoginQuery;
import helper.Message;
import helper.TimeFunctions;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
//import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import model.Appointments;

import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.sql.ResultSet;
import java.sql.SQLException;

//import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.net.URL;

/**
 * This class controls the Reports page.
 */
public class Reports implements Initializable {
    public MenuButton selectReport;
    public TextArea reportsTextArea;

    private ObservableList<Appointments> apptsList = FXCollections.observableArrayList();
    private String reportType;

    /**
     * @param url,resourceBundle initialize() method.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void generateReportAction(ActionEvent actionEvent) throws FileNotFoundException, SQLException {
        /*for (int i = 0; i < 100 ; i++) {
            String x = String.valueOf(i);
            reportsTextArea.appendText(x + "\n");
        }*/
        reportsTextArea.clear(); //clear each time button is pressed before running new report
        if (Objects.equals(reportType, "Login History")) {
            if (!loginHistoryReport()) {
                Message.error("Report Failure", "Failed to load report");
            }
        }
        if (Objects.equals(reportType, "Customer Appointments")) {
            if (!customerAppointmentsReport()) {
                Message.error("Report Failure", "Failed to load report");
            }
        }
        if (Objects.equals(reportType, "Customer Schedule")) {
            if (!customerScheduleReport()) {
                Message.error("Report Failure", "Failed to load report");
            }
        }
        if (Objects.equals(reportType, "Contact Schedule")) {
            if (!contactScheduleReport()) {
                Message.error("Report Failure", "Failed to load report");
            }
        }
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
     * @param actionEvent loginHistoryAction function used to set the user's selection text to the dropdown menu.
     */
    public void loginHistoryAction(ActionEvent actionEvent) {
        selectReport.setText("Login History");
        reportType = "Login History";
    }

    /**
     * @return true loginHistoryReport method used to scan the readerObj to count the number of successful and failed login attempts and prints the data in the reportsTextArea.
     */
    private Boolean loginHistoryReport() throws FileNotFoundException {
        File readerObj = new File("login_activity.txt");
        Scanner scanner = new Scanner(readerObj);
        Integer success = 0, fail = 0;

        while (scanner.hasNextLine()) {
            String data = scanner.nextLine();
            reportsTextArea.appendText(data + "\n");
            int loginSuccess = data.indexOf("success");
            if (loginSuccess > 0) {
                success += 1;
            } else {
                fail += 1;
            }
        }
        scanner.close();
        reportsTextArea.appendText("\nNumber of successful logins: " + success + "\n");
        reportsTextArea.appendText("Number of failed login attempts: " + fail + "\n");
        return true;
    }

    /**
     * @param actionEvent customerAppmtAction function used to set the user's selection text to the dropdown menu.
     */
    public void customerAppmtAction(ActionEvent actionEvent) {
        selectReport.setText("Number of Appointments by Type and Month");
        reportType = "Customer Appointments";
    }

    /**
     * @return true customerAppointmentsReport function used to open a connection to the database to retrieve all appointments types and months and prints data in reportsTextArea.
     * lambda expression
     */
    private Boolean customerAppointmentsReport() throws SQLException {
        JDBC.openConnection();
        ZoneId UTC = ZoneId.of("UTC");
        ResultSet appmnts = LoginQuery.getAllApps();

        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"}; //list of months
        Set<String> individualTypes = new HashSet<String>(); //set used to hold individual types

        ArrayList<String> types = new ArrayList(); //list of all types in appointments result set
        ArrayList<String> apptMonths = new ArrayList(); //list of all months that have appointments in result set

        reportsTextArea.appendText("Appointments by Month Totals\n\n");

        for (String allMonth : months) {
            Integer count = 0;
            for (String month : apptMonths) {
                if (allMonth.toUpperCase().equals(month)) {
                    System.out.println("Match");
                    count += 1;
                }
            }
            reportsTextArea.appendText(allMonth + ": " + count + "\n");
        }

        reportsTextArea.appendText("\n\nAppointments by Type Totals\n\n");

        while (appmnts.next()) {
            String type = appmnts.getString("Type");
            types.add(type);
            LocalDateTime startDateTime = LocalDateTime.from(appmnts.getTimestamp("Start").toLocalDateTime().atZone(UTC));
            ZonedDateTime localDate = TimeFunctions.convertLocal(startDateTime);
            String month = localDate.getMonth().toString();
            apptMonths.add(month);
        }

        types.forEach((type) -> {
            individualTypes.add(type);
        });

        for (String individualType : individualTypes) { // individualTypes is the "no-duplicate list"
            Integer count = 0;
            for (String allTypes : types) {
                if (Objects.equals(individualType, allTypes)) {
                    count += 1;
                }
            }
            reportsTextArea.appendText(individualType + ": " + count + "\n");
        }


        JDBC.closeConnection();
        return true;
    }

    /**
     * @param actionEvent contactSchedAction function used to set the user's selection text to the dropdown menu.
     */
    public void contactSchedAction(ActionEvent actionEvent) {
        selectReport.setText("Contact Schedule");
        reportType = "Contact Schedule";
    }

    /**
     * @return true customerScheduleReport function used to open a connection to the database to retrieve all contacts with their appointment information and print the data in reportsTextArea.
     * lambda expression
     */
    private Boolean customerScheduleReport() throws SQLException {
        JDBC.openConnection();
        ZoneId UTC = ZoneId.of("UTC");
        ResultSet appmnts = LoginQuery.getAllApps();
        ResultSet customers = LoginQuery.getAllCustomers();
        ArrayList<Integer> customerIDs = new ArrayList<Integer>();
        while (customers.next()) {
            customerIDs.add(customers.getInt("Customer_ID"));
        }
        while (appmnts.next()) {
            ZonedDateTime start = TimeFunctions.convertLocal(appmnts.getTimestamp("Start").toLocalDateTime());
            ZonedDateTime end = TimeFunctions.convertLocal(appmnts.getTimestamp("End").toLocalDateTime());
            Appointments new_appointment = new Appointments(appmnts.getInt("Appointment_ID"),
                    appmnts.getString("Title"),
                    appmnts.getString("Description"),
                    appmnts.getString("Location"),
                    appmnts.getString("Type"),
                    start, end,
                    appmnts.getInt("Customer_ID"),
                    appmnts.getInt("User_ID"),
                    appmnts.getInt("Contact_ID")
            );
            new_appointment.setContact(new_appointment.getContactID());
            apptsList.add(new_appointment);
        }
        reportsTextArea.appendText("All Customer Appointment Dates & Times\n\n");
        customerIDs.forEach((ID) -> { // lambda
            try {
                String customerName = LoginQuery.getCustomerName(ID);
                reportsTextArea.appendText(customerName + " Appointments:\n");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            apptsList.forEach((appointment) -> {
                        if (Objects.equals(ID, appointment.getCustomerID())) {
                            reportsTextArea.appendText("     -  " + appointment.getTimeDateStart() + " -- " + appointment.getTimeDateEnd() + "\n");
                        }
                    }
            );
        });
        JDBC.closeConnection();
        return true;
    }

    /**
     * @return true contactScheduleReport function used to open a connection to the database to retrieve all contacts with their appointment information and print the data in reportsTextArea.
     * lambda expression
     */
    private Boolean contactScheduleReport() throws SQLException {
        JDBC.openConnection();
        ZoneId UTC = ZoneId.of("UTC");
        ResultSet appmnts = LoginQuery.getAllApps();
        ResultSet contacts = LoginQuery.getAllContacts();
        ArrayList<Integer> contactIDs = new ArrayList<Integer>();
        while (contacts.next()) {
            contactIDs.add(contacts.getInt("Contact_ID"));
        }
        while (appmnts.next()) {
            ZonedDateTime start = TimeFunctions.convertLocal(appmnts.getTimestamp("Start").toLocalDateTime());
            ZonedDateTime end = TimeFunctions.convertLocal(appmnts.getTimestamp("End").toLocalDateTime());
            Appointments new_appointment = new Appointments(appmnts.getInt("Appointment_ID"),
                    appmnts.getString("Title"),
                    appmnts.getString("Description"),
                    appmnts.getString("Location"),
                    appmnts.getString("Type"),
                    start, end,
                    appmnts.getInt("Customer_ID"),
                    appmnts.getInt("User_ID"),
                    appmnts.getInt("Contact_ID")
            );
            new_appointment.setContact(new_appointment.getContactID());
            apptsList.add(new_appointment);
        }
        reportsTextArea.appendText("All Contact Appointment Dates & Times\n\n");
        contactIDs.forEach((ID) -> { // nested lambda
            try {
                String contactName = LoginQuery.getContact(ID);
                reportsTextArea.appendText(contactName + " Appointments:\n");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            apptsList.forEach((appointment) -> { // nested lambda
                        if (Objects.equals(ID, appointment.getContactID())) {
                            reportsTextArea.appendText("     -  " + appointment.getTimeDateStart() + " -- " + appointment.getTimeDateEnd() + "\n");
                        }
                    }
            );
        });
        JDBC.closeConnection();
        return true;
    }



    public void custSchedAction(ActionEvent actionEvent) {
        selectReport.setText("Customer Schedule");
        reportType = "Customer Schedule";
    }
}
