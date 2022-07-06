package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class AddAppointments implements Initializable {
    public TextField descriptionField;
    public MenuButton selectCustomer;
    public TextField titleField;
    public TextField apptID;
    public MenuButton selectType;
    public MenuButton selectUser;
    public MenuButton selectContact;
    public TextField locationField;
    public DatePicker apptStartDate;
    public DatePicker apptEndDate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void saveApptChanges(ActionEvent actionEvent) {
    }

    /** The populateContacts method opens a connection to the database and with the help of an imported function, retrieves the contact name column data and assigns the user selection to the menu button label.*/
      /*  private void populateContacts() throws SQLException {
            JDBC.openConnection();
            java.util.List<String> listofContacts = LoginQuery.getContacts();
            java.util.List<MenuItem> contactsMenuItems = new ArrayList<MenuItem>();
            for (int i = 0; i < listofContacts.size(); i++) {
                contactsMenuItems.add(new MenuItem(listofContacts.get(i)));
                contactsMenuItems.get(i).setOnAction(selectContactAction);
                selectContact.getItems().add(contactsMenuItems.get(i));
            }
            JDBC.closeConnection();
        } */

    /** @param actionEvent selectContactAction function fires when the user selects a Contact from the menu list.*/
    /* public EventHandler<ActionEvent> selectContactAction =
            new EventHandler<ActionEvent>() {
                public void handle(ActionEvent ae) {
                    contact = ((MenuItem) ae.getSource()).getText();
                    selectContact.setText(contact);
                }
            }; */


    public void directToDashboard(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Dashboard");
        stage.setScene(new Scene(root, 1100, 590));
        stage.show();
    }

    public void clearApptChanges(ActionEvent actionEvent) {
    }
}
