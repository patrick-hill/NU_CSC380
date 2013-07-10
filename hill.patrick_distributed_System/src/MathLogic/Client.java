package MathLogic;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Patrick
 * Date: 7/9/13
 * Time: 9:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class Client {

    public static void main(String[] args) {
        String request;
        Scanner s = new Scanner(System.in);
        System.out.println("Add or Sub (Subtract)?");
        request = s.nextLine();
        System.out.println("Enter your first integer...");
        request += "-" + s.nextInt();
        System.out.println("Enter your second integer...");
        request += "," + s.nextInt();

        // Connect to server and send the request

        try {
            Socket socket = new Socket("localhost", 8080);
            OutputStream sOut = socket.getOutputStream();
            InputStream sIn = socket.getInputStream();

            PrintWriter sWrite = new PrintWriter(sOut, true);
            sWrite.println(request);
            sWrite.flush();
            socket.shutdownOutput();

            BufferedReader br;
            br = new BufferedReader( new InputStreamReader(sIn));

            String serverResponse = br.readLine();

//            Scanner s2 = new Scanner(sIn);
//            String serverResponse = s2.nextLine();
            System.out.println("MathServer Replied: " + serverResponse);
            socket.shutdownInput();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
