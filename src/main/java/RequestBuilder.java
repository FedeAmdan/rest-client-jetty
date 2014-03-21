import org.eclipse.jetty.client.util.BytesContentProvider;
import org.eclipse.jetty.http.HttpMethod;

import java.util.Map;
import java.util.Set;

public class RequestBuilder {

    private String baseUri;
    private String resource;
    private HttpMethod method;
    private Map<String,String> headers;
    private Map<String,String> params;
    private BytesContentProvider payload;

    public RequestBuilder setResource(String resource){
        this.resource = resource;
        return this;
    }
    public String getResource(){
        return resource;
    }

    public RequestBuilder setMethod(HttpMethod method){
        this.method = method;
        return this;
    }

    public HttpMethod getMethod(){
        return method;
    }

    public RequestBuilder addHeader(String key,String value){
        this.headers.put(key,value);
        return this;
    }

    public String getHeader(String key){
        return this.headers.get(key);
    }

    public Set<String> getHeadersSet(){
        return this.headers.keySet();
    }

    public RequestBuilder addParam(String key,String value){
        this.params.put(key,value);
        return this;
    }

    public String getParam(String key){
        return this.params.get(key);
    }

    public Set<String> getParamsSet(){
        return this.params.keySet();
    }

    public RequestBuilder setPayload(String payload){
        this.payload = new BytesContentProvider(payload.getBytes());
        return this;
    }

    public BytesContentProvider getPayload(){
        return payload;
    }

    public RequestBuilder setBaseUri(String baseUri){
        this.baseUri = baseUri;
        return this;
    }

    public  String getBaseUri(){
        return baseUri;
    }
}
