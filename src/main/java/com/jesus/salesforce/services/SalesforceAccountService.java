package com.jesus.salesforce.services;

import com.jesus.salesforce.domain.SalesforceAccount;
import com.jesus.salesforce.repository.SalesforceAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableMongoRepositories(basePackages = {"com.jesus.salesforce.repository"})
public class SalesforceAccountService {

    @Autowired private SalesforceAccountRepository salesforceAccountRepository;

    public List<SalesforceAccount> findAllSalesforceAccount() {
        return salesforceAccountRepository.findAll();
    }
}
