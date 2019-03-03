package bussinessLogic;

import Messagess.ExceptionMessagess;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import library.database.DatabaseHandler;
import library.displayIssueList.IssueInfo;
import library.settings.SetingsInformation;

public class IssueListLogic {

    private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    public static void displayingIssueListLogic(ObservableList<IssueInfo> list, TableView<IssueInfo> tableView) throws Exception {
        list.clear();
        DatabaseHandler handler = DatabaseHandler.getInstance();
        String qu = ExceptionMessagess.ISSUE_JOIN_QUERY;
        ResultSet rs = handler.execQuery(qu);
        SetingsInformation pref = SetingsInformation.loadJsonObjectFromFile();
        try {
            int columsCounter = 0;
            while (rs.next()) {
                columsCounter += 1;
                String memberName = rs.getString("name");
                String bookID = rs.getString("bookID");
                String bookTitle = rs.getString("title");
                Timestamp issueTime = rs.getTimestamp("issueTime");
                System.out.println("Issued on " + issueTime);
                Integer days = Math.toIntExact(TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - issueTime.getTime())) + 1;
                Double fine = MainLogic.getFineAmount(days);
                IssueInfo issueInfo = new IssueInfo(columsCounter, bookID, bookTitle, memberName, formatDateTimeString(new Date(issueTime.getTime())), days, fine);
                list.add(issueInfo);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        tableView.setItems(list);
    }

    public static String formatDateTimeString(Date date) {
        return DATE_TIME_FORMAT.format(date);
    }

    public static String formatDateTimeString(Long time) {
        return DATE_TIME_FORMAT.format(new Date(time));
    }

    public static String getDateString(Date date) {
        return DATE_FORMAT.format(date);
    }

}
