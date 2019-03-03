
package library.settings;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;


public class SettingsController implements Initializable {
    

    @FXML
    private JFXTextField daysWithouthFine;
    @FXML
    private JFXTextField finePerDay;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeDefaulValues();
    }    

    @FXML
    private void saveButtonAction(ActionEvent event) {
        
        int nOfDaysWithouthFine =Integer.parseInt(daysWithouthFine.getText());
        float fine = Float.parseFloat(finePerDay.getText());
        String userName = username.getText();
        String userPassword = password.getText();
        
        SetingsInformation preferences = SetingsInformation.loadJsonObjectFromFile();
        preferences.setNumberOfDaysWithouthFine(nOfDaysWithouthFine);
        preferences.setFinePerDay(fine);
        preferences.setUsername(userName);
        preferences.setPassword(userPassword);
        
        SetingsInformation.writeNewPreferencesInFile(preferences);
        
    }

    @FXML
    private void cancelButtonAction(ActionEvent event) {
        ((Stage) daysWithouthFine.getScene().getWindow()).close();
    }
    
    private void initializeDefaulValues(){
        SetingsInformation preferences = SetingsInformation.loadJsonObjectFromFile();
        
        daysWithouthFine.setText(String.valueOf(preferences.getNumberOfDaysWithouthFine()));
        finePerDay.setText(String.valueOf(preferences.getFinePerDay()));
        username.setText(String.valueOf(preferences.getUsername()));
        password.setText(String.valueOf(preferences.getPassword()));
    }
   
}
