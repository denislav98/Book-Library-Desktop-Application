
package Messagess;


public class MessageMaker {
    
    public static void showSimpleMessage(String title,String content){
    javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                alert.setTitle(title);
                alert.setHeaderText(null);
                alert.setContentText(content);
                alert.showAndWait();
    }
    
    public static void showErrorMessage(String title,String content){
         javafx.scene.control.Alert alert2 = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
                alert2.setTitle("ERROR");
                alert2.setHeaderText(title);
                alert2.setContentText(content);
                alert2.showAndWait();
    }
    
    public static void showConfirmationMessage(String title,String content){
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
    }
    
    
    
}
