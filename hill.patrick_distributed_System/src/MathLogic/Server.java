package MathLogic;

import MathLogic.old.MathServer;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;

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
        private BufferedReader br;
        private PrintWriter serverWrite;
        private OutputStream sOut;
        private Class c = null;

        public ObjectServer(Socket s, int connectionCount) {
            this.s = s;
            System.out.println("Server #: "+connectionCount + " has been started.");
        }

        @Override
        public void run() {
            try {
                startConnection();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (ClassNotFoundException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (NoSuchMethodException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (InvocationTargetException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (InstantiationException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IllegalAccessException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        public void startConnection() throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException {
            // Get the connection all setup and working
            sOut = s.getOutputStream();
            br = new BufferedReader( new InputStreamReader( s.getInputStream()));
            serverWrite = new PrintWriter(sOut, true);

            // Get classname from client
            String clientClass = readString();
            c = getClassInstance(clientClass);          // Singleton Pattern

            // Get & Send constructor list to client
            Object con = c.getConstructor().newInstance();

            // Get class methods
            Method[] classMethods = getMethodsFromClass(c);
            // Send method list to client
            String methodList = makeClassMethodList(classMethods);
            sendString(methodList);

            // Get method index from client
            Method m = classMethods[Integer.parseInt(readString())];

            // Client sends params for chosen method
            String temp = readString();         // schema param1,param2
            String clientParams[] = temp.split(",");

            // Create a Class array to hold clients class parameters...
            Class params[] = new Class[clientParams.length];
            for(int i=0;i<clientParams.length;i++)
                    params[i] = getClassInstance(clientParams[i]);

            // Send Class methods to client
            Object[] cons = new Object[params.length];
            Annotation[] as = c.getAnnotations();
            Method[][] paramMethodArray = new Method[params.length][params.length];
            String[] paramMethodString = new String[params.length];

            for(int i=0;i<params.length;i++) {
                cons[i] = getClassConstructor(params[i]);
                paramMethodArray[i] = getMethodsFromClass(params[i]);
                paramMethodString[i] = makeClassMethodList(paramMethodArray[i]);
            }

            // Invoke Clients Method
            Method m2 = c.getDeclaredMethod(m.getName(),
                    m.getParameterTypes());
            Object answer = m2.invoke(con, params); // method must accept an array of Objects

            // Send client the final response before closing connection
            serverWrite.println(answer.toString());
        }

        private String readString() throws IOException {
            return br.readLine();
        }
        private void sendString(String msg) {
            serverWrite.println(msg);
            serverWrite.flush();
        }
        private Class getClassInstance(String clientClass) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
            if(c == null) {
                c = Class.forName(clientClass);
//                c.newInstance();
            }
            return c;
        }
        private String makeClassConstructorList(Class c) {
            String conList = "";
            for (Constructor con : c.getDeclaredConstructors())
                conList += con.toString();
            return conList;
        }
        private Object getClassConstructor(Class c) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
            return c.getConstructor().newInstance();
        }
        private Method[] getMethodsFromClass(Class c) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
            Object con = c.getConstructor().newInstance();
            return c.getDeclaredMethods();
        }
        private String makeClassMethodList(Method[] classMethods) {
            String methodList = "";
            for(Method m : classMethods)
                methodList += m + ", ";
            return methodList;
        }
        public List getClassesInPackage(String packageName) throws Exception {
            URL packageUrl = this.getClass().getClassLoader().getResource(packageName.replace(".", "/"));
            List allClasses = new ArrayList();
//            if(packageUrl != null) {
//                Path packagePath = Paths.get(packageUrl.toURI());
//                if(Files.isDirectory(packagePath)) {
//                    try(DirectoryStream ds = Files.newDirectoryStream(packagePath, "*.class")) {
//                        for(Path d : ds) {
//                            allClasses.add(Class.forName(packageName + "." +
//                                    d.getFileName().toString().replace(".class", "")));
//                        }
//                    }
//                }
//                return allClasses;
//            }
            return null;
        }
    }
}
