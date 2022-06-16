package controller;

import helper.JDBC;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

import helper.LoginQuery;

/**
 * created class AddPartForm.java  @author Angela Ivey
 */
/** @author Angela Ivey */

/** This class controls the LoginForm page.*/
public class LoginForm implements Initializable {

    public Label greeting;
    public RadioButton freRadioButton;
    public RadioButton engRadioButton;
    public Label timeZoneLabel;
    public Label dateDisplayLabel;
    public Hyperlink registerLink;
    public TextField userNameField;
    public TextField passwordField;
    public Label userNameLabel;
    public Label passwordLabel;
    public Hyperlink forgotPwordLink;
    public Label selectLangLabel;
    public Button loginButton;
    public String userNameInput;
    public String userPwordInput;


    /** @param url,resourceBundle used to initialize translate() method.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ZonedDateTime currentDateTime = ZonedDateTime.now();
        dateDisplayLabel.setText(DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm").format(currentDateTime));
        timeZoneLabel.setText(String.valueOf(currentDateTime.getZone()));
    }

    /** The translate method that contains actions that will take place when specific user events occur.*/
    public void translate() {
        /** @param actionEvent used to retrieve default language setting and set it to == the user's selected radio button.*/
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
            registerLink.setText(rb.getString("registerLink"));
            userNameField.setPromptText(rb.getString("userNameField"));
            passwordField.setPromptText(rb.getString("passwordField"));
            userNameLabel.setText(rb.getString("userNameLabel"));
            passwordLabel.setText(rb.getString("passwordLabel"));
            forgotPwordLink.setText(rb.getString("forgotPwordLink"));
            selectLangLabel.setText(rb.getString("selectLangLabel"));
            loginButton.setText(rb.getString("loginButton"));
        } else if (Locale.getDefault().getLanguage().equals("en")) {
            greeting.setText(rb.getString("greeting"));
            freRadioButton.setText(rb.getString("freRadioButton"));
            engRadioButton.setText(rb.getString("engRadioButton"));
            registerLink.setText(rb.getString("registerLink"));
            userNameField.setPromptText(rb.getString("userNameField"));
            passwordField.setPromptText(rb.getString("passwordField"));
            userNameLabel.setText(rb.getString("userNameLabel"));
            passwordLabel.setText(rb.getString("passwordLabel"));
            forgotPwordLink.setText(rb.getString("forgotPwordLink"));
            selectLangLabel.setText(rb.getString("selectLangLabel"));
            loginButton.setText(rb.getString("loginButton"));
        }

    }

    /** loginAction function used to execute username and pword authentication via the LoginQuery. */
    public void loginAction() throws SQLException {
        userNameInput = userNameField.getText();
        userPwordInput = passwordField.getText();
        JDBC.openConnection();
        //LoginQuery.insert(userNameInput, userPwordInput);
        if(LoginQuery.authenticate(userNameInput, userPwordInput)){
            System.out.println("Authenticated");
        } else System.out.println("Incorrect Login");


        JDBC.closeConnection();

    }

}
