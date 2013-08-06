/**
 * Created with IntelliJ IDEA.
 * User: Patrick
 * Date: 7/22/13
 * Time: 5:46 PM
 * To change this template use File | Settings | File Templates.
 */

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "title", "publisher" })
public class Book {
    private Author author;
    private String title;
    private String publisher;

    public Book() {

    }

    public Book(Author author, String title, String publisher) {
        this.author = author;
        this.title = title;
        this.publisher = publisher;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @XmlElement(name = "author")
    public String getTitle() {
        return title;
    }
    @XmlElement(name = "publisher")
    public String getPublisher() {
        return publisher;
    }


}
