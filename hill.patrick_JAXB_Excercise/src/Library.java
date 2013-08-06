import javax.xml.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: Patrick
 * Date: 7/22/13
 * Time: 5:46 PM
 * To change this template use File | Settings | File Templates.
 */

//@XmlAccessorType
//@XmlAttribute
//@XmlElement(name = "Library")
@XmlRootElement(name = "library")
@XmlType(propOrder = {"book"})
public class Library {
    private Book book;

    public Library() {
    }

    public Library(Book book) {
        this.book = book;
    }
    @XmlElement(name = "book")
    public String getBook() {
        return null;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
