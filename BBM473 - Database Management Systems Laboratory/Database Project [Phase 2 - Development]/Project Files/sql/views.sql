-- View 1: Finding Products That Which Sales More and Makes Money More
CREATE VIEW Best_Sales AS
SELECT name, description, price, cart_item.quantity AS sold_count, (product.price*cart_item.quantity) AS total_revenue
FROM orders
INNER JOIN cart_item
ON orders.session_id = cart_item.session_id
INNER JOIN product
ON product.product_id = cart_item.product_id
GROUP BY product.product_id;

-- Checking View 1 
SELECT * FROM Best_Sales;

-- View 2: Finding Best Customers (Who Buys More)
CREATE VIEW Best_Customers AS
SELECT name, surname, SUM(total_amount) as total_shopping
FROM users INNER JOIN payment
ON users.user_id = payment.customer_id
GROUP BY user_id
ORDER BY SUM(total_amount) DESC;

-- Checking View 2
SELECT * FROM Best_Customers; 

-- View 3: Checking Stocks of Products By Categories
CREATE VIEW Stock_Status AS
SELECT product_id, product.name, description, category.name, price, stock
FROM product INNER JOIN category
WHERE product.category_id = category.category_id
GROUP BY product_id
ORDER BY MAX(stock) DESC;

-- Checking View 3
SELECT * FROM Stock_Status;

-- View 4: Checking Whether Cargo & Shipment Is Done
CREATE VIEW Deliveries_That_Not_Completed AS
SELECT delivery.delivery_id, status FROM delivery
INNER JOIN delivery_status
ON delivery_status.delivery_id = delivery.delivery_id
WHERE delivery_status.status = 'on the way' OR delivery_status.status = 'preparing';

-- Checking View 4
SELECT * FROM Deliveries_That_Not_Completed;

-- Note: In the project, i'll develop it and i'll try to calculate difference of it between max_delivery_day of premium and platin customers.

-- View 5: Checking Whether Is There Any Order That Not Given To Cargo (Also Product Details)
CREATE VIEW Orders_That_Not_Given_To_Cargo AS
SELECT orders.order_id, status, orders.created_at, name, quantity, price as total_price
FROM orders JOIN order_status
ON orders.order_id = order_status.order_id
JOIN cart_item
ON cart_item.session_id = orders.session_id
JOIN product 
ON product.product_id = cart_item.product_id
WHERE order_status.status = 'preparing';

-- Checking View 5
SELECT * FROM Orders_That_Not_Given_To_Cargo;