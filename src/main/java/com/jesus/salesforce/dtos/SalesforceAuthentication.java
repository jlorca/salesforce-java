package com.jesus.salesforce.dtos;

public class SalesforceAuthentication {

    private String id;
    private String accessToken;
    private String instanceUrl;
    private String tokenType;
    private boolean isSuccess;

    public SalesforceAuthentication(String id, String accessToken, String instanceUrl, String tokenType, boolean isSuccess) {
        this.id = id;
        this.accessToken = accessToken;
        this.instanceUrl = instanceUrl;
        this.tokenType = tokenType;
        this.isSuccess = isSuccess;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getInstanceUrl() {
        return instanceUrl;
    }

    public void setInstanceUrl(String instanceUrl) {
        this.instanceUrl = instanceUrl;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
