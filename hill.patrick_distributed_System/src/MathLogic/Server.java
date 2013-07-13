package MathLogic;

import java.io.*;
import java.net.ServerSocket;

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
}
