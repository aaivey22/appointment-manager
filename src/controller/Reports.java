package controller;
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

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

/** This class controls the Reports page.*/
public class Reports implements Initializable {
    public MenuButton selectDivision;
    public TextArea reportsTextArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void generateReportAction(ActionEvent actionEvent) {
        for (int i = 0; i < 100 ; i++) {
            String x = String.valueOf(i);
            reportsTextArea.appendText(x + "\n");
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
    }

    public void customerAppmtAction(ActionEvent actionEvent) {
        selectDivision.setText("Customer Appointments");
    }

    public void contactSchedAction(ActionEvent actionEvent) {
        selectDivision.setText("Contact Schedule");
    }
}
