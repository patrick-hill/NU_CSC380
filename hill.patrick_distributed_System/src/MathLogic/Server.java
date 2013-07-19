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
                br = new BufferedReader( new InputStreamReader( s.getInputStream()));
                serverWrite = new PrintWriter(sOut, true);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Get Class
            Class c = clientClassLoading();
            // Get Constructor
            Object con = clientConstructorLoading(c);
            // Get Method to invoke
            Method m = clientMethodLoading(c, con);

        }
        private Class clientClassLoading() {
            sendString("classPaths");

            return getClassInstance(readString());
        }
        private Object clientConstructorLoading(Class c) {
            sendString("constructors");

            Constructor cons[] = c.getConstructors();
            String conList = "";
            int count = 0;
            for (Constructor c1 : cons)
                conList += (count++) + ": " +c1.toString() + "\n";
            sendString(conList);
            int conIndex = Integer.parseInt(readString());
            int conParams = cons[conIndex].getParameterTypes().length;
            Object con = null;
            if(conParams == 0) {
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
                con = clientConstructorParamTypeLoading(c, conParams);
            }
            return con;
        }
        private Object clientConstructorParamTypeLoading(Class c, int conParams) {
            sendString("conTypes");

            Class conParamClasses[] = new Class[conParams];
            String names[] = readString().split(",");   // schema java.lang.String,java.lang.Integer
            for (int i=0;i<conParams;i++)
                conParamClasses[i] = getClassInstance(names[i]);
            // parameter types from client
            String paramTypes[] = readString().split(",");  // schema String:A String,Integer:666
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
            Object con = null;
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
        return con;
        }

        private Method clientMethodLoading(Class c, Object con) {
            sendString("methods");

            Method allMethods[] = c.getDeclaredMethods();
            String mSend = "";
            int count = 0;
            for (Method m : allMethods){
                mSend += (count++) + ": " + m.getName();
            }
            sendString(mSend);
            int userMethod = Integer.parseInt(readString());
            Method m = allMethods[userMethod];
            Method toInvoke = null;

            // method parameters .....
            Class k[] = m.getParameterTypes();
            Class methodParamClass = null;
            if(k.length > 0)
                methodParamClass = clientMethodParamLoading(k.length);
            else {
                try {
                    toInvoke = c.getDeclaredMethod(allMethods[userMethod].getName(), methodParamClass);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
            // Run the method
            Object mParams[] = new Object[1];
            mParams[0] = methodParamClass;
            try {
                toInvoke.invoke(con, mParams);          // this works just fine !!!!!
            } catch (IllegalAccessException e) {        // but no return object ... wtf
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            Object returnType = null;
            try {
                returnType = toInvoke.invoke(con, mParams);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            if(returnType == null)
                System.out.println("NULLLLLLLLLL");
            else
                System.out.println(" it works mofo !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            sendString("final");
            sendString("sean");
            return m;
        }

        private Class clientMethodParamLoading(int methodParams) {
            sendString("methodTypes");

            Class c = getClassInstance(readString());
//            // Re-use of constructor parameter loading..... :(
//            Class methodParamClasses[] = new Class[methodParams];
//            String names[] = readString().split(",");   // schema java.lang.String,java.lang.Integer
//            for (int i=0;i<methodParams;i++)
//                methodParamClasses[i] = getClassInstance(names[i]);
//            // parameter types from client
//            String paramTypes[] = readString().split(",");  // schema String:A String,Integer:666
//            Object types[] = new Object[paramTypes.length];
//            for (int i=0;i<paramTypes.length;i++) {         // Only allows Strings & Ints
//                String t[] = paramTypes[i].split(":");
//                switch(t[0]) {
//                    case "Integer":
//                        types[i] = Integer.parseInt(t[1]);
//                        break;
//                    default:
//                        types[i] = t[1];
//                        break;
//                }
//            }
//
//            Object con = null;
//            try {
//                con = c.getConstructor(methodParamClasses).newInstance(types);
//            } catch (InstantiationException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            } catch (NoSuchMethodException e) {
//                e.printStackTrace();
//            }
//            // Get Class
////            Class k = clientClassLoading();
//            // Get Constructor
//            Object kCon = clientConstructorLoading(c);
            // Get Method to invoke
//            Method kMethod = clientMethodLoading(c, kCon);

//            Class methodParams = getClassInstance(readString());
//            Object methodCon = clientConstructorLoading(methodParams);
//          con = con = c.getConstructor(conParamClasses).newInstance(types);

            return c;
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
            Class cla = null;
            try {
                cla =  Class.forName(clientClass);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return cla;
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
