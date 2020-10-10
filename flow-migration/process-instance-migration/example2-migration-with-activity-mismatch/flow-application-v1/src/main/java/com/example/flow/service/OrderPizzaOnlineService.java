package com.example.flow.service;


import com.backbase.flow.process.annotation.ProcessBean;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;


@ProcessBean
@Service
public class OrderPizzaOnlineService {

    private enum PizzaType {Pepperoni, Margherita, Hawaiian}

    private final Logger LOGGER = LoggerFactory.getLogger(OrderPizzaOnlineService.class.getName());

    public void orderPizza(DelegateExecution execution) {
        int index = new Random().nextInt(PizzaType.values().length);
        PizzaType randomPizza = PizzaType.values()[index];

        String businessKey = execution.getProcessBusinessKey();
        LOGGER.info("Business Key: " + businessKey);
        execution.setVariable("pizza_type", randomPizza);

    }

}
