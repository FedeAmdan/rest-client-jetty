import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;

public class Main {

    public static HttpClient httpClient;
    public static String baseUri = "http://registry-qa.mulesoft.com/api";
    public static String username = "USERNAME HERE";
    public static String password = "PASSWORD HERE";
    public static String grant_type = "password";

    public static void main(String[] args) throws Exception {
        RestClient client = new RestClient(baseUri,username,password,grant_type);

        System.out.println("Trying GET to organizations/current/owners");
        ContentResponse response = client.get("/organizations/current/owners");
        System.out.println("Status: " + response.getStatus());
        System.out.println("Content: " + response.getContentAsString());

        System.out.println("Trying POST to /services new service testclient1");
        String payloadPost = "{\"name\": \"testclient1\", \"summary\": \"string\", \"description\": \"string\",\"contractExpression\": \"string\",\"tags\": [\"string\"],\"taxonomies\": [{\"id\": \"string\",\"taxonomy\": \"string\",\"path\": [\"string\"]}],\"published\": false}";
        ContentResponse response2 = client.post("/services",payloadPost);
        System.out.println("Status: " + response2.getStatus());
        System.out.println("Content: " + response2.getContentAsString());

        System.out.println("Trying PUT to /services/testclient1");
        String payloadPut = "{\"name\": \"testclient1\", \"summary\": \"string\", \"description\": \"string\",\"contractExpression\": \"string\",\"tags\": [\"string\"],\"taxonomies\": [{\"id\": \"string\",\"taxonomy\": \"string\",\"path\": [\"string\"]}],\"published\": false}";
        ContentResponse response3 = client.put("/services/testclient1",payloadPut);
        System.out.println("Status: " + response3.getStatus());
        System.out.println("Content: " + response3.getContentAsString());

        System.out.println("Trying DELETE to /services/testclient1");
        ContentResponse response4 = client.delete("/services/testclient1");
        System.out.println("Status: " + response4.getStatus());
        System.out.println("Content: " + response4.getContentAsString());

        System.out.println("Trying DELETE to /organizations/current/owners/testRestClient1 (not found)");
        ContentResponse response5 = client.delete("/organizations/current/owners/testRestClient1");
        System.out.println("Status: " + response5.getStatus());
        System.out.println("Content: " + response5.getContentAsString());

    }

    /*
    public static void main(String[] args) throws Exception {

        SslContextFactory sslContextFactory = new SslContextFactory();
        httpClient = new HttpClient(sslContextFactory);
        httpClient.start();

        OAuthAuthentication oauth = new OAuthAuthentication(httpClient);
        String token = oauth.getToken();
        System.out.println("OAuth token: "+ token);
        ContentResponse response = action(HttpMethod.GET, token, "organizations/current/owners/");
        System.out.println("Status: " + response.getStatus());
        System.out.println("Content: " + response.getContentAsString());
    }

    public static ContentResponse action(String token, String resource,Map<String,String> headers,Map<String,String> params) throws InterruptedException, ExecutionException, TimeoutException
    {
        String urlRequest = baseUri + resource;
        Request request = httpClient
                .newRequest(urlRequest)
                .param("access_token", token);

        for (String key : headers.keySet()) {
            String value = headers.get(key);
            request.header(key,value);
        }
        for (String key : params.keySet()){
            String value = headers.get(key);
            request.param(key,value);
        }

        request.method(HttpMethod.POST);
        return request.send();
    }

    public static ContentResponse action(HttpMethod method, String token, String resource) throws InterruptedException, ExecutionException, TimeoutException {
        String urlRequest = baseUri + resource;
        Request request = httpClient
            .newRequest(urlRequest)
            .param("access_token", token);
        request.method(method);
        return request.send();
    }          */

}

