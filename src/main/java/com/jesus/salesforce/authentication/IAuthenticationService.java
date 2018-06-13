package com.jesus.salesforce.authentication;

import com.jesus.salesforce.dtos.SalesforceAuthentication;
import com.jesus.salesforce.dtos.ApplicationConfigs;

public interface IAuthenticationService {
    public SalesforceAuthentication getSalesforceAuthentication(ApplicationConfigs applicationConfigs);
}
