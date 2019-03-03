
package library.displayAllBooks;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class BookModel {
    private final SimpleStringProperty title;
    private final SimpleStringProperty id;
    private final SimpleStringProperty author;
    private final SimpleStringProperty publisher;
    private final SimpleBooleanProperty availabilty;

    public BookModel(String title, String id, String author, String publisher, Boolean availabilty) {
        this.title = new SimpleStringProperty(title);
        this.id = new SimpleStringProperty(id);
        this.author = new SimpleStringProperty(author);;
        this.publisher = new SimpleStringProperty(publisher);
        this.availabilty = new SimpleBooleanProperty(availabilty);
    }
    
    public String getTitle() {
        return title.get();
    }

    public String getId() {
        return id.get();
    }

    public String getAuthor() {
        return author.get();
    }

    public String getPublisher() {
        return publisher.get();
    }

    public Boolean getAvailabilty() {
        return availabilty.get();
    }
}
