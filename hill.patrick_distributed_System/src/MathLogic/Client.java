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
        OutputStream sOut;
        InputStream sIn;
        PrintWriter sWrite;
        BufferedReader br;
        boolean isRunning = true;

        public ClientSide() {
            try {
                connectToServer();
            } catch (IOException e) {
                isRunning = false;
                try {
                    s.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
        }

        private void connectToServer() throws IOException {
            System.out.println("Connecting to the server...");
            s = new Socket("localhost", 8080);
            sOut = s.getOutputStream();
            sIn = s.getInputStream();
            sWrite = new PrintWriter(sOut, true);
            br = new BufferedReader( new InputStreamReader(sIn));
            System.out.println("Connected to server");

            while(isRunning) {
                serverClientChatter();
            }
        }
        private void serverClientChatter() {
            String server = readString();
            switch(server) {
                case "classPaths":
                    // Prompt & send user's classname
                    System.out.println("Enter the full classpath of the class...");
                    System.out.println("i.e. : MathLogic.MyClass");
                    String className = scan.nextLine();
                    if(className.isEmpty())
                        className = "MathLogic.MyClass";
                    sendString(className);  // no error handling !!!
                    break;
                case "constructors":
                    // Get constructor list
                    String cons = readString();
                    System.out.println("Enter the index of the desired constructor...");
                    System.out.println(cons);
                    String temp = scan.nextLine();
                    if(temp.isEmpty())
                        temp = "0";
                    sendString(temp);
                    break;
                case "conTypes":
                    System.out.println("Enter the constructor parameter types comma delimited...");
                    System.out.println("i.e. : java.lang.String,java.lang.Integer");
                    temp = scan.nextLine();
                    if(temp.isEmpty())
                        temp = "java.lang.Integer";
                    sendString(temp);
                    System.out.println("enter the parameter types and values as follows comma delimited....");
                    System.out.println("i.e.: String:A,Integer:66");
                    temp = scan.nextLine();
                    if(temp.isEmpty())
                        temp = "MyParam String :)";
                    sendString(temp);
                    break;
                case "methods":
                    String methodList = readString();
                    System.out.println("Received Method List...");
                    System.out.println(methodList);
                    // Have user prompt for desired method...
                    System.out.println("Enter the # corresponding to the method you would like to invoke");
                    temp = scan.nextLine();
                    if(temp.isEmpty())
                        temp = "0";
                    sendString(temp);
                    break;
                case "methodTypes":
                    System.out.println("Enter the full classpath of the parameter...");
                    System.out.println("i.e.: MathLogic.MyParam");
                    temp = scan.nextLine();
                    if(temp.isEmpty())
                        temp = "MathLogic.MyParam";
                    sendString(temp);
                    break;
                case "final":
                    isRunning = false;
                    System.out.println("AAAAAAAAAAAAAAAND DONE!!!!!!");
                    String answer = readString();
                    System.out.println("Method return toString() is:");
                    System.out.println(answer);

                    try {
                        s.shutdownInput();
                        s.shutdownOutput();
                        s.close();
                        scan.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
        private String readString() {
            String temp = "";
            try {
                temp =  br.readLine();
            } catch (IOException e) {
                isRunning = false;
                e.printStackTrace();
            }
            return temp;
        }
        private void sendString(String s) {
            sWrite.println(s);
            sWrite.flush();
        }
    }
}
