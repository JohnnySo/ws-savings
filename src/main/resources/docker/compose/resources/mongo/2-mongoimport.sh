#! /bin/bash

mongoimport -u ${MONGO_INITDB_ROOT_USERNAME} -p ${MONGO_INITDB_ROOT_PASSWORD} --authenticationDatabase=admin \
-d ${MONGO_INITDB_DATABASE} -c categories --file /categories.json --jsonArray

mongoimport -u ${MONGO_INITDB_ROOT_USERNAME} -p ${MONGO_INITDB_ROOT_PASSWORD} --authenticationDatabase=admin \
-d ${MONGO_INITDB_DATABASE} -c subcategories --file /subcategories.json --jsonArray

mongoimport -u ${MONGO_INITDB_ROOT_USERNAME} -p ${MONGO_INITDB_ROOT_PASSWORD} --authenticationDatabase=admin \
-d ${MONGO_INITDB_DATABASE} -c users --file /user.json