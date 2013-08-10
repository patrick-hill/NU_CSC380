package service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.List;

/**
 * User: Patrick
 * Date: 8/9/13
 */

@WebService(name = "LunchService", targetNamespace = "http://localhost/LunchService")
public interface LunchService {

    @WebMethod(operationName = "getRestaurants")
    public
    @WebResult(name = "RestaurantList")
    List<Restaurant> getRestaurants();

    @WebMethod(operationName = "getMenu")
    public
    @WebResult(name = "MenuList")
    Menu getMenu(@WebParam(name = "restaurant") Restaurant restaurant);

    @WebMethod(operationName = "addOrder")
    public void addOrder(@WebParam(name = "restaurant") Restaurant restaurant, @WebParam(name = "menuItems") MenuItem... menuItems);
}
