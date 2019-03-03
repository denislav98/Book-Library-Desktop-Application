
package library.displayIssueList;

import bussinessLogic.IssueListLogic;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class IssueListController implements Initializable {

    private  ObservableList<IssueInfo> list = FXCollections.observableArrayList();

    
    @FXML
    private TableView<IssueInfo> tableView;
    @FXML
    private TableColumn<IssueInfo, Integer> idCol;
    @FXML
    private TableColumn<IssueInfo, String> bookIDCol;
    @FXML
    private TableColumn<IssueInfo, String> bookNameCol;
    @FXML
    private TableColumn<IssueInfo, String> holderNameCol;
    @FXML
    private TableColumn<IssueInfo, String> issueCol;
    @FXML
    private TableColumn<IssueInfo, Integer> daysCol;
    @FXML
    private TableColumn<IssueInfo, Double> fineCol;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol();
        try {
            loadData();
        } catch (Exception ex) {
            Logger.getLogger(IssueListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    
    private void initCol() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        bookIDCol.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        bookNameCol.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        holderNameCol.setCellValueFactory(new PropertyValueFactory<>("holderName"));
        issueCol.setCellValueFactory(new PropertyValueFactory<>("dateOfIssue"));
        daysCol.setCellValueFactory(new PropertyValueFactory<>("days"));
        fineCol.setCellValueFactory(new PropertyValueFactory<>("fine"));
        tableView.setItems(list);
    }

    
    private void loadData() throws Exception {
        IssueListLogic.displayingIssueListLogic(list,tableView);
    }
    
    @FXML
    private void deleteBook(ActionEvent event) {
    }

}
