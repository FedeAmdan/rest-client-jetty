import org.eclipse.jetty.client.api.ContentResponse;


public class ThreadRestCall implements Runnable {
    private final RestClient client;

    ThreadRestCall(RestClient client) {
        this.client = client;
    }

    @Override
    public void run() {
        runGet();
        runDelete();
        runPost();
    }

    public void runDelete(){
        try{
            System.out.println("Trying DELETE to /organizations/current/owners/testRestClient1 (not found)");
            ContentResponse response5 = client.delete("/organizations/current/owners/testRestClient1")
                    .header("Content-Type", "application/json")
                    .send();
            System.out.println("Delete Status: " + response5.getStatus());
            System.out.println("Delete Content: " + response5.getContentAsString());
        }
        catch (Exception ex){System.out.println(ex.toString());}
    }

    public void runPost(){
        try{
        System.out.println("Trying POST to /services new service testclient1");
        String payloadPost = "{\"name\": \"testclient1\", \"summary\": \"string\", \"description\": \"string\",\"contractExpression\": \"string\",\"tags\": [\"string\"],\"taxonomies\": [{\"id\": \"string\",\"taxonomy\": \"string\",\"path\": [\"string\"]}],\"published\": false}";
        ContentResponse response2 = client.post("/services")
                .payload(payloadPost)
                .header("Content-Type", "application/vnd.mulesoft.habitat+json")
                .send();
        System.out.println("Post Status: " + response2.getStatus());
        System.out.println("Post Content: " + response2.getContentAsString());
        }
        catch (Exception ex){System.out.println(ex.toString());}
    }

    public void runGet(){
        try{
        System.out.println("Trying GET to organizations/current/owners");

        ContentResponse response = client.get("/organizations/current/owners").send();

        System.out.println("Status: " + response.getStatus());
        System.out.println("Content: " + response.getContentAsString());
        }
        catch (Exception ex){System.out.println(ex.toString());}
    }

}
