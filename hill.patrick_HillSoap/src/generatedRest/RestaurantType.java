
package generatedRest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for restaurantType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="restaurantType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="menuItems" type="{}menuItemsType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "restaurantType", propOrder = {
    "menuItems"
})
public class RestaurantType {

    @XmlElement(required = true)
    protected MenuItemsType menuItems;
    @XmlAttribute(name = "name")
    protected String name;

    /**
     * Gets the value of the menuItems property.
     * 
     * @return
     *     possible object is
     *     {@link MenuItemsType }
     *     
     */
    public MenuItemsType getMenuItems() {
        return menuItems;
    }

    /**
     * Sets the value of the menuItems property.
     * 
     * @param value
     *     allowed object is
     *     {@link MenuItemsType }
     *     
     */
    public void setMenuItems(MenuItemsType value) {
        this.menuItems = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

}
