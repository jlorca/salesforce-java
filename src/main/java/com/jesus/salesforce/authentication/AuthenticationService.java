package com.jesus.salesforce.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import com.jesus.salesforce.dtos.SalesforceAuthentication;
import com.jesus.salesforce.dtos.ApplicationConfigs;

public class AuthenticationService {

    private static AuthenticationServiceImpl authenticationServiceImpl;

    public static SalesforceAuthentication getSalesforceAuthentication(ApplicationConfigs applicationConfigs) {
        return getInstance().getSalesforceAuthentication(applicationConfigs);
    }

    private static AuthenticationServiceImpl getInstance() {
        if(authenticationServiceImpl == null) {
            authenticationServiceImpl = new AuthenticationServiceImpl();
        }

        return authenticationServiceImpl;
    }
}
