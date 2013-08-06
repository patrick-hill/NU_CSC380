import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created with IntelliJ IDEA.
 * User: Patrick
 * Date: 7/22/13
 * Time: 5:46 PM
 * To change this template use File | Settings | File Templates.
 */
@XmlType(propOrder = { "name", "phone" })
public class Author {
    private String name;
    private int phone;

    public Author() {

    }

    public Author(String name, int phone) {
        this.name = name;
        this.phone = phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    @XmlElement(name = "name")
    public String getName(){
        return name;
    }

    @XmlElement(name = "phone")
    public int getPhone() {
        return phone;
    }
}
