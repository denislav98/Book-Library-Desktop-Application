
package library.settings;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import library.database.DatabaseHandler;

public class SettingsLoader extends Application {
     
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("settings.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Settings");
        
        // za po-burzoto otvarqne na main menuto 
        new Thread(() -> {
            try { 
                DatabaseHandler.getInstance();
            } catch (Exception ex) {
                Logger.getLogger(SettingsLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}
