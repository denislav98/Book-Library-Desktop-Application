
package library.displayAllBooks;

import bussinessLogic.DisplayingBookLogic;
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
import javafx.scene.layout.AnchorPane;

public class AllBooksListController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<BookModel> tableView;
    @FXML
    private TableColumn<BookModel, String> titleCol;
    @FXML
    private TableColumn<BookModel, String> idCol;
    @FXML
    private TableColumn<BookModel, String> authorCol;
    @FXML
    private TableColumn<BookModel, String> publisherCol;
    @FXML
    private TableColumn<BookModel, Boolean> availabilityCol;
    
    ObservableList<BookModel> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeColums();
        try {
            DisplayingBookLogic.loadDataFromDataBase(list,tableView);
        } catch (Exception ex) {
            Logger.getLogger(AllBooksListController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void initializeColums() {
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        publisherCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        availabilityCol.setCellValueFactory(new PropertyValueFactory<>("availabilty"));
    }

    @FXML
    private void deleteBook(ActionEvent event) throws Exception {
        DisplayingBookLogic.deletingBookLogic(tableView,list);
    }
    
    

}
