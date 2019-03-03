package library.addUser;

import DatabaseModels.Queries;
import Messagess.MessageMaker;
import bussinessLogic.AddUserLogic;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import library.database.DatabaseHandler;

public class AddUserController implements Initializable {

    @FXML
    private JFXTextField name;
    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField phone;
    @FXML
    private JFXTextField mail;
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton cancelButton;

    DatabaseHandler handler;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            handler = DatabaseHandler.getInstance();
            AddUserLogic.checkData(handler);

        } catch (Exception ex) {
            Logger.getLogger(AddUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void addUser(ActionEvent event) {

        String userName = name.getText();
        String userId = id.getText();
        String mobile = phone.getText();
        String userEmail = mail.getText();

        AddUserLogic.addingUserLogic(userName, userId, mobile, mobile);

        Queries.insertIntoUserTable(userId, userName, mobile, userEmail, handler);

    }

    @FXML
    private void cancelBook(ActionEvent event) {
        ((Stage) name.getScene().getWindow()).close();
    }

}
