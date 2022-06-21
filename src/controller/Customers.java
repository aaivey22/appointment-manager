package controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class Customers implements Initializable {
    public Button toDashboard;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    /** @param actionEvent directToDashboard function used to redirect user to Dashboard form.*/
    public void directToDashboard(ActionEvent actionEvent) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(root, 1100, 590));
            stage.show();
    }
}
