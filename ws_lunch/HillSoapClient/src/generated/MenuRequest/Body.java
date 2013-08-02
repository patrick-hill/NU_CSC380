
package generated.MenuRequest;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://localhost:8080/restaurants}getRestaurantMenu"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "getRestaurantMenu"
})
@XmlRootElement(name = "Body", namespace = "http://www.w3.org/2001/12/soap-envelope")
public class Body {

    @XmlElement(namespace = "http://localhost:8080/restaurants", required = true)
    protected GetRestaurantMenu getRestaurantMenu;

    /**
     * Gets the value of the getRestaurantMenu property.
     * 
     * @return
     *     possible object is
     *     {@link generated.MenuRequest.GetRestaurantMenu }
     *
     */
    public GetRestaurantMenu getGetRestaurantMenu() {
        return getRestaurantMenu;
    }

    /**
     * Sets the value of the getRestaurantMenu property.
     *
     * @param value
     *     allowed object is
     *     {@link generated.MenuRequest.GetRestaurantMenu }
     *     
     */
    public void setGetRestaurantMenu(GetRestaurantMenu value) {
        this.getRestaurantMenu = value;
    }

}
