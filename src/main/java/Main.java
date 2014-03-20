import org.eclipse.jetty.client.HttpClient;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static HttpClient httpClient;
    public static String baseUri = "http://registry-qa.mulesoft.com/api";
    public static String username = "username";
    public static String password = "password";
    public static String grant_type = "password";

    public static void main(String[] args) throws Exception {
        RestClient client = new RestClient(baseUri,username,password,grant_type);

        System.out.println("-----------------------------------------------------");
        System.out.println("Threads:");
        List<Thread> threads = new ArrayList<Thread>();
        for (int i = 0; i < 20; i++) {
            Runnable task = new ThreadRestCall(client);
            Thread worker = new Thread(task);
            // We can set the name of the thread
            worker.setName(String.valueOf(i));
            worker.start();

            // Remember the thread for later usage
            threads.add(worker);
        }
        for (int i = 0; i < 20; i++) {
            threads.get(i).run();
        }

        for (int i = 0; i < 20; i++) {
            threads.get(i).join();
        }
        System.out.println("End of the tests");
    }

}

