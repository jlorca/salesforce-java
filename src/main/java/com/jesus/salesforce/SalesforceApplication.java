package com.jesus.salesforce;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jesus.salesforce.dtos.ApplicationConfigs;
import com.jesus.salesforce.dtos.SalesforceAuthentication;
import com.jesus.salesforce.authentication.AuthenticationService;

import java.io.IOException;

@SpringBootApplication
@Controller
public class SalesforceApplication {

	@Autowired ApplicationConfigs applicationConfigs;

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "MAINN!";
	}

	@RequestMapping("/jesus")
	@ResponseBody
	public String other() throws Exception {
		SalesforceAuthentication salesforceAuthentication = AuthenticationService.getSalesforceAuthentication(applicationConfigs);
		if(salesforceAuthentication != null && salesforceAuthentication.isSuccess()) {
			org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient();
			GetMethod getMethod = new GetMethod(applicationConfigs.getRestUrl() + "/JesusAccounts");
			getMethod.setRequestHeader("Authorization", "Bearer " + salesforceAuthentication.getAccessToken());

			String responseBody = null;
			try {
				httpClient.executeMethod(getMethod);
				responseBody = getMethod.getResponseBodyAsString();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return responseBody;
		}

		return "Something went wrong during the authentication";
	}

	public static void main(String[] args) {
		SpringApplication.run(SalesforceApplication.class, args);
	}
}
