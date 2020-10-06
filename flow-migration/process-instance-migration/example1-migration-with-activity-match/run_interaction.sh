#!/bin/bash

for i in `seq 1 10`;
do 
curl --location --request POST 'http://localhost:7777/api/send-personal-message-flow/api/interaction/v1/interactions/simple-interaction/actions/simple-step-handler' \
--header 'Content-Type: application/json' \
--header 'Cookie: anonymousUserId=myFirstValue1; XSRF-TOKEN=5bfa3aac-affc-4721-9539-9c2fc106f994' \
--data-raw '{
	"payload": {
		"firstName": "Harry",
		"lastName": "Potter",
		"dateOfBirth": "1990-01-01"
	}
}' &

done
