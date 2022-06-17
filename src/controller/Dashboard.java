package controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class Dashboard implements Initializable {
    public Label appCount5;
    public Label appCount6;
    public Label appCount4;
    public Label appCount2;
    public Label appCount3;
    public Label appCount1;
    public Label appCount0;
    public Label dateLabel0;
    public Label dateLabel1;
    public Label dateLabel2;
    public Label dateLabel3;
    public Label dateLabel4;
    public Label dateLabel5;
    public Label dateLabel6;

    /** @param url,resourceBundle used to initialize setDates() method.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDates();
        setAptCount();
    }

    /** The setDates method contains a for loop that adds 1 day to each label in listOfDates object.*/
    private void setDates() {
        Label[] listOfDates = {dateLabel0, dateLabel1, dateLabel2, dateLabel3, dateLabel4, dateLabel5, dateLabel6};
        for (int i = 0; i < 7; i++){
            listOfDates[i].setText(DateTimeFormatter.ofPattern("EEEE").format(ZonedDateTime.now().plusDays(i))
                    + " " + DateTimeFormatter.ofPattern("d").format(ZonedDateTime.now().plusDays(i)));
        }
    }

    private void setAptCount() {
        Label[] listOfApts = {appCount0, appCount1, appCount2, appCount3, appCount4, appCount5, appCount6};
        for (int i = 0; i < 7; i++){
            listOfApts[i].setText(DateTimeFormatter.ofPattern("d").format(ZonedDateTime.now().plusDays(i)));
        }
    }

    /** @param actionEvent cancelAction function used to redirect user to login form.*/
    public void cancelAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Login");
        stage.setScene(new Scene(root, 810, 470));
        stage.show();
    }
}
