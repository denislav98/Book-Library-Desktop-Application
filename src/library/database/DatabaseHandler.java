
package library.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
//import jdk.nashorn.internal.ir.Statement;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import library.displayAllBooks.BookModel;

public class DatabaseHandler {
    
    private static DatabaseHandler handler = null;
    
    private static final  String DB_URL = "jdbc:derby:database;create=true";
    public static  Connection conn = null;
    private static Statement stmt = null;  // statements like insert , update , select .. 
    
     private DatabaseHandler() throws Exception {  // za da moje da ogranichim dostupa .Nikoi class nqma da moje da dostupi direktno bazata
        createConnection();
        setupUserTable();
        setupBookTable(); 
        setupIssueTable();
        
    }
     
     public static DatabaseHandler getInstance() throws Exception{      // shte mojem da izpolzvame obekta handler samo kogato izvikame purvo metoda getInstance()
         
         if(handler == null){
             handler = new DatabaseHandler();
         }
         
         return handler;
     }
     
    private static void createConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            conn = DriverManager.getConnection(DB_URL);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Cant load database", "Database Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }
    
  private  void setupBookTable() throws SQLException, Exception{
        
        String TABLE_NAME = "BOOK";
        
        try{
            
            stmt =  conn.createStatement();         // create statement object from the db
            
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables =dbm.getTables(null,null,TABLE_NAME.toUpperCase(),null);
            
            if(tables.next()){
                System.out.println("Table " + TABLE_NAME + "already exist.");
            }
            else{
               stmt.execute("CREATE TABLE "+ TABLE_NAME + "(" 
               + " id varchar(200) primary key,\n"
               + " title varchar(200),\n"
               + " author varchar(200),\n" 
               + " publisher varchar(200),\n" 
               + " isAvail boolean default true" 
               + " )");
            }
        }catch(SQLException e){
            
       System.out.println(e.getMessage() + "please setup the database");
       
    }finally{
            
        }
    }
    public ResultSet execQuery(String query) {
        ResultSet result;
        try {
            stmt = conn.createStatement();  // createStatement e global object
            result = stmt.executeQuery(query);
        }
        catch (SQLException ex) {
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return null;
        }
        finally {
        }
        return result;
    }
        
     public boolean execAction(String query) {  // for making some kind of action like inserting or creating

         try {
            stmt = conn.createStatement();
            stmt.execute(query);
            
            return true;
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error: " + ex.getMessage() , "Error occured " ,JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return false;
        }
        finally {
        }
    }

    private void setupUserTable() {
        
        String TABLE_NAME = "MEMBER";
        
        try{
            stmt =  conn.createStatement();
            
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables =dbm.getTables(null,null,TABLE_NAME.toUpperCase(),null);
            
            if(tables.next()){                                                // proverka za sushtestvuvashta veche tablica
                System.out.println("Table " + TABLE_NAME + " already exist.");   
            }
            else{
               stmt.execute("CREATE TABLE "+ TABLE_NAME + "(" 
               + " id varchar(200) primary key,\n"
               + " name varchar(200),\n"
               + " mobile varchar(20),\n" 
               + " email varchar(100)\n" 
               + " )");
            }
        }catch(SQLException e){
            
       System.out.println(e.getMessage() + "please setup the database");
       
    }finally{
            
        }
    }
    
     private void setupIssueTable() {
        
        String TABLE_NAME = "ISSUE";
        
        try{
            stmt =  conn.createStatement();
            
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables =dbm.getTables(null,null,TABLE_NAME.toUpperCase(),null);
            
            if(tables.next()){
                System.out.println("Table " + TABLE_NAME + " already exist.");
            }
            else{
               stmt.execute("CREATE TABLE "+ TABLE_NAME + "(" 
               + " bookID varchar(200) primary key,\n"
               + " memberID varchar(200),\n"
               + " issueTime timestamp default CURRENT_TIMESTAMP,\n" //for both time and date
               + " renew_count integer default 0,\n"                 // the number of renewing the book by the user ,after every operation is incremented
               + " FOREIGN KEY (bookID) REFERENCES BOOK(id),\n"      // proverka dali tova id e tochno ot tazi tablica
               + " FOREIGN KEY (memberID) REFERENCES MEMBER(id)"         
               + " )");
            }
        }catch(SQLException e){
            
       System.out.println(e.getMessage() + "please setup the database");
       
    }finally{
            
        }
    }
    
    
    public boolean deleteBook(BookModel book){
        
        try {
            String deleteStatement = "DELETE FROM BOOK WHERE ID = ?";
            PreparedStatement ps = conn.prepareStatement(deleteStatement);
            ps.setString(1, book.getId());
            int res = ps.executeUpdate();
            if(res == 1){            
            return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public boolean isBookIsAlreadyIssued(BookModel book){
        try {
            String checkIfIssueTableContainsBookId = "SELECT COUNT(*) FROM ISSUE WHERE bookID = ?";
            PreparedStatement ps = conn.prepareStatement(checkIfIssueTableContainsBookId);
            ps.setString(1, book.getId());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                
                int count = rs.getInt(1);
                System.out.println(count);
                return(count > 1);
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
}
//ctr + shit + i