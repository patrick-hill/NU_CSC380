package MathLogic;

import MathLogic.old.MathServer;

import java.io.*;
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
                    new ObjectServer(ss.accept(), count).run();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static class ObjectServer implements Runnable {
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
            startConnection();
        }

        public void startConnection() {
            // Get the connection all setup and working
            try {
                sOut = s.getOutputStream();
                br = new BufferedReader( new InputStreamReader( s.getInputStream()));
                serverWrite = new PrintWriter(sOut, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Client Class Loading
//            Class c = clientClassLoading();
            clientConstructorLoading();
        }

        private Class clientClassLoading() {
            return getClassInstance(readString());
        }
        private void clientConstructorLoading() {
            Class c = getClassInstance(readString());
//            String test = readString();
//            System.out.println("String is: " + test);
//            Class c = Class.forName(readString());
            Constructor cons[] = c.getConstructors();
            int count = 0;
            if(cons.length == 1)
                sendString((count++) + ": " + cons[0].toString());
            else {
                String conList = "";
                int count2 = 0;
                for (Constructor c1 : cons)
                    conList += (count2++) + ": " +c1.toString() + "\n";
                // Send constructors
                sendString(conList);
            }
            int conIndex = Integer.parseInt(readString());
            int conParams = cons[conIndex].getParameterTypes().length;
            Object con = null;
            if(conParams == 0) {
                sendString("skip");
                try {
                    con = c.getConstructor().newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
            else {
                sendString("types");
                Class conParamClasses[] = new Class[conParams];
                // Get class names for con parameters.....
                String temp = readString();     // schema java.lang.String,java.lang.Integer
                String names[] = temp.split(",");
                for (int i=0;i<conParams;i++) {
                    conParamClasses[i] = getClassInstance(names[i]);
                }
                // parameter types from client
                temp = readString();    // schema String:A String,Integer:666
                String paramTypes[] = temp.split(",");

                Object types[] = new Object[paramTypes.length];

                for (int i=0;i<paramTypes.length;i++) {         // Only allows Strings & Ints
                    String t[] = paramTypes[i].split(":");
                    switch(t[0]) {
                        case "Integer":
                            types[i] = Integer.parseInt(t[1]);
                            break;
                        default:
                            types[i] = t[1];
                            break;
                    }
                }
                try {
                    con = c.getConstructor(conParamClasses).newInstance(types);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
            // call methods now.... jesus this is a pain in the ass....

            Method allMethods[] = c.getDeclaredMethods();
            String mSend = "";
            count = 0;
            for (Method m : allMethods){
                mSend += (count++) + ": " + m.getName();
            }
            sendString(mSend);
            int userMethod = Integer.parseInt(readString());
            Method m = allMethods[userMethod];
            Method toInvoke = null;

            // method parameters .....
            Class k[] = m.getParameterTypes();

            // 10:30... time to more on to other classes.....

            // again, too much work for too little a time frame!

            try {
                toInvoke = c.getDeclaredMethod(allMethods[userMethod].getName(), // Method params go here);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            Class returnType = toInvoke.getReturnType();
            try {
                toInvoke.invoke(con);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            sendString(returnType.toString());
        }

        private String readString() {
            String tmp = "";
            try {
                tmp = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return tmp;
        }
        private void sendString(String msg) {
            serverWrite.println(msg);
            serverWrite.flush();
        }
        private Class getClassInstance(String clientClass) {
            if(c == null) {
                try {
                    c = Class.forName(clientClass);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Made class: " + c.getName());
            return c;
        }
        private String makeClassConstructorList(Class c) {
            String conList = "";
            for (Constructor con : c.getDeclaredConstructors())
                conList += con.toString();
            return conList;
        }
        private Object getClassConstructor(Class c) {
            Object tmp = null;
            try {
                tmp = c.getConstructor().newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IllegalAccessException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (InvocationTargetException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (NoSuchMethodException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            return tmp;
        }
        private Method[] getMethodsFromClass(Class c) {
            try {
                Object con = c.getConstructor().newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IllegalAccessException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (InvocationTargetException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (NoSuchMethodException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            return c.getDeclaredMethods();
        }
        private String makeClassMethodList(Method[] classMethods) {
            String methodList = "";
            for(Method m : classMethods)
                methodList += m + ", ";
            return methodList;
        }
    }
}
