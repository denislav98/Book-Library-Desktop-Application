package bussinessLogic;

import Messagess.ExceptionMessagess;
import Messagess.MessageMaker;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import library.addNewBook.AddNewBookController;
import library.database.DatabaseHandler;

public class AddBookLogic {    

    public static void addingBook(String bookId, String author, String title, String pub) {
        if (bookId.isEmpty() || author.isEmpty() || title.isEmpty() || pub.isEmpty()) {
            MessageMaker.showErrorMessage(ExceptionMessagess.ERROR_MESSAGE, ExceptionMessagess.ERROR_CONTENT);
            return;
        }

    }

    public static void checkData(DatabaseHandler databaseHandler) {
        String query = ExceptionMessagess.BOOK_ID_QUERY;
        ResultSet resultSet = databaseHandler.execQuery(query);

        try {
            while (resultSet.next()) {
                String bookTitle = resultSet.getString("id");
                System.out.println(bookTitle);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddNewBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
