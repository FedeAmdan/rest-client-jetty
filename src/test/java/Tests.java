/*
 * (c) 2003-2014 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class Tests
{

    public static HttpClient httpClient;
    public static String baseUri = "http://registry-qa.mulesoft.com/api";
    public static String username = "username";
    public static String password = "password";
    public static String grantType = "password";
    public static String clientId = "WEBUI";
    public static String contentType = "application/x-www-form-urlencoded";
    public static String scope = "ADMIN_ORGANIZATIONS READ_SERVICES WRITE_SERVICES CONSUME_SERVICES APPLY_POLICIES READ_CONSUMERS WRITE_CONSUMERS CONTRACT_MGMT CONSUME_POLICIES";

    @Test
    public void getServiceOwners() throws Exception {
        OAuthAuthenticationBuilder builder = new OAuthAuthenticationBuilder();
        builder.username(username)
                .password(password)
                .grantType(grantType)
                .scope(scope)
                .clientId(clientId)
                .contentType(contentType)
                .baseUri(baseUri);

        RestClient client = new RestClient(baseUri,builder);
        System.out.println("Trying GET to organizations/current/owners");

        ContentResponse response = client.get("/organizations/current/owners").send();

        System.out.println("Status: " + response.getStatus());
        System.out.println("Content: " + response.getContentAsString());

        assertEquals(200,response.getStatus());
    }

    @Test
    public void postPutAndDeleteService() throws Exception {

        OAuthAuthenticationBuilder builder = new OAuthAuthenticationBuilder();
        builder.username(username)
               .password(password)
               .grantType(grantType)
               .scope(scope)
               .clientId(clientId)
               .contentType(contentType)
               .baseUri(baseUri);

        RestClient client = new RestClient(baseUri,builder);
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

    @Ignore
    public void threadsUsingGetPostDelete() throws Exception {
        OAuthAuthenticationBuilder builder = new OAuthAuthenticationBuilder();
        builder.username(username)
                .password(password)
                .grantType(grantType)
                .scope(scope)
                .clientId(clientId)
                .contentType(contentType)
                .baseUri(baseUri);

        RestClient client = new RestClient(baseUri,builder);
        System.out.println("-----------------------------------------------------");
        System.out.println("Threads:");
        List<Thread> threads = new ArrayList<Thread>();
        for (int i = 0; i < 20; i++) {
            Runnable task = new ThreadRestCall(client,String.valueOf(i));
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