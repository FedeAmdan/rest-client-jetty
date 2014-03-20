import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class Tests
{

    public HttpClient httpClient;
    public String baseUri = "http://registry-qa.mulesoft.com/api";
    public String username = "username";
    public String password = "password";
    public String grant_type = "password";

    @Test
    public void getServiceOwners() throws Exception {
        RestClient client = null;

        client = new RestClient(baseUri,username,password,grant_type);

        System.out.println("Trying GET to organizations/current/owners");

        ContentResponse response = client.get("/organizations/current/owners").send();

        System.out.println("Status: " + response.getStatus());
        System.out.println("Content: " + response.getContentAsString());

        assertEquals(200,response.getStatus());
    }

    @Test
    public void postPutAndDeleteService() throws Exception {
        RestClient client = null;

        client = new RestClient(baseUri,username,password,grant_type);
        System.out.println("Trying POST to /services new service testclient1");
        String payloadPost = "{\"name\": \"testclient1\", \"summary\": \"string\", \"description\": \"string\",\"contractExpression\": \"string\",\"tags\": [\"string\"],\"taxonomies\": [{\"id\": \"string\",\"taxonomy\": \"string\",\"path\": [\"string\"]}],\"published\": false}";
        ContentResponse response2 = client.post("/services")
                .payload(payloadPost)
                .header("Content-Type", "application/vnd.mulesoft.habitat+json")
                .send();
        System.out.println("Status: " + response2.getStatus());
        System.out.println("Content: " + response2.getContentAsString());
        assertEquals(201,response2.getStatus());

        System.out.println("Trying PUT to /services/testclient1");
        String payloadPut = "{\"name\": \"testclient1\", \"summary\": \"string\", \"description\": \"string\",\"contractExpression\": \"string\",\"tags\": [\"string\"],\"taxonomies\": [{\"id\": \"string\",\"taxonomy\": \"string\",\"path\": [\"string\"]}],\"published\": false}";
        ContentResponse response3 = client.put("/services/testclient1")
                .header("Content-Type","application/vnd.mulesoft.habitat+json")
                .payload(payloadPut)
                .send();
        System.out.println("Status: " + response3.getStatus());
        System.out.println("Content: " + response3.getContentAsString());

        assertEquals(204,response3.getStatus());

        System.out.println("Trying DELETE to /services/testclient1");
        ContentResponse response4 = client.delete("/services/testclient1")
                .send();
        System.out.println("Status: " + response4.getStatus());
        System.out.println("Content: " + response4.getContentAsString());
        assertEquals(204,response4.getStatus());

    }
}