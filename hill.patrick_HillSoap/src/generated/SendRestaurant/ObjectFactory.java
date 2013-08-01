
package generated.SendRestaurant;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the generated.SendRestaurant package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated.SendRestaurant
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link generated.SendRestaurant.RestaurantResponse }
     *
     */
    public RestaurantResponse createRestaurantResponse() {
        return new RestaurantResponse();
    }

    /**
     * Create an instance of {@link Restaurant }
     *
     */
    public Restaurant createRestaurant() {
        return new Restaurant();
    }

    /**
     * Create an instance of {@link generated.SendRestaurant.Body }
     *
     */
    public Body createBody() {
        return new Body();
    }

    /**
     * Create an instance of {@link generated.SendRestaurant.Envelope }
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
