
package library.usersList;

import javafx.beans.property.SimpleStringProperty;

public class User {
    private final SimpleStringProperty name;
    private final SimpleStringProperty id;
    private final SimpleStringProperty mobile;
    private final SimpleStringProperty email;

    public User(String name, String id, String mobile, String email) {
        this.name = new SimpleStringProperty(name);
        this.id = new SimpleStringProperty(id);
        this.mobile = new SimpleStringProperty(mobile);;
        this.email = new SimpleStringProperty(email);
    }
    
/*
    public String getName() {
        return name.get();
    }

    public String getBookId() {
        return id.get();
    }

    public String getAuthor() {
        return mobile.get();
    }

    public String getPublisher() {
        return email.get();
    }*/

    public String getName() {
        return name.get();
    }

    public String getId() {
        return id.get();
    }

    public String getMobile() {
        return mobile.get();
    }

    public String getEmail() {
        return email.get();
    }
  
}
