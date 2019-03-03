
package library.settings;

import Messagess.MessageMaker;
import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SetingsInformation {
        public static final String CONFIG_FILE = "C:\\Users\\User\\Documents\\NetBeansProjects\\jsonFile.txt";

    int numberOfDaysWithouthFine ;
    double finePerDay;
    String username;
    String password;

    public SetingsInformation() {
        numberOfDaysWithouthFine = 14;
        finePerDay = 0.5;
        username = "denislav";
        password = "minchev";
    }

    public int getNumberOfDaysWithouthFine() {
        return numberOfDaysWithouthFine;
    }

    public void setNumberOfDaysWithouthFine(int numberOfDaysWithouthFine) {
        this.numberOfDaysWithouthFine = numberOfDaysWithouthFine;
    }

    public double getFinePerDay() {
        return finePerDay;
    }

    public void setFinePerDay(double finePerDay) {
        this.finePerDay = finePerDay;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
  public static void writeJsonObjectToFile(){
        Writer writer = null;
        try {  // putting obj into file as String
            
            SetingsInformation preference = new SetingsInformation();
            Gson gson = new Gson();
            writer = new FileWriter(CONFIG_FILE);
            gson.toJson(preference,writer);  
            
        } catch (IOException ex) {
            Logger.getLogger(SettingsController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(SettingsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
  
  public static SetingsInformation loadJsonObjectFromFile(){
      Gson gson = new Gson();
       SetingsInformation preferences = new SetingsInformation();
            try {
                 preferences = gson.fromJson(new FileReader(CONFIG_FILE), SetingsInformation.class); // priema 2 parametura ,faila ot koito chetem i class
            } catch (FileNotFoundException ex) {
                writeJsonObjectToFile(); // v sluchai che nqma zapisana informaciq , da se zapishe takava
                Logger.getLogger(SetingsInformation.class.getName()).log(Level.SEVERE, null, ex);
            }
            return preferences;
  }
    
    public static void writeNewPreferencesInFile(SetingsInformation preference){
         Writer writer = null;
        try {  // putting obj into file as String
            
            Gson gson = new Gson();
            writer = new FileWriter(CONFIG_FILE);
            gson.toJson(preference,writer); 
            
            MessageMaker.showSimpleMessage("SUCCESS", "Settings updated");
            
        } catch (IOException ex) {
             MessageMaker.showErrorMessage("FAILED", "Can't save configurations file");
            Logger.getLogger(SettingsController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(SettingsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
