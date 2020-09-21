package com.backbase.flow.examples;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.migration.MigrationPlan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpgradeMainFromV1ToV2 {

    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private RuntimeService runtimeService;

    private final Logger LOGGER = LoggerFactory.getLogger(UpgradeMainFromV1ToV2.class.getName());


    public void executeMigration() {

        MigrationPlan migrationPlan = processEngine.getRuntimeService()
                .createMigrationPlan("send-personal-message:2:35cd11dc-f9b5-11ea-b468-acde48001122", "send-personal-message:3:7728ada2-f9b6-11ea-ad43-acde48001122")
                .mapEqualActivities()
                .build();

        // MigrationPlanReportDto report = MigrationPlanReportDto.emptyReport();

        runtimeService
                .newMigration(migrationPlan)
                .processInstanceIds("484cc9be-f9b5-11ea-b468-acde48001122", "464009c3-f9b5-11ea-b468-acde48001122", "44769458-f9b5-11ea-b468-acde48001122", "41d5357d-f9b5-11ea-b468-acde48001122")
                .execute();

    }

}
