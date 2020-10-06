package com.example.flow.process;


import com.backbase.flow.process.annotation.ProcessBean;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;


@ProcessBean
@Service
public class DepartmentSelectorService {

    private enum Departments {HR, Finance}

    private final Logger LOGGER = LoggerFactory.getLogger(DepartmentSelectorService.class.getName());

    public void selectDepartment(DelegateExecution execution) {
        int index = new Random().nextInt(Departments.values().length);
        Departments randomDepartment = Departments.values()[index];

        execution.setVariable("department", randomDepartment);
    }

}
