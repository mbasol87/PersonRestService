This project is Person Rest Service. It allows CRUD application Person/Parent Relationship.
It is based on Spring Boot + Maven + PostreSql + Hibernate.

Some Example CRUD Rest Commands --

POST Method -

curl -X POST -H 'Content-Type: application/json' -d '{
"firstName":"Danny",
"lastName":"Simpson",
"birthday":[1999,3,3],
"gender":"Male",
"parents":[{
"firstName":"Homer",
"lastName":"Simpson",
"birthday":[1983,1,1],
"relationship":"Father"}, {
"firstName":"Marge",
"lastName":"Simpson",
"birthday":[1980,2,2],
"relationship":"Mother" }]
}' http://localhost:8080/api/persons -i


curl -X POST -H 'Content-Type: application/json' -d '{
"firstName":"Homer",
"lastName":"Simpson",
"birthday":[1985,1,1],
"gender":"Male",
"nationality":"USA",
"parents":[]}
}' http://localhost:8080/api/persons -i

curl -X POST -H 'Content-Type: application/json' -d '{
"firstName":"Marge",
"lastName":"Simpson",
"birthday":[1985,1,1],
"gender":"Female",
"nationality":"USA",
"parents":[]}
}' http://localhost:8080/api/persons -i


curl -X POST -H 'Content-Type: application/json' -d '{
"firstName":"Liza",
"lastName":"Simpson",
"birthday":[1999,1,1],
"gender":"Female",
"parents":[{
"firstName":"Marge",
"lastName":"Simpson",
"birthday":[1985,1,1],
"relationship":"Mother"}]
}' http://localhost:8080/api/persons -i


curl -X POST -H 'Content-Type: application/json' -d '{
"firstName":"Bart",
"lastName":"Simpson",
"birthday":[2000,3,3],
"gender":"Male",
"parents":[{
"firstName":"Nelly",
"lastName":"Simpson",
"birthday":[1985,1,1],
"relationship":"Mother"}]
}' http://localhost:8080/api/persons -i

PUT Method -

curl -X PUT -H 'Content-Type: application/json' -d '{
"firstName":"Danny",
"lastName":"Simpson",
"birthday":[1999,3,3],
"gender":"Male",
"parents":[{
"firstName":"Homer",
"lastName":"Simpson",
"birthday":[2000,1,1],
"relationship":"Father"}]
}' http://localhost:8080/api/persons/1 -i

curl -X PUT -H 'Content-Type: application/json' -d '{
"firstName":"Liza",
"lastName":"Simpson",
"birthday":[2005,1,1],
"gender":"Female",
"nationality":"USA",
"parents":[{
"firstName":"Memphis",
"lastName":"Simpson",
"birthday":[1970,1,1],
"gender":"Male",
"nationality":"USA"
}]}' http://localhost:8080/api/persons/9 -i

GET Method --

http://localhost:8080/api/persons
http://localhost:8080/api/person/1

DELETE Method --

curl -X DELETE -H 'Content-Type: application/json' http://localhost:8080/api/person/1 -i
