
package icons;

import javafx.scene.image.Image;
import javafx.stage.Stage;
public class windowIcon {
    private static String IMAGE = "/icons/open-book.png";
    
    public static void setStageIcon(Stage stage){
        stage.getIcons().add(new Image(IMAGE));
            
        }
}
