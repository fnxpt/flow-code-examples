package com.example.flow.service;


import com.backbase.flow.process.annotation.ProcessBean;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@ProcessBean
@Service
public class ApplicationMigrationService {


    private final Logger LOGGER = LoggerFactory.getLogger(ApplicationMigrationService.class.getName());

    public void migrateApplication(DelegateExecution execution) {

        String risk = "Medium";
        String advise = "The application risk is: " + risk;

        execution.setVariable("risk", risk);
        execution.setVariable("advise", advise);
    }

}
