import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.BytesContentProvider;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import java.util.HashMap;
import java.util.Map;

//import org.eclipse.jetty.client.util.BytesContentProvider;

public class RestClient {


    private HttpClient httpClient;
    private String username;
    private String password;
    private OAuthAuthentication oAuth;
    private String baseUri;
    private String resource;
    private HttpMethod method;
    private Map<String,String> headers;
    private Map<String,String> params;
    private BytesContentProvider payload;
    private RequestBuilder builder;
    private String token;


    public RestClient(String baseUri, String username, String password,String grant_type) throws Exception {
        this.baseUri = baseUri;
        builder = new RequestBuilder();
        builder.setBaseUri(baseUri);
        this.username = username;
        this.password = password;
        SslContextFactory sslContextFactory = new SslContextFactory();
        httpClient = new HttpClient(sslContextFactory);
        httpClient.start();
        oAuth = new OAuthAuthentication(httpClient,username,password,grant_type);
        headers = new HashMap<String, String>();
        params = new HashMap<String, String>();
        token = oAuth.getNewToken();
    }

    /////////////// METHODS CREATED TO MAKE SHORTER REQUESTS /////////////////////////

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

    public ContentResponse send() throws Exception {
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

}
