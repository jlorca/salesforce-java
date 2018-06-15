package com.jesus.salesforce.repository;

import com.jesus.salesforce.domain.SalesforceAccount;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesforceAccountRepository extends MongoRepository<SalesforceAccount, String> {
}
