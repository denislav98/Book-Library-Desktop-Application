package bussinessLogic;

import Messagess.ExceptionMessagess;
import Messagess.MessageMaker;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import library.database.DatabaseHandler;
import library.displayAllBooks.AllBooksListController;
import library.displayAllBooks.BookModel;

public class DisplayingBookLogic {

    public static void loadDataFromDataBase(ObservableList<BookModel> list, TableView<BookModel> tableView) throws Exception {
        DatabaseHandler handler = DatabaseHandler.getInstance();
        String query = ExceptionMessagess.BOOK_QUERY;
        ResultSet resultSet = handler.execQuery(query);

        try {
            while (resultSet.next()) {
                String bookTitle = resultSet.getString("title");
                String id = resultSet.getString("id");
                String author = resultSet.getString("author");
                String publisher = resultSet.getString("publisher");
                Boolean available = resultSet.getBoolean("isAvail");

                list.add(new BookModel(bookTitle, id, author, publisher, available));

            }
        } catch (SQLException ex) {
            Logger.getLogger(AllBooksListController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tableView.setItems(list);
    }

    public static void deletingBookLogic(TableView<BookModel> tableView, ObservableList<BookModel> list) throws Exception {
        BookModel selectedForDeleting = tableView.getSelectionModel().getSelectedItem();
        if (selectedForDeleting == null) {
            MessageMaker.showErrorMessage( ExceptionMessagess.NO_BOOK_SELECTED_MESSAGE, ExceptionMessagess.SELECT_BOOK_MESSAGE);
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(ExceptionMessagess.DELETING_BOOK);
        alert.setContentText(ExceptionMessagess.USER_MESSAGE + selectedForDeleting + " ?");

        Optional<ButtonType> answer = alert.showAndWait();

        if (answer.get() == (ButtonType.OK)) {

            Boolean resultOfDeleting = DatabaseHandler.getInstance().deleteBook(selectedForDeleting);
            if (resultOfDeleting) {
                MessageMaker.showSimpleMessage(ExceptionMessagess.DELETING_BOOK, selectedForDeleting.getTitle() + " was successfully deleted !");
                list.remove(selectedForDeleting);
            } else {
                MessageMaker.showSimpleMessage(ExceptionMessagess.FAILED_MESSAGE, selectedForDeleting.getTitle() + " could not be deleted !");
            }

        } else {
            MessageMaker.showSimpleMessage(ExceptionMessagess.CANCEL_MESSAGE, ExceptionMessagess.CANCEL_DELETING_MESSAGE);
        }
    }

}
