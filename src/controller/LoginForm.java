package controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        translate_fr();

    }

    public void translate_fr() {

        ResourceBundle rb = ResourceBundle.getBundle("main/lang", Locale.getDefault());

        if (Locale.getDefault().getLanguage().equals("fr")) {
            System.out.println(rb.getString("hello") + " " + rb.getString("world"));
            System.out.println(rb.getString("greeting"));
            greeting.setText(rb.getString("greeting"));

        }
    }

}
