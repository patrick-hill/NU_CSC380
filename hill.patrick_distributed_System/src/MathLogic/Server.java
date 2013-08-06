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
import java.util.Scanner;

/**
 * User: Patrick
 * Date: 7/9/13
 * Time: 9:03 PM
 */
public class Server {
    public static void main(String[] args) {
//        int count = 0;
//
//        try {
//            ServerSocket ss = new ServerSocket(8080);
//
//            while (true) {
//                if(ss.isBound()) {
//                    count ++;
//                    new ObjectServer(ss.accept(), count).run();
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        new ObjectServer();
    }

    protected static class ObjectServer implements Runnable {
        private Socket s;
        private BufferedReader br;
        private PrintWriter serverWrite;
        private OutputStream sOut;
//        private Class x;
        private Object parameterCon;

        public ObjectServer() {
            test();
        }
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
            Object con = clientConstructorLoading(c);   // Class con has a Class parameter!!!!!
            // Get Method to invoke and invoke it
            Object returned = clientMethodLoading(c, con);
            //Print out returned info

        }
        private void test(){
            System.out.println("In the test method, about to automate all this shit@!!!!!!!");
            // make the parameter class first, then the main constructor, then invoke
            try {
                Class c = Class.forName("MathLogic.MyParam");
                Scanner s = new Scanner(System.in);
                Class x = Class.forName(s.nextLine());
                Class tmp[] = new Class[1];
                tmp[0] = x;
                Object con = c.getDeclaredConstructor(tmp).newInstance(69);
//                Object con = c.getConstructor(x).newInstance(69);
//                Object con = c.getConstructor(int.class).newInstance(69);
                Method m = c.getDeclaredMethod("toString");
                Object r = m.invoke(con);
                System.out.println("Return from MyParam: " + r.toString());

                Class c2 = Class.forName("MathLogic.MyClass");
                Object con2 = c2.getConstructor().newInstance();
                Method m2 = c2.getDeclaredMethod("test", con.getClass());
                Object r2 = m2.invoke(con2, con);
                System.out.println("Return from MyClass: " + r2.toString());


            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
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
                // Parameter Class Constructor
                Object paramCon = clientConstructorParamTypeLoading(c, conParams);
                try {
                    con = c.getConstructor(MyClass.class).newInstance(paramCon);
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
            return con;
        }
        private Object clientConstructorParamTypeLoading(Class c, int conParams) {
            sendString("conTypes");

            Class conParamClasses[] = new Class[conParams];
            String names[] = readString().split(",");   // schema java.lang.String,java.lang.Integer
            for (int i=0;i<conParams;i++)
                conParamClasses[i] = getClassInstance(names[i]).getClass();
            // parameter types from client                  // MyParam Class ....
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
        private Object clientMethodLoading(Class c, Object con) {
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
            Object methodParamClassConstructor = null;
            if(k.length > 0) {
                methodParamClassConstructor = clientMethodParamLoading(k.length);
            }
            try {
                toInvoke = c.getDeclaredMethod(allMethods[userMethod].getName(), con.getClass());
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            // Run the method
            Object toReturn = null;
            try {
                if(methodParamClassConstructor !=  null)
                    toReturn = toInvoke.invoke(con, methodParamClassConstructor.getClass());
                else
                    toReturn = toInvoke.invoke(con);

            } catch (IllegalAccessException e) {        // but no return type
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            return toReturn;
        }
        private Object clientMethodParamLoading(int methodParams) {
            sendString("methodTypes");

            Class x = getClassInstance(readString());
            // Re-use of constructor parameter loading..... :(
            Class methodParamClasses[] = new Class[methodParams];
            sendString("conTypes");
            String names[] = readString().split(",");   // schema java.lang.String,java.lang.Integer
            for (int i=0;i<methodParams;i++)
                methodParamClasses[i] = getClassInstance(names[i]);
            Object types[] = new Object[methodParams];
            String tmp[] = readString().split(",");
            for (int i=0;i<methodParams;i++) {         // Only allows Strings & Ints
                String t[] = tmp[i].split(":");
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
                con = x.getConstructor(methodParamClasses).newInstance(types);
//                con = x.getConstructor(String.class).newInstance(types);
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
        private void sendReturn(Object r) {
            sendString(r.toString());
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
    }
}
