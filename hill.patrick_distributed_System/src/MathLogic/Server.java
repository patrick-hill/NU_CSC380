package MathLogic;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: Patrick
 * Date: 7/9/13
 * Time: 9:03 PM
 * To change this template use File | Settings | File Templates.
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

    /**
     * Created with IntelliJ IDEA.
     * User: Patrick
     * Date: 7/9/13
     * Time: 8:51 PM
     * To change this template use File | Settings | File Templates.
     */

    public static class MathServer implements Runnable {

        private Socket s;
        private OutputStream sOut;
        private String[] request;
        private int count, x, y, response;

        public MathServer(Socket s, int count) {
            this.s = s;
            this.count = count;
            System.out.println("MathServer #: " + count + " Created!!!!");
        }

        @Override
        public void run() {
            parseClient();
            performMath();
            sendResponse();
            printToConsole();
        }

        public void parseClient() {
            try {
                sOut = s.getOutputStream();
                BufferedReader br;
                br = new BufferedReader( new InputStreamReader( s.getInputStream()));
                request = br.readLine().split("-");  // schema== command-#,#
    //            String clientResponse = br.readLine();
    //            String[] request = clientResponse.split("-");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void performMath() {
            String split[] = request[1].split(",");
            x = Integer.parseInt(split[0]);
            y = Integer.parseInt(split[1]);

            if(request[0].equalsIgnoreCase("add"))
                response = x+y;
            else
                response = x-y;
        }

        public void sendResponse() {
            try {
                PrintWriter serverWrite = new PrintWriter(sOut, true);
                serverWrite.println(response);
                sOut.flush();
                s.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        private void printToConsole() {
            System.out.println(new StringBuilder()
                    .append("MathServer # ")
                    .append(count)
                    .append(" Received: ")
                    .append(x).append(" ").append(request[0]).append(" ").append(y)
                    .append(" and Replied: ")
                    .append(response)
                    .toString());
        }
    }
}
