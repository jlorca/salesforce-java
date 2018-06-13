package com.jesus.salesforce.authentication;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jesus.salesforce.dtos.SalesforceAuthentication;
import com.jesus.salesforce.dtos.ApplicationConfigs;

import java.io.IOException;

public class AuthenticationServiceImpl implements IAuthenticationService {

    public SalesforceAuthentication getSalesforceAuthentication(ApplicationConfigs applicationConfigs) {
        HttpClient httpclient = HttpClientBuilder.create().build();

        String loginURL = generateLoginUrl(applicationConfigs);

        // to login a post request is required
        HttpPost httpPost = new HttpPost(loginURL);

        HttpResponse response = null;
        try {
            response = httpclient.execute(httpPost);
        } catch (Exception ex) {
            System.out.println("--> ex = " + ex.getMessage());
        }

        int statusCode = response.getStatusLine().getStatusCode();
        boolean isSuccess = statusCode == HttpStatus.SC_OK;
        if (!isSuccess) {
            System.out.println("Error authenticating to Force.com: " + statusCode);
            return null;
        }

        String result = null;
        try {
            result = EntityUtils.toString(response.getEntity());
        } catch (IOException ioException) {
            // Handle system IO exception
        }
        JSONObject resultJsonObject = null;
        SalesforceAuthentication salesforceAuthentication = null;
        try {
            resultJsonObject = (JSONObject) new JSONTokener(result).nextValue();
            salesforceAuthentication = generateAuthentication(resultJsonObject, isSuccess);
        } catch (JSONException jsonException) {
            // Handle JSON exception
        }

        httpPost.releaseConnection();

        return salesforceAuthentication;
    }

    private String generateLoginUrl(ApplicationConfigs applicationConfigs) {
        return applicationConfigs.getLoginUrl() + applicationConfigs.getGrantService() +
                "&client_id=" + applicationConfigs.getClientId() +
                "&client_secret=" + applicationConfigs.getClientSecret() +
                "&username=" + applicationConfigs.getUsername() +
                "&password=" + applicationConfigs.getPassword();
    }

    private SalesforceAuthentication generateAuthentication(JSONObject jsonObject, boolean isSuccess) {
        SalesforceAuthentication salesforceAuthentication = null;
        if(jsonObject != null) {
            String accessToken = jsonObject.getString("access_token");
            String instanceUrl = jsonObject.getString("instance_url");
            String id = jsonObject.getString("id");
            String tokenType = jsonObject.getString("token_type");

            salesforceAuthentication = new SalesforceAuthentication(id, accessToken, instanceUrl, tokenType, isSuccess);
        }

        return salesforceAuthentication;
    }
}
