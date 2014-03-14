import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.util.ssl.SslContextFactory;

public class RestClient {


    public HttpClient httpClient;
    public String baseUri;
    public String username;
    public String password;
    public Action action;
    public OAuthAuthentication oauth;

    public RestClient(String baseUri, String username, String password,String grant_type) throws Exception {
        this.baseUri = baseUri;
        this.username = username;
        this.password = password;
        SslContextFactory sslContextFactory = new SslContextFactory();
        httpClient = new HttpClient(sslContextFactory);
        httpClient.start();
        oauth = new OAuthAuthentication(httpClient,username,password,grant_type);
        action = new Action(baseUri, httpClient, oauth);
    }

    public ContentResponse get(String resource) throws Exception {
        return action.act(HttpMethod.GET,resource,"application/vnd.mulesoft.habitat+json");
    }

    public ContentResponse post(String resource, String payload) throws Exception {
        return action.act(HttpMethod.POST,resource,payload,"application/vnd.mulesoft.habitat+json");
    }

    public ContentResponse post(String resource) throws Exception {
        return action.act(HttpMethod.POST,resource,"application/vnd.mulesoft.habitat+json");
    }

    public ContentResponse put(String resource, String payload) throws Exception {
        return action.act(HttpMethod.PUT,resource,payload,"application/vnd.mulesoft.habitat+json");
    }

    public ContentResponse delete(String resource) throws Exception {
        return action.act(HttpMethod.DELETE,resource,"application/json");
    }

}
