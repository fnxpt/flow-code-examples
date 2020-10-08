package com.example.flow.service;


import com.backbase.flow.process.annotation.ProcessBean;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;


@ProcessBean
@Service
public class ApplicationRiskService {

    private enum ApplicationRisk {Low, Medium, High}

    private final Logger LOGGER = LoggerFactory.getLogger(ApplicationRiskService.class.getName());

    public void calculateRisk(DelegateExecution execution) {
        int index = new Random().nextInt(ApplicationRisk.values().length);
        ApplicationRisk randomRisk = ApplicationRisk.values()[index];

        execution.setVariable("risk", randomRisk.toString());

    }

}
