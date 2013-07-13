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
            connectToServer();
        }

        private void connectToServer() {
            try {
                System.out.println("Connecting to the server...");
                s = new Socket("localhost", 8080);
                OutputStream sOut = s.getOutputStream();
                InputStream sIn = s.getInputStream();
                PrintWriter sWrite = new PrintWriter(sOut, true);
                BufferedReader br;
                br = new BufferedReader( new InputStreamReader(sIn));
                System.out.println("Connected to server and Requesting the server's methods...");
                sWrite.println("methods");
                sWrite.flush();

                String methodList = br.readLine();
                System.out.println("Received Method List");
                String methods[] = methodList.split(", ");

                System.out.println("Server replied with: ");
                int count2 = 0;
                for (String str : methods) {
                    System.out.println("#"+count2+": "+str);
                    count2++;
                }
                System.out.println("Enter the # corresponding to the method you would like to invoke");
                String userPick = scan.nextLine();
                sWrite.println(userPick);
                sWrite.flush();

                System.out.println("Enter your parameters comma separated as per your method choice");
                System.out.println("i.e.: 4,5,6 or 200,3000,8 \n");
                String userParams = scan.nextLine();
                sWrite.println(userParams);
                sWrite.flush();

                // read final answer from the server
                String answer = br.readLine();
                System.out.println("MathServer Replied: " + answer);
                s.shutdownInput();
                s.shutdownOutput();
                s.close();
                scan.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
