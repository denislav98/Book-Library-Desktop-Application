
package Messagess;


public class ExceptionMessagess {
    public static final String SUCCESS_MESSAGE  = "Success";
    public static final String SUCCESS_CONTENT_MESSAGE = "Successfuly added book!";
    public static final String SUCCESS_USER_CONTENT_MESSAGE = "Successfuly added book!";
    public static final String ERROR_MESSAGE  = "Error";
    public static final String ERROR_CONTENT  = "Please fill all of the fields";
    public static final String DELETING_BOOK = "Deleting book";
    public static final String NO_BOOK_SELECTED_MESSAGE = "No book selected";
    public static final String SELECT_BOOK_MESSAGE = "Please select book for delete";
    public static final String CANCEL_MESSAGE ="Cancelled";
    public static final String CANCEL_DELETING_MESSAGE ="Deleting book cancelled ! ";
    public static final String FAILED_MESSAGE  ="Failed";
    public static final String FAILED_USER_MESSAGE  ="Failed";
    public static final String USER_MESSAGE  = "Are you sure you want to delete ";
    public static final String NO_SUCH_BOOK_MESSAGE  = "No such book is AVAILABLE in the library";
    public static final String NO_SUCH_USER_MESSAGE  = "No such USER in database!";
    //  QUERIES:
    public static final String BOOK_QUERY = "SELECT * FROM BOOK";
    public static final String BOOK_ID_QUERY = "SELECT id FROM BOOK";
    public static final String MEMBER_NAME_QUERY = "SELECT name FROM MEMBER";
    public static final String MEMBER_QUERY = "SELECT * FROM MEMBER";
    
    //JOIN QUERIES:
    public static final String ISSUE_JOIN_QUERY = "SELECT ISSUE.bookID, ISSUE.memberID, ISSUE.issueTime, MEMBER.name, BOOK.title FROM ISSUE\n"
                + "LEFT OUTER JOIN MEMBER\n"
                + "ON MEMBER.id = ISSUE.memberID\n"
                + "LEFT OUTER JOIN BOOK\n"
                + "ON BOOK.id = ISSUE.bookID";

}
