package controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.RadioButton;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * created class AddPartForm.java  @author Angela Ivey
 */
/** @author Angela Ivey */

/** This class controls the LoginForm page.*/
public class LoginForm implements Initializable {

    public Label greeting;
    public RadioButton freRadioButton;
    public RadioButton engRadioButton;
    public Label countryLabel;

    /** @param url,resourceBundle used to initialize translate() method.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        translate();

    }

    /** @param actionEvent used to retrieve default language setting and set it to == the user's selected radio button.*/
    public void translate() {
        if (freRadioButton.isSelected()) {
            Locale.setDefault(new Locale("fr"));
        } else if (engRadioButton.isSelected()) {
            Locale.setDefault(new Locale("en"));
        }
        ResourceBundle rb = ResourceBundle.getBundle("main/lang", Locale.getDefault());

        /** @param actionEvent used to retrieve default language setting via Locale object to check if it == "fr" and will change it to === "en".*/
        if (Locale.getDefault().getLanguage().equals("fr")) {
            greeting.setText(rb.getString("greeting"));
            countryLabel.setText(rb.getString("countryLabel"));


        } else if (Locale.getDefault().getLanguage().equals("en")) {
            greeting.setText(rb.getString("greeting"));
            countryLabel.setText(rb.getString("countryLabel"));
        }

    }

}
