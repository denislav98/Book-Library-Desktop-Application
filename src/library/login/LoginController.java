package library.login;

import Messagess.MessageMaker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import icons.windowIcon;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import library.main.MainController;
import library.settings.SetingsInformation;

public class LoginController implements Initializable {

    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;

    SetingsInformation preferences;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        preferences = SetingsInformation.loadJsonObjectFromFile();
    }

    @FXML
    private void loadLoginButtonAction(ActionEvent event) {

        String userName = username.getText();
        String userPassword = password.getText();
        
        if (userName.equals(preferences.getUsername()) && userPassword.equals(preferences.getPassword())) {
            closeStage();
            loadMainWindow();
        } else {
           MessageMaker.showErrorMessage("Wrong Information", "Invalid username or password");
        }
    }

    @FXML
    private void loadCancelButtonAction(ActionEvent event) {
        System.exit(0);

    }

    private void closeStage() {
        ((Stage) username.getScene().getWindow()).close();
    }

    void loadMainWindow() {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/library/main/main.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Book Library");
            stage.setScene(new Scene(parent));
            stage.show();
            windowIcon.setStageIcon(stage);
            
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
