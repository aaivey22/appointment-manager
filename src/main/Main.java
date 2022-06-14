package main;

/** Created class Main.java */
/** @author Angela Ivey */

import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.*;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

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
//        ZoneId.getAvailableZoneIds().stream().forEach(System.out::println);
//        ZoneId.getAvailableZoneIds().stream().filter(c-> c.contains("Europe")).forEach(System.out::println);

        LocalDate parisDate = LocalDate.of(2022, 05, 22);
        LocalTime parisTime = LocalTime.of(01, 00);
        ZoneId parisZoneId = ZoneId.of("Europe/Paris");
        ZonedDateTime parisZDT = ZonedDateTime.of(parisDate, parisTime, parisZoneId);
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());

        Instant parisToGMTInstant = parisZDT.toInstant();
        ZonedDateTime parisToLocalZDT = parisZDT.withZoneSameInstant(localZoneId);
        ZonedDateTime gmtToLocalZDT = parisToGMTInstant.atZone(localZoneId);
        ZonedDateTime.now();
        System.out.println("Local: " + ZonedDateTime.now());
        System.out.println("Paris: " + parisZDT);
        System.out.println("Paris->GMT: " + parisToGMTInstant);
        System.out.println("GMT->LocalDate: " + gmtToLocalZDT.toLocalDate());
        System.out.println("GMT->LocalTime: " + gmtToLocalZDT.toLocalTime());

        String date = String.valueOf(gmtToLocalZDT.toLocalDate());
        String time = String.valueOf(gmtToLocalZDT.toLocalTime());
        String dateTime = date + " " + time;
        System.out.println(dateTime);

        System.out.println(parisZDT);
        System.out.println(localZoneId);

        JDBC.openConnection();
        JDBC.closeConnection();
 //       Locale.setDefault(new Locale("fr"));
        ResourceBundle rb = ResourceBundle.getBundle("main/lang", Locale.getDefault());

        launch(args);
        System.out.println("goodbye");
    }
}
