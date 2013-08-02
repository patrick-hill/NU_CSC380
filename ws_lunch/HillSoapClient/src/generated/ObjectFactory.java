
package generated;

import generated.GetRestaurants.Body;
import generated.GetRestaurants.Envelope;
import generated.GetRestaurants.GetRestaurants;
import generated.MenuRequest.GetRestaurantMenu;
import generated.MenuResponse.GetMenuItems;
import generated.MenuResponse.MenuItems;
import generated.RestaurantsResponse.Restaurant;
import generated.RestaurantsResponse.RestaurantResponse;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the generated.GetRestaurants package.
 * <p>An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 */
@XmlRegistry
public class ObjectFactory {

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated.GetRestaurants
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link generated.GetRestaurants.GetRestaurants }
     */
    public GetRestaurants createGetRestaurants() {
        return new GetRestaurants();
    }

    /**
     * Create an instance of {@link generated.GetRestaurants.Body }
     */
    public Body createBody() {
        return new Body();
    }

    /**
     * Create an instance of {@link generated.GetRestaurants.Envelope }
     */
    public Envelope createEnvelope() {
        return new Envelope();
    }

    private final static QName _RestaurantName_QNAME = new QName("http://localhost:8080/restaurants", "restaurantName");

    /**
     * Create an instance of {@link generated.MenuRequest.GetRestaurantMenu }
     */
    public GetRestaurantMenu createGetRestaurantMenu() {
        return new GetRestaurantMenu();
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link String }{@code >}}
     */
    @XmlElementDecl(namespace = "http://localhost:8080/restaurants", name = "restaurantName")
    public JAXBElement<String> createRestaurantName(String value) {
        return new JAXBElement<String>(_RestaurantName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link generated.MenuResponse.GetMenuItems }
     */
    public GetMenuItems createGetMenuItems() {
        return new GetMenuItems();
    }

    /**
     * Create an instance of {@link generated.MenuResponse.MenuItems }
     */
    public MenuItems createMenuItems() {
        return new MenuItems();
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link String }{@code >}}
     */
    @XmlElementDecl(namespace = "http://localhost:8080/restaurants", name = "name")
    public JAXBElement<String> createName(String value) {
        return new JAXBElement<String>(_Name_QNAME, String.class, null, value);
    }

    private final static QName _Name_QNAME = new QName("http://localhost:8080/restaurants", "name");


    /**
     * Create an instance of {@link generated.RestaurantsResponse.RestaurantResponse }
     */
    public RestaurantResponse createRestaurantResponse() {
        return new RestaurantResponse();
    }

    /**
     * Create an instance of {@link generated.RestaurantsResponse.Restaurant }
     */
    public Restaurant createRestaurant() {
        return new Restaurant();
    }

}
