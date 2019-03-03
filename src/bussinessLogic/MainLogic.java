
package bussinessLogic;


import Messagess.ExceptionMessagess;
import Messagess.MessageMaker;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import library.database.DatabaseHandler;
import library.main.MainController;
import library.settings.SetingsInformation;

public class MainLogic {

    public static void loadBookBussinessLogic(TextField bookIDInput, Text bookName, Text bookAuthor, Text bookStatus, DatabaseHandler databaseHandler) {
        String id = bookIDInput.getText();
        String query = "SELECT * FROM BOOK WHERE id = '" + id + "'";

        ResultSet rs = databaseHandler.execQuery(query);
        Boolean bookIsAvailable = false;
        try {
            while (rs.next()) {

                String bName = rs.getString("title");
                String bAuthor = rs.getString("author");
                Boolean bStatus = rs.getBoolean("isAvail");

                bookName.setText(bName);
                bookAuthor.setText(bAuthor);
                String status = (bStatus) ? "Available" : "Not Available";
                bookStatus.setText(status);

                bookIsAvailable = true;
            }

            if (!bookIsAvailable) {
                bookName.setText(ExceptionMessagess.NO_SUCH_BOOK_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void loadUserBussinessLogic(TextField userIDInput, Text userName, Text userMobile, DatabaseHandler databaseHandler) {
        String id = userIDInput.getText();
        String query = "SELECT * FROM MEMBER WHERE id = '" + id + "'";
        ResultSet rs = databaseHandler.execQuery(query);
        Boolean userIsAvailable = false;
        try {
            while (rs.next()) {

                String uName = rs.getString("name");
                String uPhone = rs.getString("mobile");

                userName.setText(uName);
                userMobile.setText(uPhone);

                userIsAvailable = true;
            }

            if (!userIsAvailable) {
                userName.setText(ExceptionMessagess.NO_SUCH_USER_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void loadBorrowingOperationLogic(TextField userIDInput, TextField bookIDInput, DatabaseHandler databaseHandler, Text bookName, Text userName) {
        String userID = userIDInput.getText();
        String bookID = bookIDInput.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm borrow book operation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to borow that book " + bookName.getText() + "\n to " + userName.getText() + " ?");

        Optional<ButtonType> response = alert.showAndWait();

        if (response.get() == ButtonType.OK) {

            String insertQuery = "INSERT INTO ISSUE(memberID,bookID) VALUES ( "
                    + "'" + userID + "',"
                    + "'" + bookID + "')";
            String updateBookQuery = "UPDATE BOOK SET isAvail = false WHERE id = '" + bookID + "'";

            System.out.println(insertQuery + " " + updateBookQuery);

            if (databaseHandler.execAction(insertQuery) && databaseHandler.execAction(updateBookQuery)) {
                MessageMaker.showSimpleMessage("Successfully borowed book", "Book Borow Redy!");
            } else {
                MessageMaker.showErrorMessage("ERROR", "Can't proceed borow operation!");
            }
        } else {
            MessageMaker.showSimpleMessage("Cancelled", "Borowing book canceled!");
        }
    }

    public static void loadRenwingOperationLogic(ObservableList<String> issueData, JFXTextField bookIdWhenRenew, Boolean bookReadyForSubmission, ListView<String> issueDataList, DatabaseHandler databaseHandler) {
        bookReadyForSubmission = false;

        String id = bookIdWhenRenew.getText();
        String query = "SELECT * FROM ISSUE WHERE bookID = '" + id + "'";
        ResultSet rs = databaseHandler.execQuery(query);

        try {
            while (rs.next()) {
                String userBookId = id;
                String userId = rs.getString("memberID");
                Timestamp userIssueTime = rs.getTimestamp("issueTime");
                int countOfRenew = rs.getInt("renew_count");

                issueData.add("Borowing Date and Time : " + userIssueTime.toGMTString());
                issueData.add("Number of Renewing : " + countOfRenew);

                issueData.add("Book information : ");
                query = "SELECT * FROM BOOK WHERE ID = '" + userBookId + "'";
                ResultSet r1 = databaseHandler.execQuery(query);

                while (r1.next()) {
                    issueData.add("Book Name : " + r1.getString("title"));
                    issueData.add("Book ID : " + r1.getString("id"));
                    issueData.add("Book Author : " + r1.getString("author"));
                    issueData.add("Book Publisher : " + r1.getString("publisher"));
                }

                query = "SELECT * FROM MEMBER WHERE ID = '" + userId + "'";
                r1 = databaseHandler.execQuery(query);
                issueData.add("User information :");
                while (r1.next()) {
                    issueData.add("User Name : " + r1.getString("name"));
                    issueData.add("User Mobile : " + r1.getString("mobile"));
                    issueData.add("User Email : " + r1.getString("email"));
                }

                bookReadyForSubmission = true;   // ako sa izpulneni zaqvkite preminava kum submit
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public static void submissionOperationLogic(Boolean isBookReadyForSubmission, JFXTextField bookIdWhenRenew, DatabaseHandler databaseHandler) {
        if (!isBookReadyForSubmission) {
            MessageMaker.showErrorMessage("FAILED", "You have to select book for submit !");
            return; // nothing to happen
        }
        MessageMaker.showConfirmationMessage("Confirm returning book operation", "Are you sure you want to return that book ");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        Optional<ButtonType> response = alert.showAndWait();

        if (response.get() == ButtonType.OK) {
            String id = bookIdWhenRenew.getText();
            String deleteBookQuery = "DELETE FROM ISSUE WHERE BOOKID = '" + id + "'";
            String updateBookQuery = "UPDATE BOOK SET ISAVAIL = TRUE WHERE ID = '" + id + "'";

            if (databaseHandler.execAction(deleteBookQuery) && databaseHandler.execAction(updateBookQuery)) {
                MessageMaker.showSimpleMessage("SUCCESS", "Book has been submitted!");
            } else {
                MessageMaker.showErrorMessage("Failed to submit book!", "Error in submiting!");
            }
        } else {
            MessageMaker.showSimpleMessage("Cancelled", "Renewing book canceled!");

        }
    }

    public static void loadRenewOperationLogic(Boolean bookReadyForSubmission, JFXTextField bookIdWhenRenew, DatabaseHandler databaseHandler) {
        if (!bookReadyForSubmission) {
            MessageMaker.showErrorMessage("FAILED", "You have to select book to renew !");
            return; // nothing to happen
        }
        MessageMaker.showConfirmationMessage("Confirm renewing book operation", "Are you sure you want to RENEW that book ");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> response = alert.showAndWait();

        if (response.get() == ButtonType.OK) {

            String action = "UPDATE ISSUE SET issueTime = CURRENT_TIMESTAMP,renew_count = renew_count + 1 WHERE BOOKID = '" + bookIdWhenRenew.getText() + "'";
            System.out.println(action);

            if (databaseHandler.execAction(action)) {
                MessageMaker.showSimpleMessage("SUCCESS", "Book has been renewed !");

            } else {
                MessageMaker.showErrorMessage("FAILED", "Error in renewing!");
                Alert alert1 = new Alert(Alert.AlertType.ERROR);

            }
        } else {  // if cancel button is pressed
            MessageMaker.showSimpleMessage("Cancelled", "Renewing book canceled!");
        }
    }

    public static void bookForSearchLogic(ObservableList<String> bookInformation, JFXTextField bookForSearch, DatabaseHandler databaseHandler, ListView<String> displayBooksList) {
        String bookTitle = bookForSearch.getText();
        String query = "SELECT * FROM BOOK WHERE title = '" + bookTitle + "'";

        ResultSet rs = databaseHandler.execQuery(query);
        try {
            while (rs.next()) {
                if (rs.getString("title").equals(bookTitle)) {
                    bookInformation.add("Book Name : " + rs.getString("title"));
                    bookInformation.add("Book ID : " + rs.getString("id"));
                    bookInformation.add("Book Author : " + rs.getString("author"));
                    bookInformation.add("Book Publisher : " + rs.getString("publisher"));
                    bookInformation.add("Is it available : " + rs.getBoolean("isAvail"));
                } else {
                    MessageMaker.showSimpleMessage("No Available", "That book is not available in library ");
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        displayBooksList.getItems().setAll(bookInformation);
    }

    public static void bookForRenewOrSubmission(Boolean bookReadyForSubmission, JFXTextField bookIdWhenRenew, DatabaseHandler databaseHandler, Text memberNameHolder, Text memeberMobileHolder, Text memberEmailHolder, Text bookNameHolder, Text bookAuthorHolder, Text bookPublisherHolder, Text issueDateHolder, Text numberOfDaysHolded, Text fineHolder,StackPane rootPane) {
        bookReadyForSubmission = false;
          try{
            String id = bookIdWhenRenew.getText();
            String myQuery = "SELECT ISSUE.bookID, ISSUE.memberID, ISSUE.issueTime, ISSUE.renew_count,\n"
                    + "MEMBER.name, MEMBER.mobile, MEMBER.email,\n"
                    + "BOOK.title, BOOK.author, BOOK.publisher\n"
                    + "FROM ISSUE\n"
                    + "LEFT JOIN MEMBER\n"
                    + "ON ISSUE.memberID=MEMBER.ID\n"
                    + "LEFT JOIN BOOK\n"
                    + "ON ISSUE.bookID=BOOK.ID\n"
                    + "WHERE ISSUE.bookID='" + id + "'";
            ResultSet rs = databaseHandler.execQuery(myQuery);
            if (rs.next()) {
                memberNameHolder.setText(rs.getString("name"));
                memeberMobileHolder.setText(rs.getString("mobile"));
                memberEmailHolder.setText(rs.getString("email"));

                bookNameHolder.setText(rs.getString("title"));
                bookAuthorHolder.setText(rs.getString("author"));
                bookPublisherHolder.setText(rs.getString("publisher"));

                Timestamp mIssueTime = rs.getTimestamp("issueTime");
                Date dateOfIssue = new Date(mIssueTime.getTime());
                issueDateHolder.setText(dateOfIssue.toString());
             //   Long timeElapsed = System.currentTimeMillis() - mIssueTime.getTime(); // calculating how long book has been issued
                Integer daysElapsed = Math.toIntExact(TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - mIssueTime.getTime())) + 1;
                String days = String.format("Used %d days", daysElapsed);
                numberOfDaysHolded.setText(days);
                double fine = getFineAmount(daysElapsed);
                if (fine > 0) {
                    fineHolder.setText(String.format("Fine : %.2f", getFineAmount(daysElapsed)));
                } else {
                    fineHolder.setText("No fine");
                }
    
                bookReadyForSubmission = true; 
        
            }else{
                JFXDialogLayout dialogLayOut = new JFXDialogLayout();
                JFXButton button = new JFXButton("I'll check");
                JFXDialog dialog = new JFXDialog(rootPane,dialogLayOut,JFXDialog.DialogTransition.TOP);
                button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> dialog.close());
                dialogLayOut.setHeading(new Label("No such book is borrowed ! "));
                dialogLayOut.setActions(button);
                dialog.show();
            }
          }catch(SQLException e){
                 e.printStackTrace();
          }
    }
    
     public static double getFineAmount(int totalDays) {
        SetingsInformation preferences = SetingsInformation.loadJsonObjectFromFile();
        Integer fineDays = totalDays - preferences.getNumberOfDaysWithouthFine();
        Double fine = 0.0;
        if (fineDays > 0) {
            fine = fineDays * preferences.getFinePerDay();
        }
        return fine;
    }
    
}
