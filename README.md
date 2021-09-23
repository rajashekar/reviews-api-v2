# Reviews API 
Reviews API built using Spring boot application, JDBC with MySQL, Spring Data JPA, JDBC with Mongo DB, test cases using DataJpaTest and DataMongoTest.

## Pre-requisites
MySQL needs to be installed and configured. Instructions provided separately.
- JDK 1.8
- Maven 3.6.1 - https://maven.apache.org/download.cgi
- Mysql 
- Mongo db 

## Getting Started
Install mysql  - https://www.mysql.com/downloads/
Install mongo db - https://www.mongodb.com/download-center/community

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

### Mongo db setup
After install of mongo db
on mongo console
#### Create reviewsdb database
```
> use reviewsdb
switched to db reviewsdb
```
#### Create review user
```
> db.createUser({"user": "review", "pwd": "password", "roles": ["readWrite","dbAdmin"]});
Successfully added user: { "user" : "review", "roles" : [ "readWrite", "dbAdmin" ] }
```
#### Verify the connection
```
mongo "mongodb://review:password@localhost:27017/reviewsdb"
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

### Verify review in mongo
```
> show collections;
reviews
> db.reviews.find().pretty()
{
	"_id" : 1,
	"username" : "Tyler Durden",
	"rating" : 3,
	"title" : "its just a phone with i",
	"body" : "i seems more egotistical",
	"product" : {
		"_id" : 1,
		"name" : "iPhone",
		"description" : "Phone with i",
		"price" : 1000
	},
	"comments" : [ ],
	"_class" : "com.udacity.course3.reviews.entity.Reviews"
}
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

#### Verify comment in mongo db
```
> db.reviews.find().pretty()
{
	"_id" : 1,
	"username" : "Tyler Durden",
	"rating" : 3,
	"title" : "its just a phone with i",
	"body" : "i seems more egotistical",
	"product" : {
		"_id" : 1,
		"name" : "iPhone",
		"description" : "Phone with i",
		"price" : 1000
	},
	"comments" : [
		{
			"_id" : 1,
			"body" : "Tyler, Stop worrying about i, follow white rabbit",
			"username" : "Neo",
			"review" : {
				"_id" : 1,
				"username" : "Tyler Durden",
				"rating" : 3,
				"title" : "its just a phone with i",
				"body" : "i seems more egotistical",
				"product" : {
					"_id" : 1,
					"name" : "iPhone",
					"description" : "Phone with i",
					"price" : 1000
				}
			}
		}
	],
	"_class" : "com.udacity.course3.reviews.entity.Reviews"
}
```

#### Get Comment by id
```
curl -v -H 'Content-type: application/json' localhost:8080/comments/reviews/1
[{"id":1,"body":"Tyler, Stop worrying about i, follow white rabbit","username":"Neo","review":{"id":1,"username":"Tyler Durden","rating":3,"title":"its just a phone with i","body":"i seems more egotistical","product":{"id":1,"name":"iPhone","description":"Phone with i","price":1000}}}]
```
