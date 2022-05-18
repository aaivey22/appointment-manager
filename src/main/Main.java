package main;

/** Created class Main.java */
/** @author Angela Ivey */

import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

/** This class fires the appointment manager app.
 * The Java Docs folder is located in the project directory. The folder is called "javadoc"
 * FUTURE ENHANCEMENT the entire project should be translatable in multiple languages and also include enhancements for various disabilities.*/


public class Main extends Application {
    /** This is the start method that loads the landing page LoginForm.*/
    @Override
    public void start(Stage primaryStage) throws Exception {
        //addTestData();
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        primaryStage.setTitle("Appointment Management System");
        primaryStage.setScene(new Scene(root, 810, 470));
        primaryStage.show();
    }

    /** This is the main method that launches the program.*/
    public static void main(String[] args) {
        JDBC.openConnection();
        JDBC.closeConnection();
        Locale.setDefault(new Locale("fr"));
        ResourceBundle rb = ResourceBundle.getBundle("main/lang", Locale.getDefault());

        launch(args);
        System.out.println("goodbye");
    }
}
