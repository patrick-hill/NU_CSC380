package generated;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.6
 * 2013-09-08T21:52:33.785-06:00
 * Generated source version: 2.7.6
 * 
 */
@WebService(targetNamespace = "http://localhost/LunchService", name = "LunchService")
@XmlSeeAlso({ObjectFactory.class})
public interface LunchService {

    @RequestWrapper(localName = "addOrder", targetNamespace = "http://localhost/LunchService", className = "generated.AddOrder")
    @WebMethod
    @ResponseWrapper(localName = "addOrderResponse", targetNamespace = "http://localhost/LunchService", className = "generated.AddOrderResponse")
    public void addOrder(
        @WebParam(name = "restaurant", targetNamespace = "")
        generated.Restaurant restaurant,
        @WebParam(name = "menuItems", targetNamespace = "")
        java.util.List<generated.MenuItem> menuItems
    );

    @WebResult(name = "RestaurantList", targetNamespace = "")
    @RequestWrapper(localName = "getRestaurants", targetNamespace = "http://localhost/LunchService", className = "generated.GetRestaurants")
    @WebMethod
    @ResponseWrapper(localName = "getRestaurantsResponse", targetNamespace = "http://localhost/LunchService", className = "generated.GetRestaurantsResponse")
    public java.util.List<generated.Restaurant> getRestaurants();

    @WebResult(name = "MenuList", targetNamespace = "")
    @RequestWrapper(localName = "getMenu", targetNamespace = "http://localhost/LunchService", className = "generated.GetMenu")
    @WebMethod
    @ResponseWrapper(localName = "getMenuResponse", targetNamespace = "http://localhost/LunchService", className = "generated.GetMenuResponse")
    public generated.Menu getMenu(
        @WebParam(name = "restaurant", targetNamespace = "")
        generated.Restaurant restaurant
    );
}
