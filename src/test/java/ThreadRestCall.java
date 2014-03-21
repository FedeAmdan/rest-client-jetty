/*
 * (c) 2003-2014 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */

import org.eclipse.jetty.client.api.ContentResponse;
import static org.junit.Assert.assertEquals;


public class ThreadRestCall implements Runnable {
    private final RestClient client;
    private final String name;

    ThreadRestCall(RestClient client, String name) {
        this.client = client;
        this.name = name;
    }

    @Override
    public void run() {
        runDeleteNotFound();
        runGet();
        runPost();
        runDelete();
    }

    public void runDeleteNotFound(){
        try{
            System.out.println("Trying DELETE to /organizations/current/owners/testRestClient1 (not found)");
            ContentResponse response5 = client.delete("/organizations/current/owners/testRestClient1")
                    .header("Content-Type", "application/json")
                    .send();
            System.out.println("Delete Status: " + response5.getStatus());
            System.out.println("Delete Content: " + response5.getContentAsString());
            assertEquals(404,response5.getStatus());
        }
        catch (Exception ex){System.out.println(ex.toString());}
    }

    public void runDelete(){
        try{
            System.out.println("Trying DELETE to /services/testclient" + name);
            ContentResponse response5 = client.delete("/services/testclient" + name)
                    .header("Content-Type", "application/json")
                    .send();
            System.out.println("Delete Status of /services/testclient" + name + ": " + response5.getStatus());
            System.out.println("Delete Content of /services/testclient" + name + ": " + response5.getContentAsString());
            assertEquals(204,response5.getStatus());
        }
        catch (Exception ex){System.out.println(ex.toString());}
    }

    public void runPost(){
        try{
            System.out.println("Trying POST to /services new service testclient" + name);
            String payloadPost = "{\"name\": \"testclient" + name + "\", \"summary\": \"string\", \"description\": \"string\",\"contractExpression\": \"string\",\"tags\": [\"string\"],\"taxonomies\": [{\"id\": \"string\",\"taxonomy\": \"string\",\"path\": [\"string\"]}],\"published\": false}";
            ContentResponse response2 = client.post("/services")
                    .payload(payloadPost)
                    .header("Content-Type", "application/vnd.mulesoft.habitat+json")
                    .send();
            System.out.println("Post Status of /services testclient" + name + " :" + response2.getStatus());
            System.out.println("Post Content of /services testclient" + name + " :" + response2.getContentAsString());
            assertEquals(201,response2.getStatus());
        }
        catch (Exception ex){System.out.println(ex.toString());}
    }

    public void runGet(){
        try{
            System.out.println("Trying GET to organizations/current/owners");

            ContentResponse response = client.get("/organizations/current/owners").send();

            System.out.println("Get Status of organizations/current/owners: " + response.getStatus());
            System.out.println("Get Content of organizations/current/owners: " + response.getContentAsString());
            assertEquals(200,response.getStatus());
        }
        catch (Exception ex){System.out.println(ex.toString());}
    }

}
