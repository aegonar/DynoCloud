#!/bin/bash

export DATA='{"username": "agonar","password": 1234}'

# auth=$(curl -s -X POST \
#  -H "Content-Type: application/json" \
#  -d "${DATA}" \
#  http://localhost/api/authentication)

# curl -X GET \
#  -H "Content-Type: application/json" \
#  -H "Authorization: Bearer $auth" \
# http://localhost/api/telemetry

# echo 

# curl -X GET \
#  -H "Content-Type: application/json" \
#  -H "Authorization: Bearer $auth" \
# http://localhost/api/viewUser

# echo 

curl -X GET \
 -H "Content-Type: application/json" \
-i http://dynocare.xyz/api/hello

echo

auth=$(curl -s -X POST \
 -H "Content-Type: application/json" \
 -d "${DATA}" \
 http://dynocare.xyz/api/authentication)

echo

curl -X GET \
 -H "Content-Type: application/json" \
 -H "Authorization: Bearer $auth" \
http://dynocare.xyz/api/viewUser

echo

curl -X GET \
 -H "Content-Type: application/json" \
 -H "Authorization: Bearer $auth" \
http://dynocare.xyz/api/telemetry

echo