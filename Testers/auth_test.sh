#!/bin/bash

export DATA='{"username": "agonar","password": 1234}'

auth=$(curl -s -X POST \
 -H "Content-Type: application/json" \
 -d "${DATA}" \
 http://localhost/api/login)

# curl -X GET \
#  -H "Content-Type: application/json" \
#  -H "Authorization: Bearer $auth" \
# http://localhost/api/telemetry

echo 

curl -X GET \
 -H "Content-Type: application/json" \
 -H "Authorization: Bearer $auth" \
http://localhost/api/viewUser

# curl -X GET \
#  -H "Content-Type: application/json" \
# -i http://localhost/api/hello

# echo $auth
echo

curl -X POST \
 -H "Content-Type: application/json" \
 -H "Authorization: Bearer $auth" \
-i http://localhost/api/logout

echo

# curl -X GET \
#  -H "Content-Type: application/json" \
# -i http://dynocare.xyz/api/hello

# echo

# auth=$(curl -s -X POST \
#  -H "Content-Type: application/json" \
#  -d "${DATA}" \
#  http://dynocare.xyz/api/authentication)

# echo

# curl -X GET \
#  -H "Content-Type: application/json" \
#  -H "Authorization: Bearer $auth" \
# http://dynocare.xyz/api/viewUser

# echo

# curl -X GET \
#  -H "Content-Type: application/json" \
#  -H "Authorization: Bearer $auth" \
# http://dynocare.xyz/api/telemetry

# echo