package com.example.flow.service;


import com.backbase.flow.process.annotation.ProcessBean;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@ProcessBean
@Service
public class ApplicationProcessingService {


    private final Logger LOGGER = LoggerFactory.getLogger(ApplicationProcessingService.class.getName());

    public void processApplication(DelegateExecution execution) {

        String risk = (String) execution.getVariable("risk");
        String advise = "The application risk is: " + risk;

        execution.setVariable("advise", advise);
    }

}
