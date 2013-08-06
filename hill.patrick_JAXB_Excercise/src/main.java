import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: Patrick
 * Date: 7/22/13
 * Time: 6:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class main {
    public static void main(String[] args) {
        Library lib = new Library();
        Book b = new Book();
        Author a = new Author();
        a.setName("datName");
        a.setPhone(666);
        b.setAuthor(a);
        b.setPublisher("datPublisher");
        b.setTitle("datTitle");
        lib.setBook(b);

        try {
            File file = new File("C:\\temp\\file.xml");
            JAXBContext jc = JAXBContext.newInstance(Library.class);
            Marshaller jm = jc.createMarshaller();

            jm.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jm.marshal(lib, file);
            jm.marshal(lib, System.out);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
