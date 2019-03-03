package library.main;

import bussinessLogic.MainLogic;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.effects.JFXDepthManager;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import library.database.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;

public class MainController implements Initializable {

    @FXML
    private HBox bookInfo;
    @FXML
    private HBox userInfo;
    @FXML
    private TextField bookIDInput;
    @FXML
    private Text bookName;
    @FXML
    private Text bookAuthor;
    @FXML
    private Text bookStatus;
    @FXML
    private Text userName;
    @FXML
    private Text userMobile;
    @FXML
    private TextField userIDInput;
    @FXML
    private JFXTextField bookIdWhenRenew;

    private ListView<String> issueDataList;
    @FXML
    private JFXTextField bookForSearch;
    @FXML
    private ListView<String> displayBooksList;
    @FXML
    private StackPane rootPane;
    @FXML
    private Text memberNameHolder;
    @FXML
    private Text memberEmailHolder;
    @FXML
    private Text memeberMobileHolder;
    @FXML
    private Text bookNameHolder;
    @FXML
    private Text bookAuthorHolder;
    @FXML
    private Text bookPublisherHolder;
    @FXML
    private Text issueDateHolder;
    @FXML
    private Text numberOfDaysHolded;
    @FXML
    private Text fineHolder;

    Boolean isBookReadyForSubmission = true;
    DatabaseHandler databaseHandler;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        JFXDepthManager.setDepth(bookInfo, 1); // izostrqne na prozoreca
        JFXDepthManager.setDepth(userInfo, 1);

        try {
            databaseHandler = DatabaseHandler.getInstance();
        } catch (Exception ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void loadAddUser(ActionEvent event) {
        loadMainWindow("/library/addUser/addUser.fxml", "Add User");
    }

    @FXML
    private void loadAddBook(ActionEvent event) {
        loadMainWindow("/library/addNewBook/addNewBook.fxml", "Add Book");
    }

    @FXML
    private void looadUserTable(ActionEvent event) {
        loadMainWindow("/library/usersList/userList.fxml", "View Users");
    }

    @FXML
    private void loadBookTable(ActionEvent event) {
        loadMainWindow("/library/displayAllBooks/allBooksList.fxml", "View Books");
    }

    @FXML
    private void loadIssueBookTable(ActionEvent event) {
        loadMainWindow("/library/displayIssueList/issueList.fxml", "Issue List");
    }

    @FXML
    private void loadSettings(ActionEvent event) {
        loadMainWindow("/library/settings/settings.fxml", "Settings");
    }

    // creating scene and put it to the parent for displaying
    void loadMainWindow(String location, String title) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(location));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();

            windowIcon.setStageIcon(stage);

        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void loadBookInformation(ActionEvent event) {

        clearBookCache(); // purvo izchistvame informaciqta 
        MainLogic.loadBookBussinessLogic(bookIDInput, bookName, bookAuthor, bookStatus, databaseHandler);
    }

    @FXML
    private void loadUserInformation(ActionEvent event) {

        clearUserCache();
        MainLogic.loadUserBussinessLogic(userIDInput, userName, userMobile, databaseHandler);

    }

    @FXML
    private void loadIssueOperation(ActionEvent event) {
        MainLogic.loadBorrowingOperationLogic(userIDInput, bookIDInput, databaseHandler, bookName, userName);
    }

    @FXML
    private void loadSubmissionOperation(ActionEvent event) {
        MainLogic.submissionOperationLogic(isBookReadyForSubmission, bookIdWhenRenew, databaseHandler);
    }

    @FXML
    private void loadRenewOperation(ActionEvent event) {
        MainLogic.loadRenewOperationLogic(isBookReadyForSubmission, bookIdWhenRenew, databaseHandler);
    }

    @FXML
    private void loadBookForSearch(ActionEvent event) {
        ObservableList<String> bookInformation = FXCollections.observableArrayList();
        MainLogic.bookForSearchLogic(bookInformation, bookForSearch, databaseHandler, displayBooksList);
    }

    @FXML
    private void handleCloseMenu(ActionEvent event) {
        ((Stage) rootPane.getScene().getWindow()).close();
    }

    @FXML
    private void loadBookInformationForRenewing(ActionEvent event) {
        clearEntries();
        MainLogic.bookForRenewOrSubmission(isBookReadyForSubmission, bookIdWhenRenew, databaseHandler, memberNameHolder, memeberMobileHolder, memberEmailHolder, bookNameHolder, bookAuthorHolder, bookPublisherHolder, issueDateHolder, numberOfDaysHolded, fineHolder, rootPane);
    }

    void clearBookCache() {
        bookName.setText("");
        bookAuthor.setText("");
        bookStatus.setText("");

    }

    void clearUserCache() {
        userName.setText("");
        userMobile.setText("");

    }

    private void clearEntries() {
        memberNameHolder.setText("");
        memberEmailHolder.setText("");
        memeberMobileHolder.setText("");

        bookNameHolder.setText("");
        bookAuthorHolder.setText("");
        bookPublisherHolder.setText("");

        issueDateHolder.setText("");
        numberOfDaysHolded.setText("");
        fineHolder.setText("");

    }

}
