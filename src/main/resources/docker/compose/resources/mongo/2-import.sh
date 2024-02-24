#! /bin/bash
mongoimport -d ${MONGO_INITDB_DATABASE} -c categories --file /categories.json --jsonArray
mongoimport -d ${MONGO_INITDB_DATABASE} -c subcategories --file /subcategories.json --jsonArray
mongoimport -d ${MONGO_INITDB_DATABASE} -c users --file /user.json