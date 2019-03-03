package DatabaseModels;

import Messagess.ExceptionMessagess;
import Messagess.MessageMaker;
import library.database.DatabaseHandler;

public class Queries {
    public static void insertIntoBookTableQuery( String bookID, String bookAuthor, String bookName, String bookPublisher, DatabaseHandler handler) {
       String query = " INSERT INTO BOOK VALUES ("
                + "'" + bookID + "',"
                + "'" + bookName + "',"
                + "'" + bookAuthor + "', "
                + "'" + bookPublisher + "', "
                + "" + "true" + ""
                + ")";

        System.out.println(query);
        if (handler.execAction(query)) {
            MessageMaker.showSimpleMessage(ExceptionMessagess.SUCCESS_MESSAGE, ExceptionMessagess.SUCCESS_CONTENT_MESSAGE);

        } else {
            MessageMaker.showErrorMessage(ExceptionMessagess.ERROR_MESSAGE, ExceptionMessagess.ERROR_CONTENT);
        }
    }

    public static void insertIntoUserTable(String userId, String userName, String mobile, String userEmail, DatabaseHandler handler) {
        String statement = " INSERT INTO MEMBER VALUES ("
                + "'" + userId + "',"
                + "'" + userName + "',"
                + "'" + mobile + "',"
                + "'" + userEmail + "'"
                + ")";

        System.out.println(statement);

        if (handler.execAction(statement)) {
            MessageMaker.showSimpleMessage(ExceptionMessagess.SUCCESS_MESSAGE, ExceptionMessagess.SUCCESS_USER_CONTENT_MESSAGE);
        } else {
            MessageMaker.showErrorMessage(ExceptionMessagess.ERROR_MESSAGE,ExceptionMessagess.FAILED_USER_MESSAGE );
        }
    }
}
