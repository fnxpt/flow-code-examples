# Example 1: Migration example with activity match


Create a Database called `send-personal-message-flow`


Start the Flow Application V1

mvn spring-boot:run -Dspring-boot.run.profiles=mysql



bash run_interaction.sh

Start the Migration Application

$ mvn spring-boot:run



http://localhost:9091



Login: demo / demo


View Cockpit, Processes, Send Personal Message



Stop Flow Application V1


Start Flow Application V2

$ mvn spring-boot:run -Dspring-boot.run.profiles=mysql


http://localhost:9091



Login: demo / demo


View Cockpit, Processes, Send Personal Message



