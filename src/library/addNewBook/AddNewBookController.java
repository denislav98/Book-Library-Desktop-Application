package library.addNewBook;

import DatabaseModels.Queries;
import bussinessLogic.AddBookLogic;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import library.database.DatabaseHandler;

public class AddNewBookController implements Initializable {

    @FXML
    private JFXTextField title;
    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField author;
    @FXML
    private JFXTextField publisher;
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton cancelButton;

    @FXML
    private AnchorPane rootPan;

    DatabaseHandler databaseHandler;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            databaseHandler = DatabaseHandler.getInstance();
            AddBookLogic.checkData(databaseHandler);
        } catch (Exception ex) {
            Logger.getLogger(AddNewBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void addBook(ActionEvent event) {
        String bookID = id.getText();
        String bookAuthor = author.getText();
        String bookName = title.getText();
        String bookPublisher = publisher.getText();

        AddBookLogic.addingBook(bookID, bookAuthor, bookName, bookPublisher);
        Queries.insertIntoBookTableQuery(bookID, bookAuthor, bookName, bookPublisher, databaseHandler);

    }

    @FXML
    private void cancelBook(ActionEvent event) {
        ((Stage) id.getScene().getWindow()).close();
    }

}
