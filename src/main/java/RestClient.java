/*
 * (c) 2003-2014 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.BytesContentProvider;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

//import org.eclipse.jetty.client.util.BytesContentProvider;

public class RestClient {
    private HttpClient httpClient;
    private OAuthAuthentication oAuth;
    private String baseUri;
    private String resource;
    private HttpMethod method;
    private Map<String,String> headers;
    private Map<String,String> params;
    private BytesContentProvider payload;
    private String token;


    public RestClient(String baseUri, String username, String password,String contentType,String clientId, String grant_type, String scope) throws Exception {
        this.baseUri = baseUri;
        SslContextFactory sslContextFactory = new SslContextFactory();
        httpClient = new HttpClient(sslContextFactory);
        httpClient.start();
        oAuth = new OAuthAuthentication(httpClient);
        oAuth.baseUri(baseUri)
             .username(username)
             .password(password)
             .contentType(contentType)
             .clientId(clientId)
             .scope(scope);

        headers = new HashMap<String, String>();
        params = new HashMap<String, String>();
        token = oAuth.getNewToken();
    }

    public RestClient(String baseUri, OAuthAuthenticationBuilder builder) throws Exception {
        this.baseUri = baseUri;
        SslContextFactory sslContextFactory = new SslContextFactory();
        httpClient = new HttpClient(sslContextFactory);
        httpClient.start();
        oAuth = new OAuthAuthentication(httpClient);
        oAuth.baseUri(baseUri)
                .username(builder.username())
                .password(builder.password())
                .contentType(builder.contentType())
                .clientId(builder.clientId())
                .scope(builder.scope());

        headers = new HashMap<String, String>();
        params = new HashMap<String, String>();
        token = oAuth.getNewToken();
    }

    public ContentResponse send() throws InterruptedException, ExecutionException, TimeoutException, IOException {
        String urlRequest = baseUri + resource;
        Request httpRequest = httpClient
                .newRequest(urlRequest)
                .param("access_token", token);
        for (String key : headers.keySet()) {
            String value = headers.get(key);
            httpRequest.header(key,value);
        }
        for (String key : params.keySet()){
            String value = params.get(key);
            httpRequest.param(key,value);
        }

        httpRequest.method(method);
        if (payload != null)
            httpRequest.content(payload);

        if (token == null){
            token = oAuth.getNewToken();
        }
        ContentResponse response = httpRequest.send();
        if (response.getStatus() == 403){
            token = oAuth.getNewToken();
            response = httpRequest.send();
        }
        return response;
    }

    public RestClient setResource(String resource){
        this.resource = resource;
        return this;
    }

    public RestClient method(HttpMethod method){
        this.method = method;
        return this;
    }

    public RestClient header(String key,String value){
        this.headers.put(key,value);
        return this;
    }

    public RestClient param(String key,String value){
        this.params.put(key,value);
        return this;
    }

    public RestClient payload(String payload){
        this.payload = new BytesContentProvider(payload.getBytes());
        return this;
    }

    public RestClient baseUri(String baseUri){
        this.baseUri = baseUri;
        return this;
    }

    public synchronized void oAuthUsername(String username){
        oAuth.username(username);
    }

    public synchronized void oAuthPassword(String password){
        oAuth.password(password);
    }

    public RestClient get(String resource){
        this.method = HttpMethod.GET;
        this.resource = resource;
        return this;
    }

    public RestClient post(String resource){
        this.method = HttpMethod.POST;
        this.resource = resource;
        return this;
    }

    public RestClient put(String resource){
        this.method = HttpMethod.PUT;
        this.resource = resource;
        return this;
    }

    public RestClient delete(String resource){
        this.method = HttpMethod.DELETE;
        this.resource = resource;
        return this;
    }
}
