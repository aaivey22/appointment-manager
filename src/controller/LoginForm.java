package controller;

import helper.JDBC;
import helper.LoginQuery;
import helper.Message;

import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.io.File;
import java.io.FileWriter;

import java.net.URL;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.stage.Stage;

/**
 * created class LoginForm.java  @author Angela Ivey
 *
 * @author Angela Ivey
 * @author Angela Ivey
 * @author Angela Ivey
 * @author Angela Ivey
 */
/** @author Angela Ivey */

/** This class controls the LoginForm page.*/
public class LoginForm implements Initializable {

    public Label greeting;
    public Label timeZoneLabel;
    public Label dateDisplayLabel;
    public Label userNameLabel;
    public Label passwordLabel;
    public Label selectLangLabel;

    public RadioButton freRadioButton;
    public RadioButton engRadioButton;

    public Button loginButton;
    public Button registerButton;

    public TextField userNameField;
    public TextField passwordField;

    public Hyperlink forgotPwordLink;

    private String userNameInput;
    private String userPwordInput;
    private String alertText = "The user name and or password entered are incorrect.";
    private String alertTitle = "Incorrect Login";
    private String retrievePwordText = "Contact the IT department at @helpdesk to reset your password.";
    private String retrievePwordTitle = "Password Help";

    /** @param url,resourceBundle used to initialize currentDateTime.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ZonedDateTime currentDateTime = ZonedDateTime.now();
        dateDisplayLabel.setText(DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm").format(currentDateTime));
        timeZoneLabel.setText(String.valueOf(currentDateTime.getZone()));
    }

    /** The translate method that contains actions that will take place when a language radio btn is selected.*/
    public void translate() {
        /** @param actionEvent used to retrieve default language setting and set it to == the user's selected radio button.*/
        if (freRadioButton.isSelected()) {
            Locale.setDefault(new Locale("fr"));
        }
        if (engRadioButton.isSelected()) {
            Locale.setDefault(new Locale("en"));
        }
        ResourceBundle rb = ResourceBundle.getBundle("main/lang", Locale.getDefault());

        /** @param actionEvent used to retrieve default language setting via Locale object to check if it == "fr" and will change it to === "en".*/
        if (Locale.getDefault().getLanguage().equals("fr")) {
            greeting.setText(rb.getString("greeting"));
            freRadioButton.setText(rb.getString("freRadioButton"));
            engRadioButton.setText(rb.getString("engRadioButton"));
            registerButton.setText(rb.getString("registerButton"));
            userNameField.setPromptText(rb.getString("userNameField"));
            passwordField.setPromptText(rb.getString("passwordField"));
            userNameLabel.setText(rb.getString("userNameLabel"));
            passwordLabel.setText(rb.getString("passwordLabel"));
            forgotPwordLink.setText(rb.getString("forgotPwordLink"));
            selectLangLabel.setText(rb.getString("selectLangLabel"));
            loginButton.setText(rb.getString("loginButton"));
            alertText = "Le nom d'utilisateur et/ou le mot de passe saisis sont incorrects.";
            retrievePwordText = "Contactez le service informatique de @helpdesk pour réinitialiser votre mot de passe.";
            alertTitle = "Login incorrect";
            retrievePwordTitle = "Aide sur le mot de passe";
        } else if (Locale.getDefault().getLanguage().equals("en")) {
            greeting.setText(rb.getString("greeting"));
            freRadioButton.setText(rb.getString("freRadioButton"));
            engRadioButton.setText(rb.getString("engRadioButton"));
            registerButton.setText(rb.getString("registerButton"));
            userNameField.setPromptText(rb.getString("userNameField"));
            passwordField.setPromptText(rb.getString("passwordField"));
            userNameLabel.setText(rb.getString("userNameLabel"));
            passwordLabel.setText(rb.getString("passwordLabel"));
            forgotPwordLink.setText(rb.getString("forgotPwordLink"));
            selectLangLabel.setText(rb.getString("selectLangLabel"));
            loginButton.setText(rb.getString("loginButton"));
            alertText = "The user name and or password entered are incorrect.";
            retrievePwordText = "Contact the IT department at @helpdesk to reset your password.";
            alertTitle = "Incorrect Login";
            retrievePwordTitle = "Password Help";
        }
    }

    /**  @param actionEvent loginAction function used to execute username and pword authentication via the LoginQuery. */
    /** If the user is authenticated, they will be redirected to their dashboard page.*/
    /** Else the user is NOT authenticated, the alertText variable will be displayed in an error message.
     * Will write to log files upon successful or failed authentication*/
    public void loginAction(ActionEvent actionEvent) throws SQLException, IOException {
        userNameInput = userNameField.getText();
        userPwordInput = passwordField.getText();
        JDBC.openConnection();
        if (LoginQuery.authenticate(userNameInput, userPwordInput)) {
            writeLog(true);
            Parent root = FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("My Dashboard");
            stage.setScene(new Scene(root, 1100, 590));
            stage.show();
        } else {
            writeLog(false);
            Message.error(alertTitle, alertText);
        }
        JDBC.closeConnection();
    }

    /**  @param login writeLog function used to write io log to text file. */
    public void writeLog(Boolean login) throws IOException {
        String status;
        if (login) status = "success";
        else status = "failed";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm");
        ZonedDateTime currentDateTime = ZonedDateTime.now();
        File log = new File("login_activity.txt");
        if (log.createNewFile()) {
            FileWriter fw = new FileWriter("login_activity.txt", true);
            fw.write(currentDateTime.format(formatter) + " [username] " + userNameField.getText() + " [login status] " + status + " \n");
            fw.close();
        } else {
            FileWriter fw = new FileWriter("login_activity.txt", true);
            fw.write(currentDateTime.format(formatter) + " [username] " + userNameField.getText() + " [login status] " + status + " \n");
            fw.close();
        }
    }


    /**  @param actionEvent retrievePword function used to execute an informational alert. */
    public void retrievePword(ActionEvent actionEvent) {
        Message.information(retrievePwordTitle, retrievePwordText);
    }

    /**  @param actionEvent registerUser function used to redirect user to the RegisterUser page. */
    public void registerUser(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/RegisterUser.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Register User");
        stage.setScene(new Scene(root, 810, 470));
        stage.show();
    }

}
