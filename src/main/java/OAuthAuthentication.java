import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.http.HttpMethod;
import org.joda.time.DateTime;

import java.io.IOException;

public class OAuthAuthentication {

    HttpClient httpClient;
    private DateTime time;
    private String username;
    private String password;
    private String grant_type;

    public OAuthAuthentication(HttpClient httpClient, String username, String password,String grant_type){
        this.httpClient = httpClient;
        this.username = username;
        this.password = password;
        this.grant_type = grant_type;
    }

    public synchronized String getNewToken() throws Exception {
        String json = authenticateASRapi();
        String token = getTokenFromJSON(json);
        System.out.println("OAuth token: "+ token);
        return token;

    }
    private synchronized String authenticateASRapi() throws Exception {

        Request request = httpClient
                .newRequest("https://registry-qa.mulesoft.com/api/access-token")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("grant_type", grant_type)
                .header("username",username)
                .header("password",password)
                .header("client_id","WEBUI")
                .header("scope", "ADMIN_ORGANIZATIONS+READ_SERVICES+WRITE_SERVICES+CONSUME_SERVICES+APPLY_POLICIES+READ_CONSUMERS+WRITE_CONSUMERS+CONTRACT_MGMT+CONSUME_POLICIES");

        request.method(HttpMethod.POST);
        ContentResponse response = request.send();
        return response.getContentAsString();
    }

    public String getTokenFromJSON(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = mapper.getFactory();
        JsonParser parser = factory.createParser(json);
        JsonNode node = mapper.readTree(parser);
        //JsonNode node = mapper.readValue(json);
        return node.findValues("access_token").get(0).asText();
    }
}
