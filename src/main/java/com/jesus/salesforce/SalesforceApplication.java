package com.jesus.salesforce;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
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

	@Value("${salesforceCredentials.environment}")
	private String environment;

	@Value("${salesforceCredentials.clientId}")
	private String clientId;

	@Value("${salesforceCredentials.clientSecret}")
	private String clientSecret;

	@Value("${salesforceCredentials.redirectUri}")
	private String redirectUri;

	@Value("${salesforceCredentials.restUrl}")
	private String restUrl;

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "MAIN!";
	}

	@RequestMapping("/jesus")
	@ResponseBody
	String other() {
		HttpClient httpclient = new HttpClient();
		GetMethod getMethod = new GetMethod(restUrl);
		getMethod.setRequestHeader("Authorization", "OAuth " + clientId);
//		getMethod.addParameter("grant_type","authorization_code");
//
//		// For session ID instead of OAuth 2.0, use "grant_type", "password"
//		getMethod.addParameter("client_id",clientId);
//		getMethod.addParameter("client_secret",clientSecret);
//		getMethod.addParameter("redirect_uri",redirectUri);

		String responseBody = null;
		try {
			httpclient.executeMethod(getMethod);
			responseBody = getMethod.getResponseBodyAsString();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return responseBody;
	}

	public static void main(String[] args) {
		SpringApplication.run(SalesforceApplication.class, args);
	}
}
