package com.jesus.salesforce;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@Controller
public class SalesforceApplication {

	@Value("${amazonProperties.endpointUrl}")
	private String endpointUrl;

	public static void main(String[] args) {
		SpringApplication.run(SalesforceApplication.class, args);
	}

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "HI HI HI!";
	}
}
