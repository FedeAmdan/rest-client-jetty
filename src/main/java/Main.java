import org.eclipse.jetty.client.HttpClient;

public class Main {

    public static HttpClient httpClient;
    public static String baseUri = "http://registry-qa.mulesoft.com/api";
    public static String username = "username";
    public static String password = "password";
    public static String grant_type = "password";
    public static String clientid = "WEBUI";
    public static String contentType = "application/x-www-form-urlencoded";
    public static String scope = "ADMIN_ORGANIZATIONS READ_SERVICES WRITE_SERVICES CONSUME_SERVICES APPLY_POLICIES READ_CONSUMERS WRITE_CONSUMERS CONTRACT_MGMT CONSUME_POLICIES";

    public static void main(String[] args) throws Exception {

    }

}

