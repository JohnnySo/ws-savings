print('################################################################# START #################################################################');
db.createUser(
    {
        user: "savings",
        pwd: "dummy",
        roles: [
            {
                role: "readWrite",
                db: "savings"
            }
        ]
    }
);
db.createCollection("categories");
db.createCollection("users");
db.createCollection("subcategories");
print('################################################################# END #################################################################');