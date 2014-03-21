/*
 * (c) 2003-2014 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
    public class OAuthAuthenticationBuilder {
    private String username;
    private String password;
    private String grantType;
    private String baseUri;
    private String contentType;
    private String clientId;
    private String scope;

    public OAuthAuthenticationBuilder()
    {
        username = "incorrect username";
        password = "incorrect password";
        grantType = "password";
        baseUri = "http://registry-qa.mulesoft.com/api";
        contentType = "application/x-www-form-urlencoded";
        clientId = "WEBUI";
        scope = "ADMIN_ORGANIZATIONS READ_SERVICES WRITE_SERVICES CONSUME_SERVICES APPLY_POLICIES READ_CONSUMERS WRITE_CONSUMERS CONTRACT_MGMT CONSUME_POLICIES";
    }

    public synchronized OAuthAuthenticationBuilder username(String username){
        this.username = username;
        return this;
    }

    public String username(){
        return username;
    }

    public synchronized OAuthAuthenticationBuilder password(String password){
        this.password = password;
        return this;
    }

    public String password(){
        return password;
    }

    public synchronized OAuthAuthenticationBuilder baseUri(String baseUri){
        this.baseUri = baseUri;
        return this;
    }

    public String baseUri(){
        return baseUri;
    }

    public synchronized OAuthAuthenticationBuilder contentType(String contentType){
        this.contentType = contentType;
        return this;
    }

    public String contentType(){
        return contentType;
    }

    public synchronized OAuthAuthenticationBuilder clientId(String clientId){
        this.clientId = clientId;
        return this;
    }

    public String clientId(){
        return clientId;
    }

    public synchronized OAuthAuthenticationBuilder scope(String scope){
        this.scope = scope;
        return this;
    }

    public String scope(){
        return scope;
    }

    public synchronized OAuthAuthenticationBuilder grantType(String grantType){
        this.grantType = grantType;
        return this;
    }

    public String grantType(){
        return grantType;
    }
}