#!/bin/bash

curl --location --request GET 'localhost:9091/migrate' \
--header 'Content-Type: application/json' \
--header 'Cookie: anonymousUserId=myFirstValue1; XSRF-TOKEN=5bfa3aac-affc-4721-9539-9c2fc106f994' \
--data-raw '' 

