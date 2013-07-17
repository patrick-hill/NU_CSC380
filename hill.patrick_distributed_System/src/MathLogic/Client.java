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
        new ClientSide();
    }

    public static class ClientSide {
        Socket s;
        Scanner scan = new Scanner(System.in);

        public ClientSide() {
            try {
                connectToServer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void connectToServer() throws IOException {
            // Connect to the Server
            System.out.println("Connecting to the server...");
            s = new Socket("localhost", 8080);
            OutputStream sOut = s.getOutputStream();
            InputStream sIn = s.getInputStream();
            PrintWriter sWrite = new PrintWriter(sOut, true);
            BufferedReader br = new BufferedReader( new InputStreamReader(sIn));
            System.out.println("Connected to server");

            // Prompt & send user's classname
            System.out.println("Enter the full classpath you would like to use...");
            String className = scan.nextLine();
            sWrite.println(className);  // no error handling !!!
            sWrite.flush();

            // Get method list from server
            String methodList = br.readLine();
            System.out.println("Received Method List");
            String methods[] = methodList.split(", ");
            System.out.println("Server replied with: ");
            int count = 0;
            for (String str : methods) // {
                System.out.println("#"+(count++)+": "+str);
//                count++;
//            }

            // Have user prompt for desired method...
            System.out.println("Enter the # corresponding to the method you would like to invoke");
            String userPick = scan.nextLine();
            sWrite.println(userPick);
            sWrite.flush();

            // User enters classpath for param objects
            System.out.println("Method Parameters: Enter the classpath for each param...");
            System.out.println("i.e.: Math.Logic,Math.Resources.Integer,etc \n");
            String userParams = scan.nextLine();
            sWrite.println(userParams);
            sWrite.flush();

            // read final answer from the server
            String answer = br.readLine();
            System.out.println("Server Replied: " + answer);
            s.shutdownInput();
            s.shutdownOutput();
            s.close();
            scan.close();
        }
    }
}
