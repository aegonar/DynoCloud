#!/bin/bash

export DATA='{"username": "agonar","password": 1234}'

# auth=$(curl -s -X POST \
#  -H "Content-Type: application/json" \
#  -d "${DATA}" \
#  http://localhost/api/login)

# # curl -X GET \
# #  -H "Content-Type: application/json" \
# #  -H "Authorization: Bearer $auth" \
# # http://localhost/api/telemetry

# echo $auth 

# curl -X GET \
#  -H "Content-Type: application/json" \
#  -H "Authorization: Bearer $auth" \
# http://localhost/api/viewUser

# # curl -X GET \
# #  -H "Content-Type: application/json" \
# # -i http://localhost/api/hello


# echo

# curl -X POST \
#  -H "Content-Type: application/json" \
#  -H "Authorization: Bearer $auth" \
# -i http://localhost/api/logout

# echo

# curl -X GET \
#  -H "Content-Type: application/json" \
# -i http://dynocare.xyz/api/hello

# echo

# auth=$(curl -s -X POST \
#  -H "Content-Type: application/json" \
#  -d "${DATA}" \
#  http://dynocare.xyz/api/login)

# echo $auth

# curl -X GET \
#  -H "Content-Type: application/json" \
#  -H "Authorization: Bearer $auth" \
# http://dynocare.xyz/api/viewUser

# echo

# curl -X POST \
#  -H "Content-Type: application/json" \
#  -H "Authorization: Bearer $auth" \
# -i http://dynocare.xyz/api/logout

# echo
# curl -X GET \
#  -H "Content-Type: application/json" \
#  -H "Authorization: Bearer $auth" \
# http://dynocare.xyz/api/telemetry

# echo


# echo

# auth=$(curl -s -X POST \
#  -H "Content-Type: application/json" \
#  -d "${DATA}" \
#  http://192.168.0.253/api/login)

# echo $auth

# curl -X GET \
#  -H "Content-Type: application/json" \
#  -H "Authorization: Bearer $auth" \
# http://192.168.0.253/api/viewUser

# echo

# curl -X POST \
#  -H "Content-Type: application/json" \
#  -H "Authorization: Bearer $auth" \
# http://192.168.0.253/api/logout

#export reg='{"username": "agonar","password": 1234, "name":"Alejandro", "lastname":"Gonzalez", "email":"penis", "phone":""}'
#export reg='{"userID":"001", "userName": "agonar", "name":"Alejandro", "lastName":"Gonzalez", "email":"penis", "phone":"7873415476"}'
export reg='{"userName": "654654", "password":"ebin", "name":"Alejandro", "lastName":"Gonzalez", "email":"email@mail.com", "phone":"7873415476"}'


curl -X POST \
 -H "Content-Type: application/json" \
 -d "${reg}" \
-i http://localhost/server_api/register
 echo