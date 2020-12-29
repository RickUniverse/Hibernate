CREATE DATABASE hibernate5;
USE hibernate5;

DROP TABLE news;
CREATE TABLE news(
	id INT PRIMARY KEY AUTO_INCREMENT,
	title VARCHAR(100),
	author VARCHAR(11),
	`date` DATETIME
)

SELECT * FROM news;

CREATE TABLE workers(
	id INT PRIMARY KEY AUTO_INCREMENT,
	NAME VARCHAR(44),
	yearplay DOUBLE(10,4)
)

INSERT INTO order_table(customer_id) VALUES(NULL)
DESC order_table;
SELECT * FROM order_table
SELECT * FROM `customer_table`