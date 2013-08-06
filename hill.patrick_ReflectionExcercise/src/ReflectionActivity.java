import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: Patrick
 * Date: 7/11/13
 * Time: 5:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReflectionActivity {

    private int id;
    protected String name;
    public double number;

    public ReflectionActivity() {
        this.id = 1;
    }

    public ReflectionActivity(String name, double number) {
        this.id = 1;
        this.name = name;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double addToNumber(double number) {
        return this.number + number;
    }

    public static void main(String[] args) throws IllegalAccessException,
            InstantiationException, NoSuchMethodException, ClassNotFoundException,
            InvocationTargetException, NoSuchFieldException {

        System.out.println("CONSTRUCTORS");
        Class c = Class.forName("ReflectionActivity");
        for(Constructor con : c.getDeclaredConstructors())
            System.out.println("Con String: " + con.toString());


        Object con = c.getConstructor(String.class, double.class).newInstance("dude", 6);

        Field[] fields = c.getDeclaredFields();
        System.out.println("FIELDS");
        for(Field f : fields) {
            System.out.println("Field Name: " + f.getName());
            System.out.println("Field Type: " + f.getType());
        }
        Method m = c.getDeclaredMethod("addToNumber", double.class);
        System.out.println(m.invoke(con, 666));
        System.out.println("METHODS");

        Method[] allMethods = c.getDeclaredMethods();
        for(Method m2 : allMethods) {
            System.out.println("Method Name: " + m2.getName());
            System.out.println("Return Type: " + m2.getReturnType());
            Class<?>[] pType  = m.getParameterTypes();
            for (Class<?> d : pType)
                System.out.println("Parameters : " + d);
//            System.out.println("Parameters : " + m.getParameterTypes());
        }

        System.out.println("Performing dat madness");
        Method m3 = c.getDeclaredMethod(allMethods[1].getName(), allMethods[1].getParameterTypes());
        m3.invoke(con);


        System.out.println("Con List");
        Constructor cons[] = c.getConstructors();
        String conList = "";
        int count = 0;
        for (Constructor c1 : cons)
            conList += (count++) + ": " +c1.toString() + "\n";
        System.out.println(conList);
        System.out.println("ConMods");
        int conParams = cons[1].getParameterTypes().length;
        System.out.println(conParams);

        System.out.println("Method Param Types");
        Class k[] = m.getParameterTypes();
        System.out.println(k[0]);


            test();

    }
    private static void test() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        System.out.println("TESTING");
        System.out.println("TESTING");
        System.out.println("TESTING");
        System.out.println("TESTING");
        // get class
        // get constructor
            // get con paramters
        // get methods, if any
        // get return type.... how?

        Class c = Class.forName("MyTestString");
//        Object con = c.getConstructor(String.class, double.class).newInstance("dude", 6);
        Object con = c.getConstructor(String.class).newInstance("Test String");
        Method m = c.getDeclaredMethod("toString");
        Object returned = m.invoke(con);
        System.out.println("Return From toString is: "+  returned.toString());

        Class c2 = Class.forName("MyClassTest");
        Object con2 = c2.getConstructor().newInstance();
//        Method m3 = c.getDeclaredMethod(allMethods[1].getName(), allMethods[1].getParameterTypes());
        Method all[] = c2.getDeclaredMethods();
        Method m2 = c2.getDeclaredMethod(all[0].getName(), con.getClass());
        System.out.println("ANSWER: when invoking, pass both constructors!!!!");
        Object r2 = m2.invoke(con2, con);   // FINALLY IT FINALLY FINALLY FINALLY FINALLY WORKS!!!!!!!!!!!!
        System.out.println("Return From toString is: "+  r2.toString());

    }
}






































