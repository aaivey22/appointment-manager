package controller;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class ModifyCustomers implements Initializable {
    public TextField custIDField;
    public TextField custNameField;
    public TextField custAddressField;
    public TextField custPostalField;
    public TextField custPhoneField;
    public MenuButton custStateField;
    public MenuButton custCountryField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void saveChangesAction(ActionEvent actionEvent) {
    }

    public void revertChangesAction(ActionEvent actionEvent) {
    }

    public void custStateAction(ActionEvent actionEvent) {
    }

    public void custSCountryAction(ActionEvent actionEvent) {
    }

    public void directToCustomers(ActionEvent actionEvent) {
    }
}
