#! /bin/bash
mongoimport -d ${MONGO_INITDB_DATABASE} -c categories --file /import-data/categories.json --jsonArray
mongoimport -d ${MONGO_INITDB_DATABASE} -c subcategories --file /import-data/subcategories.json --jsonArray
mongoimport -d ${MONGO_INITDB_DATABASE} -c users --file /import-data/user.json