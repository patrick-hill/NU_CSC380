import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;

/**
 * User: Patrick
 * Date: 7/30/13
 */
@WebServlet(name = "OrderingServlet", urlPatterns = "/Restaurants")
public class OrderingServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            BufferedReader br = request.getReader();
//            while(!br.ready()){
//                System.out.println("waiting");}
            JAXBContext context = JAXBContext.newInstance(generated.ObjectFactory.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Object cRequest = unmarshaller.unmarshal(br);
            String[] toSplit = cRequest.getClass().toString().split("\\.");
            String className = toSplit[1];

            // Debug Info
            System.out.println("cRequest: " + cRequest);
            System.out.println("ClassName: " + className);

            sendToClient(className, response);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private void sendToClient(String cmd, javax.servlet.http.HttpServletResponse response) {
        BufferedReader brm = null;
        if(cmd.equals("GetRestaurants") || cmd.equals("RestaurantsRequest"))
            brm = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("restaurant-restaurants-soap-response.xml")));
        else if(cmd.equals("GetRestaurantsMenu") || cmd.equals("MenuRequest"))
            brm = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("restaurant-menu-soapp-response.xml")));
        try {
            response.getWriter().write(makeString(brm));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String makeString(BufferedReader br) {
        String toSend = "";
        try {
            String file;
            while ((file = br.readLine()) != null)
//                toSend += file;
                toSend += file + "\n";
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Sending to client: \n" + toSend);
        return toSend;
    }
}
