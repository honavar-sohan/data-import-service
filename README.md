# Data Import Service

This project is divided in 2 parts
* data-service 
    - This service is used to import that in db, currently it only read csv file and inserts data in mysql
* data-service-api
    - data service api is the api that can used  to query reviews

# Stack Used
* Spring Boot Web
* Sping Batch Job
* Spring JPA

# Steps to run
 this will start the mysql 
```
docker run --name=mysql -d  -p 3306:3306 -e MYSQL_ROOT_PASSWORD=password -e MYSQL_ROOT_HOST=% -e MYSQL_DATABASE=test  mysql/mysql-server
```
steps to run the data job
```
cd  data-service 
```
build the required jar
```
mvn clean install
```
run the jar
```
java -jar target/data-service-1.0-SNAPSHOT.jar
```
once the job is completed successfully 
run the api service
```
cd data-service-api
mvn clean install
java -jar target/data-service-api-0.0.1-SNAPSHOT.
```

Check the output
```
curl --location --request POST 'localhost:8080/reviews/products' \
--header 'Content-Type: application/json' \
--data-raw '{
    "productId" : 2
}'
```


