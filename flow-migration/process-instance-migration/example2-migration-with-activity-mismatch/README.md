# Example 2: Migration example with activity mismatch
The following example describes a scenario where a there is a mismatch between the activities within the source process definition, and the activities within the target process definition exist.  

## Getting Started
The following list defines the technologies and libraries I used to implement the sample code:
* Maven 3.6+
* DBeaver SQL Software 
* Java 11
* Camunda Modeler
* An IDE of your choice:
  * Spring Tool Suite, Eclipse, IntelliJ IDEA Community Edition

## Prerequisites
* Access to Backbase Repository (https://repo.backbase.com)
* Create a local developer environment. See [Backbase Community](https://community.backbase.com/documentation/flow/latest/create_developer_environment)

## Process Definitions
### Order Pizza V1.0.0
The following diagram contains the BPMN for the Order Pizza V1.0.0 process definition.
![Order Pizza v1.0.0](.images/order-pizza-1.0.0.png)

### Order Pizza V1.1.0
The following diagram contains the BPMN for the Order Pizza V1.1.0 process definition.
![Order Pizza v1.0.0](.images/order-pizza-1.1.0.png)

## Application Configuration
The application configuration is the single most important information that is used to instruct the Migration Application on how to migrate the process instances. The application configuration consists mainly of database configuration and also the migration configuration itself.

### Database Configuration
The migration application must connect to the same database as the flow application. The configuration within the application.yaml file is defined as follows:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/order-pizza-flow
    username: root
    password: root
```

### Migration Configuration
The migration configuration specifies the properties used by the migration application to perform the process instance migration. The configuration within the application.yaml file is defined as follows:

```yaml
migration:
  sourceDefinition:
    key: "order-pizza"
    version: "1.0.0"
  targetDefinition:
    key: "order-pizza"
    version: "1.1.0"
  updateEventTriggers: false
  activities:
    - source: "user_task_call_local_franchise"
      target: "user_task_call_customer_complaints"
      updateEventTrigger: false
  skipIoMappings: false
  skipCustomListeners: false
```

The meaning of the properties are as follows:
* The `migration:sourceDefinition:key property` defined the process key of the source process definition. This value is set within the `Id` field on the General tab within the Camunda Modeller application of the process definition.
* The `migration:sourceDefinition:version` property defined the version tag of the source process definition. This value is set within the `Version Tag` field on the General tab within the Camunda Modeller application of the process definition.
* The `migration:targetDefinition:key` property defined the process key of the target process definition. This value is set within the `Id` field on the General tab within the Camunda Modeller application of the process definition.
* The `migration:targetDefinition:version` property defined the version tag of the target process definition. This value is set within the `Version Tag` field on the General tab within the Camunda Modeller application of the process definition.
* The `migration:updateEventTriggers` property toggles whether the migration instructions should include updating of the respective event triggers where appropriate.
* The `migration:activities:source` property defines the id of the activity within the source process definition that is mapped to an activity within the target process definition.
* The `migration:activities:target` property defines the id of the activity within the target process definition that is mapped from an activity within the source process definition.
* The `migration:activities:updateEventTrigger` property toggles whether the migration instruction should include updating of the respective event trigger where appropriate.   
* The `migration:skipIoMappings` property specifies whether custom listeners (task and execution) should be invoked when executing the instructions.
* The `migration:skipCustomListeners` property specifies whether input/output mappings for tasks should be invoked throughout the transaction when executing the instructions.

## Running the Example

### 1. Create a local developer environment
The Flow Applications and the Migration Application requires the Backbase local environment to run. A detailed explanation 
on how to setup the local environment can be found on [Backbase Community](https://community.backbase.com/documentation/flow/latest/create_developer_environment).    

As a minimum start the following:
* MySQL Database
* The Infrastructure and Platform Services
* The Edge service

### 2. Create the database
You can use DBeaver (SQL Software) to connect to the MySQL database to create a database called `order-pizza-flow`.

### 3. Start the Flow Application V1

In a new terminal window, go to `example2-migration-with-activity-mismatch/flow-application-v1` and execute the following command:
```bash
$ mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

The Flow Application will startup and deploy the `order-pizza` process definition with version 1.0.0 to the Camunda BPM engine.

Validate that the Flow Application is up and running by opening the IPS Eureka registry in your browser using: http://localhost:8080/registry/

In a new terminal window, go to `example2-migration-with-activity-mismatch/` and execute the following command:

```bash
$ bash run_interaction.sh
```

The script executes a curl command that starts the interaction engine, which in turn starts an instance of the `order-pizza` process.

### 4. Start the Migration Application

In a new terminal window, go to `example2-migration-with-activity-mismatch/migration-application` and execute the following command:
```bash 
$ mvn spring-boot:run
```

Open the Camunda Cockpit application in your browser using: 

In your browser, navigate to http://localhost:9091 and log in to Camunda Cockpit using the admin credentials:

* Username: demo
* Password: demo

Navigate to the Cockpit web application, select the processes and finally view the "Order Pizza" process definition. You should be able to see the following screen:

![Cockpit](.images/cockpit_001.png) 


### 5. Stop Flow Application V1 & Start Flow Application V2

**Note:** Remember to first stop the Flow Application V1 service.

In a new terminal window, go to `example2-migration-with-activity-mismatch/flow-application-v2` and execute the following command:
```bash
$ mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

The Flow Application will startup and deploy the `order-pizza` process definition with version 1.1.0 to the Camunda BPM engine.

Validate that the Flow Application is up and running by opening the IPS Eureka registry in your browser using: http://localhost:8080/registry/

### 6. View New Process Definition

In your browser, navigate to http://localhost:9091 and log in to Camunda Cockpit using the admin credentials:
* Username: demo
* Password: demo

Navigate to the Cockpit web application, select the processes and finally view the "Order Pizza" process definition. 

**Note:** The Definition Version has changed to a drop-down and the second version is available. You should be able to see the following screen:

![Cockpit](.images/cockpit_002.png) 


### 7. Perform the Migration

In a new terminal window, go to `example2-migration-with-activity-mismatch/` and execute the following command:

```bash
$ bash run_migration.sh
```

The script executes a curl command that starts the execution of the Migration Plan. 

### 8. View Migration Result
In your browser, navigate to http://localhost:9091 and log in to Camunda Cockpit using the admin credentials:
* Username: demo
* Password: demo

Navigate to the Cockpit web application, select the processes and finally view the "Order Pizza" process definition. 

**Note:** Ensure that version 2 is selected and that the page was refreshed. You should be able to see the following screen:

![Cockpit](.images/cockpit_003.png)

