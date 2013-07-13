package MathLogic;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * User: Patrick
 * Date: 7/13/13
 * Time: 10:37 AM
 */
public class MathServer implements Runnable {
    private Socket s;
    private OutputStream sOut;
    private int serverCount;
    private Method[] allMethods;
    private String clientParams;
    private Object answer;

    public MathServer(Socket s, int serverCount) {
        this.s = s;
        this.serverCount = serverCount;
        System.out.println("MathServer #: " + serverCount + " Created!!!!");
    }

    @Override
    public void run() {
        parseClient();
    }

    private void parseClient() {
        try {
            sOut = s.getOutputStream();
            BufferedReader br;
            br = new BufferedReader( new InputStreamReader( s.getInputStream()));
            PrintWriter serverWrite = new PrintWriter(sOut, true);
            Class c;
            String clientResponse = br.readLine();
            String methodList = "";
            if(clientResponse.equalsIgnoreCase("methods")) {
                c = Class.forName("MathLogic.Math");
                c.newInstance();
                Object con = c.getConstructor().newInstance();
                allMethods = c.getDeclaredMethods();
                System.out.println("Sending Methods...");
                for(Method m : allMethods) {
                    methodList += m + ", ";
                }
                serverWrite.println(methodList);
                serverWrite.flush();

                System.out.println("Waiting for client response...");
                int methodRequest = Integer.parseInt(br.readLine());
                Method m = allMethods[methodRequest];

                clientParams = br.readLine(); // schema param1, param2
                String toMethods[] = clientParams.split(",");
                // Convert String to int's
                int intPs[] = new int[toMethods.length];
                for (int i=0;i<toMethods.length;i++)
                    intPs[i] = Integer.parseInt(toMethods[i]);
                // Invoke proper method
                Method m2 = c.getDeclaredMethod(m.getName(), m.getParameterTypes());
                answer = m2.invoke(con, intPs);
                serverWrite.println(answer.toString());
            }
        sOut.flush();
        s.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private void printToConsole() {
        System.out.println(new StringBuilder()
                .append("MathServer # ")
                .append(serverCount)
                .append(" Received: ")
                .append(clientParams)
                .append(" and Replied: ")
                .append(answer.toString())
                .toString());
    }
}
