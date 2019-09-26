# Reviews API 
This Project is done as part of Udacity Java developer course. The purpose of the project is to understand the concepts of Spring boot application, JDBC with MySQL, Spring Data JPA, test cases using DataJpaTest.

## Pre-requisites
MySQL needs to be installed and configured. Instructions provided separately.
- JDK 1.8
- Maven 3.6.1 - https://maven.apache.org/download.cgi
- Mysql 

## Getting Started
Install mysql  - https://www.mysql.com/downloads/

### Mysql setup 

After install 
#### Starting mysql server
```
mysql.server start
```
#### Create user and grant privileges
```
CREATE USER review@localhost IDENTIFIED BY 'password';
grant all privileges on *.* to review@localhost with grant option;
```
#### Verify Connection with above user
```
mysql -u review -p
```
on password prompt - enter `password`
#### Create database
```
create database reviewsdb
```
#### Connecting to reviewsdb
```
use reviewsdb
```

### Clone this repository
```
git clone https://github.com/rajashekar/reviews-api.git
```

### Run
```
cd reviews-api
mvn spring-boot:run
```

### Review Api Examples 
#### Create product
```
curl -v -H 'Content-type: application/json' \
    localhost:8080/products/ \
    -d '{"name": "iPhone", "description": "Phone with i", "price": "1000"}'
```
#### Verify product in mysql
```
mysql> select * from product;
+----+--------+--------------+-------+
| ID | NAME   | DESCRIPTION  | PRICE |
+----+--------+--------------+-------+
|  1 | iPhone | Phone with i |  1000 |
+----+--------+--------------+-------+
```
#### Get product with id
```
curl localhost:8080/products/1
{"id":1,"name":"iPhone","description":"Phone with i","price":1000}
```
#### Get all products
```
curl localhost:8080/products/
[{"id":1,"name":"iPhone","description":"Phone with i","price":1000},{"id":2,"name":"Pixel","description":"Phone with x","price":1000}]
```
####  Create Review
```
curl -v -H 'Content-type: application/json' localhost:8080/reviews/products/1 -d '{"username": "Tyler Durden", "rating": 3, "title": "its just a phone with i", "body": "i seems more egotistical"}'
{"id":1,"username":"Tyler Durden","rating":3,"title":"its just a phone with i","body":"i seems more egotistical","product":{"id":1,"name":"iPhone","description":"Phone with i","price":1000}}
```
#### Verify review in mysql
```
mysql> select * from review;
+----+--------------+--------+-------------------------+--------------------------+------------+
| ID | USERNAME     | RATING | TITLE                   | BODY                     | PRODUCT_ID |
+----+--------------+--------+-------------------------+--------------------------+------------+
|  1 | Tyler Durden |      3 | its just a phone with i | i seems more egotistical |          1 |
+----+--------------+--------+-------------------------+--------------------------+------------+
```
#### Get review by id
```
curl -v -H 'Content-type: application/json' localhost:8080/reviews/products/1
[{"id":1,"username":"Tyler Durden","rating":3,"title":"its just a phone with i","body":"i seems more egotistical","product":{"id":1,"name":"iPhone","description":"Phone with i","price":1000}}]
```
#### Create Comment
```
curl -v -H 'Content-type: application/json' localhost:8080/comments/reviews/1 -d '{"username": "Neo", "body": "Tyler, Stop worrying about i, follow white rabbit"}'
{"id":1,"body":"Tyler, Stop worrying about i, follow white rabbit","username":"Neo","review":{"id":1,"username":"Tyler Durden","rating":3,"title":"its just a phone with i","body":"i seems more egotistical","product":{"id":1,"name":"iPhone","description":"Phone with i","price":1000}}}
```
#### Verify comment in mysql
```
mysql> select * from comment;
+----+---------------------------------------------------+----------+-----------+
| ID | BODY                                              | USERNAME | REVIEW_ID |
+----+---------------------------------------------------+----------+-----------+
|  1 | Tyler, Stop worrying about i, follow white rabbit | Neo      |         1 |
+----+---------------------------------------------------+----------+-----------+
```
#### Get Comment by id
```
curl -v -H 'Content-type: application/json' localhost:8080/comments/reviews/1
[{"id":1,"body":"Tyler, Stop worrying about i, follow white rabbit","username":"Neo","review":{"id":1,"username":"Tyler Durden","rating":3,"title":"its just a phone with i","body":"i seems more egotistical","product":{"id":1,"name":"iPhone","description":"Phone with i","price":1000}}}]
```

## Contributing
This repository is done as part of Udacity Java developer. Therefore, most likely will not accept any pull requests.