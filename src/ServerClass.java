import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerClass {
    static ArrayList<MultiThreadHandlerClass> multiThreads = new ArrayList<>();
    public static ServerCourseManagement sCM = new ServerCourseManagement();
    private static ExecutorService pool = Executors.newFixedThreadPool(25);
    public static void main(String[] args) {
        System.out.println("hey");
        try {
            ServerSocket serverSocket = new ServerSocket(1427);
            System.out.println("---Server Started---");
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    System.out.println("user connected...");
                    MultiThreadHandlerClass mTHC = new MultiThreadHandlerClass(socket);
//                    Thread newThread = new Thread(mTHC);
//                    System.out.println(2);
//                    newThread.start();
//                    newThread.join();
                    pool.execute(mTHC);
                    multiThreads.add(mTHC);

                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
//            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            PrintWriter pw = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {

        }
//         catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
    }
}
