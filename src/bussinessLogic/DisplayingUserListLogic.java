
package bussinessLogic;

import Messagess.ExceptionMessagess;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import library.database.DatabaseHandler;
import library.usersList.User;
import library.usersList.UserListController;

public class DisplayingUserListLogic {
    public static void loadDataFromDataBase(ObservableList<User> list, TableView<User> tableView) throws Exception {
     DatabaseHandler handler = DatabaseHandler.getInstance();
        String query = ExceptionMessagess.MEMBER_QUERY;
        ResultSet resultSet = handler.execQuery(query);

        try {
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String mobile = resultSet.getString("mobile");
                String email = resultSet.getString("email");
                
                list.add(new User(name , id , mobile , email ));
               
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tableView.getItems().setAll(list);
    }
}

