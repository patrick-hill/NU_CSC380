
package generated.SendMenu;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the generated.SendMenu package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Name_QNAME = new QName("http://localhost:8080/restaurants", "name");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated.SendMenu
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link generated.SendMenu.GetMenuItems }
     *
     */
    public GetMenuItems createGetMenuItems() {
        return new GetMenuItems();
    }

    /**
     * Create an instance of {@link MenuItems }
     *
     */
    public MenuItems createMenuItems() {
        return new MenuItems();
    }

    /**
     * Create an instance of {@link Body }
     *
     */
    public Body createBody() {
        return new Body();
    }

    /**
     * Create an instance of {@link Envelope }
     *
     */
    public Envelope createEnvelope() {
        return new Envelope();
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://localhost:8080/restaurants", name = "name")
    public JAXBElement<String> createName(String value) {
        return new JAXBElement<String>(_Name_QNAME, String.class, null, value);
    }

}
