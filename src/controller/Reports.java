package controller;
import helper.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

/** This class controls the Reports page.*/
public class Reports implements Initializable {
    public MenuButton selectDivision;
    public TextArea reportsTextArea;

    private String reportType;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void generateReportAction(ActionEvent actionEvent) throws FileNotFoundException {
        /*for (int i = 0; i < 100 ; i++) {
            String x = String.valueOf(i);
            reportsTextArea.appendText(x + "\n");
        }*/
        if (reportType == "Login History"){
            if(!loginHistoryReport()){
                Message.error("Report Failure", "Failed to load report");
            }
        }
        if (reportType == "Customer Appointments"){
            if(!customerAppointmentsReport()){
                Message.error("Report Failure", "Failed to load report");
            }
        }
        if (reportType == "Contact Schedule"){
            if(!contactScheduleReport()){
                Message.error("Report Failure", "Failed to load report");
            }
        }
    }


    public void directToDashboard(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Dashboard");
        stage.setScene(new Scene(root, 1100, 590));
        stage.show();
    }

    public void loginHistoryAction(ActionEvent actionEvent) {
        selectDivision.setText("Login History");
        reportType = "Login History";
    }

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
    };

    public void customerAppmtAction(ActionEvent actionEvent) {
        selectDivision.setText("Customer Appointments");
        reportType = "Customer Appointments";

    }

    private Boolean customerAppointmentsReport(){
        return true;
    };

    public void contactSchedAction(ActionEvent actionEvent) {
        selectDivision.setText("Contact Schedule");
        reportType = "Contact Schedule";

    }

    private Boolean contactScheduleReport(){
        return true;
    };
}
