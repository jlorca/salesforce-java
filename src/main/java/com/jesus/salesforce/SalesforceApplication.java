package com.jesus.salesforce;

import com.jesus.salesforce.authentication.AuthenticationService;
import com.jesus.salesforce.domain.SalesforceAccount;
import com.jesus.salesforce.dtos.ApplicationConfigs;
import com.jesus.salesforce.dtos.SalesforceAuthentication;
import com.jesus.salesforce.services.SalesforceAccountService;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
@Controller
@ComponentScan(basePackages = {"com.jesus.salesforce"})
public class SalesforceApplication {

	@Autowired ApplicationConfigs applicationConfigs;
	@Autowired SalesforceAccountService salesforceAccountService;

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

	@RequestMapping("/accounts")
	@ResponseBody
	public String findAllSalesforceAccounts() {
		List<SalesforceAccount> salesforceAccounts = salesforceAccountService.findAllSalesforceAccount();

		if(salesforceAccounts != null && salesforceAccounts.size() > 0) {
			String accountNames = "";
			for(SalesforceAccount salesforceAccount : salesforceAccounts) {
				accountNames += salesforceAccount.getName() + " ";
			}

			return accountNames;
		} else {
			return "NO ACCOUNTS FROM DB";
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(SalesforceApplication.class, args);
	}
}
