Order Processing System

Overview

This is a simple Order Processing System built using Spring Boot 3.4.2, GraphQL, ActiveMQ 5.18.6, Hibernate, Spring Data JPA, and MySQL8.0. 
The system supports order creation, retrieval, and processing via a GraphQL API and uses ActiveMQ for messaging.
// How to Run the Project

1️⃣ Prerequisites

I used  and Ensure you have the following installed:

Java 17 
Maven
MySQL(8.0)

ActiveMQ (for messaging)--version(5.18.6)
Postman 

2️⃣ Clone the Repository

git remote add origin https://github.com/sikander724/order-system.git


3️⃣ Configure the Database

Create a MySQL database named order_db and update the application.properties file:

spring.datasource.url=jdbc:mysql://localhost:3306/
#e.g:spring.datasource.url=jdbc:mysql://localhost:3306/Order_Processing_System
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
server.port=8081
4️⃣ Start ActiveMQ
Download ActiveMQ.
Extract and navigate to the bin directory.
Start ActiveMQ:
Activemq start  # For Windows
 ActiveMQ is running by opening http://localhost:8161/admin (default login: admin/admin).
📌 GraphQL API Usage

1️⃣ Create an Order

1. Mutation:
====payload==
Body=raw
header=application/json
url=http://localhost:8081/graphql
{
  "query": "mutation { createOrder(orderInput: { customerName: \"sum\", product: \"tap\", quantity: 1 }) { id customerName product quantity status } }"
}


2️⃣ Fetch All Orders

Query:
payload will be this 
url=http://localhost:8081/graphql method=post
{
  "query": "query { getAllOrders { id customerName product quantity status } }"
}

3️⃣ Fetch Order by ID

Query:

{
  "query": "query { getOrderById(orderId: 10) { id customerName product quantity status } }"
}


🔄 ActiveMQ Processing

1️⃣ How Order Processing Works

When an order is created, it is sent to activemq on the  Queue name (order.queue)
 after the process  after a 15-second delay. Because i delay it to check the pending status of the data

The order status is updated from Pending  and Processed.

2️⃣ Checking ActiveMQ Messages

Open http://localhost:8161/admin

Go to Queues and click on order.queue to browse pending messages.



📽️ Submission Guidelines


git init
git add .
git commit -m "Initial Commit"
git branch -M main
git remote add origin https://github.com/YOUR_GITHUB_USERNAME/order-system.git
git push -u origin main



