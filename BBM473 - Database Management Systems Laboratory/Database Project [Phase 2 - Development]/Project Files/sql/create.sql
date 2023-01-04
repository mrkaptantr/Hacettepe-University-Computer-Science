-- For serial checks
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS supplier;
DROP TABLE IF EXISTS admin;
DROP TABLE IF EXISTS customer;
DROP TABLE IF EXISTS standart_customer;
DROP TABLE IF EXISTS premium_customer;
DROP TABLE IF EXISTS platin_customer;
DROP TABLE IF EXISTS company;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS shopping_session;
DROP TABLE IF EXISTS cart_item;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS order_status;
DROP TABLE IF EXISTS payment;
DROP TABLE IF EXISTS cash_payment;
DROP TABLE IF EXISTS credit_card_payment;
DROP TABLE IF EXISTS discount;
DROP TABLE IF EXISTS delivery;
DROP TABLE IF EXISTS delivery_status;

-- Creating tables
CREATE TABLE users (
  user_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  name varchar(20),
  surname varchar(15),
  address varchar(41),
  country varchar(15),
  city varchar(10),
  phone varchar(11),
  password varchar(15),
  created_at date,
  FOREIGN KEY (user_id) REFERENCES shopping_session(user_id)
);

CREATE TABLE admin (
  admin_id INTEGER PRIMARY KEY REFERENCES users(user_id) NOT NULL
);

CREATE TABLE supplier (
  supplier_id INTEGER PRIMARY KEY REFERENCES users(user_id) NOT NULL,
  company_id INTEGER NOT NULL,
  FOREIGN KEY (company_id) REFERENCES company(company_id)
);

CREATE TABLE customer (
  customer_id INTEGER PRIMARY KEY REFERENCES users(user_id) NOT NULL,
  birthdate date,
  credit_card_id INTEGER NOT NULL,
  FOREIGN KEY (credit_card_id) REFERENCES credit_card_payment(card_number)
);

CREATE TABLE standart_customer (
  customer_id INTEGER PRIMARY KEY REFERENCES customer(customer_id) NOT NULL
);

CREATE TABLE premium_customer (
  customer_id INTEGER PRIMARY KEY REFERENCES customer(customer_id) NOT NULL,
  discount_id INTEGER NOT NULL,
  max_delivery_day INTEGER NOT NULL,
  start_date date,
  end_date date NOT NULL,
  FOREIGN KEY (discount_id) REFERENCES discount(discount_id)
);

CREATE TABLE platin_customer (
  customer_id INTEGER PRIMARY KEY REFERENCES customer(customer_id) NOT NULL,
  discount_id INTEGER NOT NULL,
  max_delivery_day INTEGER NOT NULL,
  start_date date,
  end_date date NOT NULL,
  FOREIGN KEY (discount_id) REFERENCES discount(discount_id)
);

CREATE TABLE company (
  company_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  name varchar(41),
  category_id INTEGER,
  logo_url varchar(50),
  FOREIGN KEY (category_id) REFERENCES category(category_id),
  FOREIGN KEY (company_id) REFERENCES supplier(company_id)
);

CREATE TABLE category (
  category_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  name varchar(20),
  type varchar(20),
  img_url varchar(50),
  FOREIGN KEY (category_id) REFERENCES company(category_id),
  FOREIGN KEY (category_id) REFERENCES product(category_id)
);

CREATE TABLE product (
  product_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  name varchar(20),
  description varchar(80),
  img_url varchar(50),
  category_id INTEGER,
  price DECIMAL,
  discount_perc DECIMAL,
  created_at date,
  modified_at date,
  deleted_at date,
  stock INTEGER NOT NULL,
  FOREIGN KEY (category_id) REFERENCES category(category_id),
  FOREIGN KEY (product_id) REFERENCES cart_item(product_id)
);

CREATE TABLE shopping_session (
  session_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  user_id INTEGER NOT NULL,
  total INTEGER,
  created_at date,
  updated_at date,
  FOREIGN KEY (user_id) REFERENCES users(user_id),
  FOREIGN KEY (session_id) REFERENCES cart_item(session_id),
  FOREIGN KEY (session_id) REFERENCES orders(session_id)
);

CREATE TABLE cart_item (
  cart_item_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  session_id INTEGER NOT NULL,
  product_id INTEGER NOT NULL,
  quantity INTEGER,
  created_at date,
  modified_at date,
  FOREIGN KEY (session_id) REFERENCES shopping_session(session_id),
  FOREIGN KEY (product_id) REFERENCES product(product_id)
);

CREATE TABLE orders (
  order_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  session_id INTEGER NOT NULL,
  created_at date,
  payment_id INTEGER NOT NULL,
  delivery_id INTEGER NOT NULL,
  FOREIGN KEY (session_id) REFERENCES shopping_session(session_id),
  FOREIGN KEY (payment_id) REFERENCES payment(payment_id),
  FOREIGN KEY (delivery_id) REFERENCES delivery(delivery_id),
  FOREIGN KEY (order_id) REFERENCES order_status(order_id)
);

CREATE TABLE order_status (
  order_id INTEGER PRIMARY KEY REFERENCES orders(order_id) NOT NULL,
  status varchar(10)
);

CREATE TABLE payment (
  payment_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  customer_id INTEGER NOT NULL,
  payment_method varchar(11),
  total_amount INTEGER,
  discount_id INTEGER,
  FOREIGN KEY (customer_id) REFERENCES customer(customer_id),
  FOREIGN KEY (discount_id) REFERENCES discount(discount_id),
  FOREIGN KEY (payment_id) REFERENCES orders(payment_id)
);

CREATE TABLE cash_payment (
  payment_id INTEGER PRIMARY KEY REFERENCES payment(payment_id) NOT NULL,
  discount_id INTEGER,
  FOREIGN KEY (discount_id) REFERENCES discount(discount_id)
);

CREATE TABLE credit_card_payment (
  payment_id INTEGER PRIMARY KEY REFERENCES payment(payment_id) NOT NULL,
  card_number INTEGER NOT NULL,
  installment_number INTEGER NOT NULL,
  FOREIGN KEY (card_number) REFERENCES customer(credit_card_id)
);

CREATE TABLE discount (
  discount_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  percentage DECIMAL NOT NULL,
  FOREIGN KEY (discount_id) REFERENCES premium_customer(discount_id),
  FOREIGN KEY (discount_id) REFERENCES platin_customer(discount_id),
  FOREIGN KEY (discount_id) REFERENCES cash_payment(discount_id)
);

CREATE TABLE delivery (
  delivery_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  delivery_method varchar(15),
  delivered_at date,
  FOREIGN KEY (delivery_id) REFERENCES delivery_status(delivery_id),
  FOREIGN KEY (delivery_id) REFERENCES orders(delivery_id)
);

CREATE TABLE delivery_status (
  status_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  delivery_id INTEGER NOT NULL,
  status varchar(10),
  FOREIGN KEY (delivery_id) REFERENCES delivery(delivery_id)
);

-- No need sequences or triggers for auto increase of primary key values, since we used AUTOINCREMENT keyword. It's easier. :)
