# Spring AMQP and RabbitMQ

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![RabbitMQ](https://img.shields.io/badge/Rabbitmq-FF6600?style=for-the-badge&logo=rabbitmq&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)

Peer-to-peer messaging: This is the distribution pattern used in message queues with a one-to-one relationship between the sender and recipient of the message. Each message in the queue is sent to only one recipient and is used only once. Peer-to-peer messaging is called when a message is to be acted upon only once. Examples of suitable use cases for this style of messaging include payroll and financial transaction processing. In these systems, both senders and recipients need a guarantee that each payment will be sent once and only once.


## Table of Contents

- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Database](#database)
- [Author](#author)


## Installation

1. Clone the repository:

```bash
git clone https://github.com/setxpro/message-brocker-rabbitmq.git
```
2. Install dependencies with Maven

3. Run docker-compose up to create database and rabbitmq image

## Configuration

1. Create a configuration in `application.yml`

```yaml
server:
  - port: 8080 

spring:
  datasource:
    username: root
    url: jdbc:mysql://localhost:3306/orders?createDatabaseIfNotExists=true&serverTimezone=UTC&useSSL=false
    password: ''
  jpa:
    show-sql: 'true'
    hibernate:
      ddl-auto: 'update'

  rabbitmq:
    host: localhost
    port: 5672
    username: rabbitmq
    password: rabbitmq
```

2. Create a configuration in `pom.xml`

```xml
<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>com.mysql</groupId>
			<artifactId>mysql-connector-j</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-amqp</artifactId>
		</dependency>

	</dependencies>
```

3. Create a job with docker in `docker-compose.yml`
	
	```yml
	
	services:
  		mysql-8:
  		  image: mysql:8.0.18
  		  command: --default-authentication-plugin=mysql_native_password
  		  environment:
  		    MYSQL_ALLOW_EMPTY_PASSWORD: "yes"
  		    MYSQL_ROOT_PASSWORD: ""
  		    MYSQL_DATABASE: "orders"
  		  ports:
  		    - "3306:3306"
		
	```

## Usage

After your realized all configurations

1. Start the application with Maven
2. Start docker containers Rabbitmq and mysql

## API Endpoints
The API provides the following endpoints:
    
    Endpoints
        [order-service]  - http://localhost:8080/v1/order


```json
    {
        "id": 16,
        "value": 2500,
        "paid": false
    }
    
```

## RabbitMQ

    Endpoints
        [PAINEL] - http://localhost:15672/
        [CONNECTION] - http://localhost:5672/

    Queue
        [notification-service] - orders.v1.order-created.send-notification
        [cashback-service] - orders.v1.order-created.generate-cashback

## DATABASE

Mysql - created with Docker

## Author

👤 **Patrick Anjos**