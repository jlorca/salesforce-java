package com.jesus.salesforce;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@SpringBootApplication
@Controller
public class SalesforceApplication {

//	@Value("${salesforceCredentials.environment}")
//	private String environment;
//
//	@Value("${salesforceCredentials.clientId2}")
//	private String clientId;
//
//	@Value("${salesforceCredentials.clientSecret}")
//	private String clientSecret;
//
//	@Value("${salesforceCredentials.redirectUri}")
//	private String redirectUri;
//
//	@Value("${salesforceCredentials.restUrl}")
//	private String restUrl;

	@Value("${salesforceCredentials.loginUrl}")
	private String loginUrl;

	@Value("${salesforceCredentials.clientId}")
	private String clientId;

	@Value("${salesforceCredentials.clientSecret}")
	private String clientSecret;

	@Value("${salesforceCredentials.grantService}")
	private String grantService;

	@Value("${salesforceCredentials.username}")
	private String username;

	@Value("${salesforceCredentials.password}")
	private String password;

	@Value("${salesforceCredentials.restUrl}")
	private String restUrl;

	private String sessionId;

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "MAIN!";
	}

	@RequestMapping("/jesus")
	@ResponseBody
	String other() throws Exception {
		authenticateWithSalesforce();

		org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient();
		GetMethod getMethod = new GetMethod(restUrl + "/JesusAccounts");
		getMethod.setRequestHeader("Authorization", "Bearer " + sessionId);

		String responseBody = null;
		try {
			httpClient.executeMethod(getMethod);
			responseBody = getMethod.getResponseBodyAsString();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return responseBody;
	}

	private void authenticateWithSalesforce() {
		HttpClient httpclient = HttpClientBuilder.create().build();

		String loginURL = loginUrl +
				grantService +
				"&client_id=" + clientId +
				"&client_secret=" + clientSecret +
				"&username=" + username +
				"&password=" + password;

		HttpPost httpPost = new HttpPost(loginURL);
		HttpResponse response = null;

		try {
			// Execute the login POST request
			response = httpclient.execute(httpPost);
		} catch (ClientProtocolException cpException) {
			// Handle protocol exception
		} catch (IOException ioException) {
			// Handle system IO exception
		}

		// verify response is HTTP OK
		final int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != HttpStatus.SC_OK) {
			System.out.println("Error authenticating to Force.com: "+statusCode);
			return;
		}

		String getResult = null;
		try {
			getResult = EntityUtils.toString(response.getEntity());
		} catch (IOException ioException) {
			// Handle system IO exception
		}
		JSONObject jsonObject = null;
		try {
			jsonObject = (JSONObject) new JSONTokener(getResult).nextValue();
			sessionId = jsonObject.getString("access_token");
		} catch (JSONException jsonException) {
			// Handle JSON exception
		}

		// release connection
		httpPost.releaseConnection();
	}

	public static void main(String[] args) {
		SpringApplication.run(SalesforceApplication.class, args);
	}
}
