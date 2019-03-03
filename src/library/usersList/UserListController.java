
package library.usersList;
import bussinessLogic.DisplayingUserListLogic;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class UserListController implements Initializable {

    @FXML
    private TableView<User> tableView;
    @FXML
    private TableColumn<User,String> nameCol;
    @FXML
    private TableColumn<User,String > idCol;
    @FXML
    private TableColumn<User,String > mobileCol;
    @FXML
    private TableColumn<User,String > emailCol;
    
    ObservableList<User> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            initCol();
            DisplayingUserListLogic.loadDataFromDataBase(list, tableView);
        } catch (Exception ex) {
            Logger.getLogger(UserListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    private void initCol() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        mobileCol.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
    }
}
