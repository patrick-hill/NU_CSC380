
package generatedRest;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the generated package. 
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

    private final static QName _Restaurants_QNAME = new QName("", "restaurants");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RestaurantsType }
     * 
     */
    public RestaurantsType createRestaurantsType() {
        return new RestaurantsType();
    }

    /**
     * Create an instance of {@link MenuItemsType }
     * 
     */
    public MenuItemsType createMenuItemsType() {
        return new MenuItemsType();
    }

    /**
     * Create an instance of {@link RestaurantType }
     * 
     */
    public RestaurantType createRestaurantType() {
        return new RestaurantType();
    }

    /**
     * Create an instance of {@link MenuItemType }
     * 
     */
    public MenuItemType createMenuItemType() {
        return new MenuItemType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RestaurantsType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "restaurants")
    public JAXBElement<RestaurantsType> createRestaurants(RestaurantsType value) {
        return new JAXBElement<RestaurantsType>(_Restaurants_QNAME, RestaurantsType.class, null, value);
    }

}
