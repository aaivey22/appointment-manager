package controller;
import helper.JDBC;
import helper.LoginQuery;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class ModifyAppointments implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

}
