package ordering;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * User: Patrick
 * Date: 7/30/13
 */
@WebServlet(name = "OrderingServlet", urlPatterns = "/ordering")
public class OrderingServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Hit the POST");

        BufferedReader br = request.getReader();
        File f = File.createTempFile("xml", ".xml");
        FileWriter fw = new FileWriter(f);

        // Temp
        String tmp = br.readLine();
        System.out.println("Read from request:");
        System.out.println(tmp);
        fw.write(tmp);
//            fw.write(br.readLine());
        fw.flush();


//            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
//            JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
//            Unmarshaller unmarshaller = context.createUnmarshaller();
//            RestaurantType r  = (RestaurantType) ((JAXBElement) unmarshaller.unmarshal(br)).getValue();


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Hit the GET");
    }
}
