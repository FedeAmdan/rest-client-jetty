/*
 * (c) 2003-2014 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.http.HttpMethod;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class OAuthAuthentication {
    HttpClient httpClient;
    private String username;
    private String password;
    private String grant_type;
    private String baseUri;
    private String contentType;
    private String clientId;
    private String scope;

    public OAuthAuthentication(HttpClient httpClient)
    {
        this.httpClient = httpClient;
        username = "incorrect username";
        password = "incorrect password";
        grant_type = "password";
        baseUri = "http://registry-qa.mulesoft.com/api";
        contentType = "application/x-www-form-urlencoded";
        clientId = "WEBUI";
        scope = "ADMIN_ORGANIZATIONS READ_SERVICES WRITE_SERVICES CONSUME_SERVICES APPLY_POLICIES READ_CONSUMERS WRITE_CONSUMERS CONTRACT_MGMT CONSUME_POLICIES";
    }

    public synchronized OAuthAuthentication username(String username){
        this.username = username;
        return this;
    }

    public synchronized OAuthAuthentication password(String password){
        this.password = password;
        return this;
    }

    public synchronized OAuthAuthentication baseUri(String baseUri){
        this.baseUri = baseUri;
        return this;
    }

    public synchronized OAuthAuthentication contentType(String contentType){
        this.contentType = contentType;
        return this;
    }

    public synchronized OAuthAuthentication clientId(String clientId){
        this.clientId = clientId;
        return this;
    }

    public  synchronized OAuthAuthentication scope(String scope){
        this.scope = scope;
        return this;
    }

    public synchronized OAuthAuthentication grantType(String grant_type){
        this.grant_type = grant_type;
        return this;
    }

    public synchronized ContentResponse authenticate() throws InterruptedException, ExecutionException, TimeoutException {
        Request request = httpClient
                .newRequest(baseUri + "/access-token")
                .header("Content-Type", contentType)
                .header("grant_type", grant_type)
                .header("username",username)
                .header("password",password)
                .header("client_id",clientId)
                .header("scope", scope);
        request.method(HttpMethod.POST);
        return request.send();
    }

    public synchronized String getNewToken() throws InterruptedException, ExecutionException, TimeoutException, IOException {
        ContentResponse response = authenticate();
        String json = response.getContentAsString();
        String token = findValueInJson(json,"access_token");
        System.out.println("OAuth token: "+ token);
        return token;
    }

    private String findValueInJson(String json,String value) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = mapper.getFactory();
        JsonParser parser = factory.createParser(json);
        JsonNode node = mapper.readTree(parser);
        return node.findValues(value).get(0).asText();
    }
}
