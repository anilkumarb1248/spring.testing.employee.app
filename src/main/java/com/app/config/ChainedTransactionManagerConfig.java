package com.app.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

//@Configuration  // Not working
public class ChainedTransactionManagerConfig {
	
	@Bean(name = "chainedTransactionManager")
    public ChainedTransactionManager transactionManager(@Qualifier("employeeTransactionManager") PlatformTransactionManager txnMngr1,
                                                    @Qualifier("departmentTransactionManager") PlatformTransactionManager txnMngr2) {
         return new ChainedTransactionManager(txnMngr1, txnMngr2);
    }

}
