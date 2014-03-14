import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.BytesContentProvider;
import org.eclipse.jetty.http.HttpMethod;

import java.util.Map;

public class Action {

    public String baseUri;
    public HttpClient httpClient;
    private OAuthAuthentication oauth;

    public Action(String baseUri, HttpClient httpClient, OAuthAuthentication oauth){
        this.baseUri = baseUri;
        this.httpClient = httpClient;
        this.oauth = oauth;
    }

    public ContentResponse act(HttpMethod method, String resource,Map<String,String> headers,Map<String,String> params) throws Exception {

        String token = oauth.getToken();

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

        request.method(method);
        return request.send();
    }

    public ContentResponse act(HttpMethod method, String resource,String contentType) throws Exception {
        String token = oauth.getToken();

        String urlRequest = baseUri + resource;
        Request request = httpClient
                .newRequest(urlRequest)
                .param("access_token", token)
                .header("Content-Type", contentType);
        request.method(method);
        return request.send();
    }

    public ContentResponse act(HttpMethod method, String resource, String payload,String contentType) throws Exception {
        String token = oauth.getToken();

        String urlRequest = baseUri + resource;
        Request request = httpClient
                .newRequest(urlRequest)
                .param("access_token", token)
                .header("Content-Type", contentType)
                .content(new BytesContentProvider(payload.getBytes()));

        request.method(method);
        return request.send();
    }
}
