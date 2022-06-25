package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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
    public DatePicker apptEndDate;
    public Button toDashboard;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void saveApptChanges(ActionEvent actionEvent) {
    }

    public void goBackAction(ActionEvent actionEvent) {
    }

    public void directToDashboard(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Dashboard");
        stage.setScene(new Scene(root, 1100, 590));
        stage.show();
    }
}
