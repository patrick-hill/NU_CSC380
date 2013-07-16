package MathLogic;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * User: Patrick
 * Date: 7/9/13
 * Time: 9:03 PM
 */
public class Server {
    public static void main(String[] args) {
        int count = 0;

        try {
            ServerSocket ss = new ServerSocket(8080);

            while (true) {
                if(ss.isBound()) {
                    count ++;
                    new MathServer(ss.accept(), count).run();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected class ObjectServer implements Runnable {
        private Socket s;
        private OutputStream sOut;
        private InputStream sIn;
        private Class c = null;

        private String clientClass;
        private Method[] classMethods;

        public ObjectServer(Socket s, int connectionCount) {
            this.s = s;
            System.out.println("Server #: "+connectionCount + " has been started.");
        }

        @Override
        public void run() {
            startConnection();
        }

        public void startConnection() {
            try {
                sOut = s.getOutputStream();
                BufferedReader br;
                br = new BufferedReader( new InputStreamReader( s.getInputStream()));
                PrintWriter serverWrite = new PrintWriter(sOut, true);
                Class c;
                clientClass = br.readLine();
                c = getInstance();
                // Received class name
                Object con = c.getConstructor().newInstance();
                classMethods = c.getDeclaredMethods();
                System.out.println("Sending Methods...");
                String methodList = "";
                for(Method m : classMethods) {
                    methodList += m + ", ";
                }
                serverWrite.println(methodList);
                serverWrite.flush();

                System.out.println("Waiting for client response...");
                int methodRequest = Integer.parseInt(br.readLine());
                Method m = classMethods[methodRequest];

                String clientParams;
                clientParams = br.readLine(); // schema param1, param2
                String toMethods[] = clientParams.split(",");
                // Convert String to int's
                int intPs[] = new int[toMethods.length];
                for (int i=0;i<toMethods.length;i++)
                    intPs[i] = Integer.parseInt(toMethods[i]);
                // Invoke proper method
                Method m2 = c.getDeclaredMethod(m.getName(),
                        m.getParameterTypes());
                Object answer = m2.invoke(con, intPs);
                serverWrite.println(answer.toString());

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

        private Class getInstance() throws ClassNotFoundException,
                IllegalAccessException, InstantiationException {
            if(c == null) {
                c = Class.forName(clientClass);
                c.newInstance();
            }
            return c;
        }
    }
}
