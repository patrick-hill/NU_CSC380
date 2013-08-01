package ordering;

import generated.GetRestaurants.Envelope;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;

/**
 * User: Patrick
 * Date: 7/30/13
 */
@WebServlet(name = "OrderingServlet", urlPatterns = "/ordering")
public class OrderingServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            BufferedReader br = request.getReader();
            JAXBContext context = JAXBContext.newInstance(generated.ObjectFactory.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Object cRequest = unmarshaller.unmarshal(br);
            String[] toSplit = cRequest.getClass().toString().split("\\.");
            String className = toSplit[1];

            // Debug Info
//            System.out.println(cRequest);
//            System.out.println(className);

            if (className.equals("GetRestaurants") || className.equals("RestaurantsRequest"))
                sendRestaurants(response);
            else if (className.equals("GetRestaurantsMenu") || className.equals("MenuRequest"))
                sendRestaurantsMenu(response);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private void sendRestaurantsMenu(javax.servlet.http.HttpServletResponse response) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("restaurant-menu-soapp-response.xsd"));
        String toSend = "";
        String file;
        while ((file = br.readLine()) != null)
            toSend += file;
        response.getWriter().write(toSend);
    }

    private void sendRestaurants(javax.servlet.http.HttpServletResponse response) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("restaurant-restaurants-soap-response.xml"));
        String toSend = "";
        String file;
        while ((file = br.readLine()) != null)
            toSend += file;
        response.getWriter().write(toSend);
    }
}
