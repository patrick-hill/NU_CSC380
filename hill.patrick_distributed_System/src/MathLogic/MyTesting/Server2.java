package MathLogic.MyTesting;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: Patrick
 * Date: 7/18/13
 * Time: 9:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class Server2 {

    public static void main(String[] args) {
        new ObjectServer().run();
    }

    protected static class ObjectServer implements Runnable {
        private Socket s;
        private BufferedReader br;
        private PrintWriter serverWrite;
        private OutputStream sOut;

        @Override
        public void run() {
            startConnection();
        }

        public void startConnection() {
            // Get Class
            Class c = clientClassLoading();
            // Get Constructor
            Object con = clientConstructorLoading(c);
            // Get Method to invoke
            Method m = clientMethodLoading(c, con);
        }
        private Class clientClassLoading() {
            return getClassInstance("MathLogic.MyClass");
        }
        private Object clientConstructorLoading(Class c) {
            Constructor cons[] = c.getConstructors();
            int conParams = cons[0].getParameterTypes().length;
            Object con =  clientConstructorParamTypeLoading(c, conParams);
            return con;
        }
        private Object clientConstructorParamTypeLoading(Class c, int conParams) {
            Class conParamClasses[] = new Class[conParams];
//            String names[] = readString().split(",");   // schema java.lang.String,java.lang.Integer
            String names[] = new String[0];
            names[0] = "MathLogic.MyParam";
            for (int i=0;i<conParams;i++)
                conParamClasses[i] = getClassInstance(names[i]);
            // parameter types from client
//            String paramTypes[] = readString().split(",");  // schema String:A String,Integer:666
            String paramTypes[] = new String[0];
            paramTypes[0] = "java";
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

            Method allMethods[] = c.getDeclaredMethods();
            Method m = allMethods[0];
            Method toInvoke = null;

            // method parameters .....
            Class k[] = m.getParameterTypes();
            Class methodParamClass = null;
            if(k.length > 0)
                methodParamClass = clientMethodParamLoading(k.length);
            else {
                try {
                    toInvoke = c.getDeclaredMethod(allMethods[0].getName(), methodParamClass);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
            // make the return type class.
            Class returnType = toInvoke.getReturnType();


            try {
                toInvoke.invoke(con);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            sendString(returnType.toString());
            return m;
        }

        private Class clientMethodParamLoading(int methodParams) {

            Class c = getClassInstance("MathLogic.MyParam");
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
