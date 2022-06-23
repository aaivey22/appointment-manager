package controller;

import helper.JDBC;
import helper.LoginQuery;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

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
    public TextField noteDisplay;


    /** @param url,resourceBundle used to initialize setDates() method.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setDates();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            setAptCount();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** The setDates method contains a for loop that adds 1 day to each label in listOfDates object.*/
    private void setDates() throws SQLException {
        Label[] listOfDates = {dateLabel0, dateLabel1, dateLabel2, dateLabel3, dateLabel4, dateLabel5, dateLabel6};
        for (int i = 0; i < 7; i++){
            listOfDates[i].setText(DateTimeFormatter.ofPattern("EEEE").format(ZonedDateTime.now().plusDays(i))
                    + " " + DateTimeFormatter.ofPattern("d").format(ZonedDateTime.now().plusDays(i)));
        }

        /** The dateLabelMonthText and dateLabelNumText are assigned the current month and date via the now() method and displayed in the "mini date display".*/
        dateLabelMonthText.setText(DateTimeFormatter.ofPattern("LLLL").format(ZonedDateTime.now()));
        dateLabelNumText.setText(DateTimeFormatter.ofPattern("d").format(ZonedDateTime.now()));
    }

    /** The setAptCount method opens a connection to the database to count the number of appointments in the 'appointments' column for the current and following 6 days. */
    private void setAptCount() throws SQLException {
        JDBC.openConnection();
        Label[] listOfApts = {appCount0, appCount1, appCount2, appCount3, appCount4, appCount5, appCount6};
        for (int i = 0; i < 7; i++){
            ZonedDateTime crntDate = ZonedDateTime.now().plusDays(i);
            int appointmentCount = LoginQuery.getNumRecords(String.valueOf(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(crntDate)));
            listOfApts[i].setText(String.valueOf(appointmentCount));
        }
        JDBC.closeConnection();
    }

    /** @param actionEvent directToAppointments function used to redirect user to Appointments form.*/
    public void directToAppointments(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Appointments");
        stage.setScene(new Scene(root, 1100, 590));
        stage.show();
    }

    /** @param actionEvent directToCustomers function used to redirect user to Customers form.*/
    public void directToCustomers(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Customers");
        stage.setScene(new Scene(root, 1100, 590));
        stage.show();
    }

    /** @param actionEvent directToReports function used to redirect user to Reports form.*/
    public void directToReports(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Reports.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Reports");
        stage.setScene(new Scene(root, 1100, 590));
        stage.show();
    }

    /** @param actionEvent cancelAction function used to redirect user to login form.*/
    public void cancelAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Login");
        stage.setScene(new Scene(root, 810, 470));
        stage.show();
    }

    public void updateNoteAction(ActionEvent actionEvent) {
    }
}
