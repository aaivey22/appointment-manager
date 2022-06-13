package controller;

import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;

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
    public Label timeZoneLabel;
    public Hyperlink registerLink;
    public TextField userNameField;
    public TextField passwordField;
    public Label userNameLabel;
    public Label passwordLabel;
    public Hyperlink forgotPwordLink;
    public Label selectLangLabel;
    public Button loginButton;

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
            freRadioButton.setText(rb.getString("freRadioButton"));
            engRadioButton.setText(rb.getString("engRadioButton"));
            countryLabel.setText(rb.getString("countryLabel"));
            timeZoneLabel.setText(rb.getString("timeZoneLabel"));
            registerLink.setText(rb.getString("registerLink"));
            userNameField.setText(rb.getString("userNameField"));
            passwordField.setText(rb.getString("passwordField"));
            userNameLabel.setText(rb.getString("userNameLabel"));
            passwordLabel.setText(rb.getString("passwordLabel"));
            forgotPwordLink.setText(rb.getString("forgotPwordLink"));
            selectLangLabel.setText(rb.getString("selectLangLabel"));
            loginButton.setText(rb.getString("loginButton"));
        } else if (Locale.getDefault().getLanguage().equals("en")) {
            greeting.setText(rb.getString("greeting"));
            freRadioButton.setText(rb.getString("freRadioButton"));
            engRadioButton.setText(rb.getString("engRadioButton"));
            countryLabel.setText(rb.getString("countryLabel"));
            timeZoneLabel.setText(rb.getString("timeZoneLabel"));
            registerLink.setText(rb.getString("registerLink"));
            userNameField.setText(rb.getString("userNameField"));
            passwordField.setText(rb.getString("passwordField"));
            userNameLabel.setText(rb.getString("userNameLabel"));
            passwordLabel.setText(rb.getString("passwordLabel"));
            forgotPwordLink.setText(rb.getString("forgotPwordLink"));
            selectLangLabel.setText(rb.getString("selectLangLabel"));
            loginButton.setText(rb.getString("loginButton"));
        }

    }

}
