import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPMessage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * User: Patrick
 * Date: 8/1/13
 */
public class Client {
    public static void main(String[] args) {
        new Client().start();
    }

    private Socket s;
    private BufferedWriter serverWrite;
    private BufferedReader serverRead;
    private JAXBContext context;

    private HttpURLConnection con;

    public void start() {
        init();
        callServer("restaurants");
        parseServer();
//        callServer("menu");
//        parseServer();
    }

    public void init() {
        try {
            URL serverURL = new URL("http://localhost:8080/Restaurants");
            con = (HttpURLConnection) serverURL.openConnection();

            con.setDoInput(true);
            con.setDoOutput(true);
            con.setRequestMethod("POST");

            serverWrite = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
            serverRead = new BufferedReader(new InputStreamReader(con.getInputStream()));

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void callServer(String cmd) {
        String received = "";
        BufferedReader br;
        System.out.println("CMD is: " + cmd);
        try {
            if(cmd.equals("restaurants"))
                br = new BufferedReader(new InputStreamReader(
                        this.getClass().getClassLoader().getResourceAsStream(
                                "restaurant-restaurants-soap-request.xml")));
            else
                br = new BufferedReader(new InputStreamReader(
                        this.getClass().getClassLoader().getResourceAsStream(
                                "restaurant-menu-soap-request.xml")));
            String toSend = "";
            String file;
            while ((file = br.readLine()) != null)
//                toSend += file;
                toSend += file + "\n";
            System.out.println("Sending to server: \n" + toSend);
            serverWrite.write(toSend);
            serverWrite.flush();

//            file = new String();
//            while ((file = br.readLine()) != null)
//                received += file;
        } catch (IOException e) {
            e.printStackTrace();
        }
//        return received;
    }

    public void parseServer() {
        try {
//            while(!serverRead.ready()){
//                System.out.println("waiting");}
            JAXBContext context = JAXBContext.newInstance(generated.ObjectFactory.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Object cRequest = unmarshaller.unmarshal(serverRead);

            String[] toSplit = cRequest.getClass().toString().split("\\.");
            String className = toSplit[1];

            // Debuggin Info
            System.out.println("ToSplit is: " + toSplit);
            System.out.println("ClassName: " + className);


        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
