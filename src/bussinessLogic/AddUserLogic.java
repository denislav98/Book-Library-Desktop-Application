package bussinessLogic;

import Messagess.ExceptionMessagess;
import Messagess.MessageMaker;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import library.addUser.AddUserController;
import library.database.DatabaseHandler;

public class AddUserLogic {

    public static void addingUserLogic(String username, String id, String mobile, String mail) {
        Boolean IsEmpty = username.isEmpty() || id.isEmpty() || mobile.isEmpty() || mail.isEmpty();
        if (IsEmpty) {
            MessageMaker.showErrorMessage(ExceptionMessagess.ERROR_MESSAGE, ExceptionMessagess.ERROR_CONTENT);
            return;
        }

    }

    public static void checkData(DatabaseHandler databaseHandler) {
        String query = ExceptionMessagess.MEMBER_NAME_QUERY;
        ResultSet resultSet = databaseHandler.execQuery(query);

        try {
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                System.out.println(name);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddUserController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
