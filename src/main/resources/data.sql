INSERT INTO products (name, price, stock, category, is_active, created_at)
VALUES ('Iphone 14', 900.00, 15, 'PHONE', true, CURRENT_TIMESTAMP);
INSERT INTO products (name, price, stock, category, is_active, created_at)
VALUES ('Iphone 15', 1200.00, 10, 'PHONE', true, CURRENT_TIMESTAMP);
INSERT INTO products (name, price, stock, category, is_active, created_at)
VALUES ('Samsung S23', 850.00, 20, 'PHONE', true, CURRENT_TIMESTAMP);
INSERT INTO products (name, price, stock, category, is_active, created_at)
VALUES ('MacBook Air M2', 1500.00, 5, 'LAPTOP', true, CURRENT_TIMESTAMP);
INSERT INTO products (name, price, stock, category, is_active, created_at)
VALUES ('MacBook Pro M3', 2200.00, 3, 'LAPTOP', true, CURRENT_TIMESTAMP);
INSERT INTO products (name, price, stock, category, is_active, created_at)
VALUES ('Dell XPS 15', 1800.00, 7, 'LAPTOP', true, CURRENT_TIMESTAMP);
INSERT INTO products (name, price, stock, category, is_active, created_at)
VALUES ('AirPods Pro', 250.00, 25, 'ACCESSORY', true, CURRENT_TIMESTAMP);
INSERT INTO products (name, price, stock, category, is_active, created_at)
VALUES ('Apple Watch Ultra', 700.00, 8, 'WATCH', true, CURRENT_TIMESTAMP);
INSERT INTO products (name, price, stock, category, is_active, created_at)
VALUES ('Logitech Mouse', 60.00, 40, 'ACCESSORY', true, CURRENT_TIMESTAMP);
INSERT INTO products (name, price, stock, category, is_active, created_at)
VALUES ('Mechanical Keyboard', 120.00, 30, 'ACCESSORY', true, CURRENT_TIMESTAMP);
INSERT INTO orders (customer_name, customer_email, order_date, status, total_amount)
VALUES ('Ali Valiyev', 'ali@gmail.com', CURRENT_TIMESTAMP, 'PENDING', 2100.00);
INSERT INTO orders (customer_name, customer_email, order_date, status, total_amount)
VALUES ('Bekzod Karimov', 'bekzod@gmail.com', CURRENT_TIMESTAMP, 'CONFIRMED', 1500.00);
INSERT INTO orders (customer_name, customer_email, order_date, status, total_amount)
VALUES ('Sarvar Aliyev', 'sarvar@gmail.com', CURRENT_TIMESTAMP, 'SHIPPED', 1150.00);
INSERT INTO orders (customer_name, customer_email, order_date, status, total_amount)
VALUES ('Ali Valiyev', 'ali@gmail.com', CURRENT_TIMESTAMP, 'DELIVERED', 250.00);
INSERT INTO orders (customer_name, customer_email, order_date, status, total_amount)
VALUES ('Dilshod Akbarov', 'dilshod@gmail.com', CURRENT_TIMESTAMP, 'CANCELLED', 1800.00);
INSERT INTO order_item (order_id, product_id, quantity, unit_price, total_price)
VALUES (1, 2, 1, 1200.00, 1200.00);
INSERT INTO order_item (order_id, product_id, quantity, unit_price, total_price)
VALUES (1, 4, 1, 1500.00, 1500.00);
INSERT INTO order_item (order_id, product_id, quantity, unit_price, total_price)
VALUES (2, 4, 1, 1500.00, 1500.00);
INSERT INTO order_item (order_id, product_id, quantity, unit_price, total_price)
VALUES (3, 1, 1, 900.00, 900.00);
INSERT INTO order_item (order_id, product_id, quantity, unit_price, total_price)
VALUES (3, 7, 1, 250.00, 250.00);
INSERT INTO order_item (order_id, product_id, quantity, unit_price, total_price)
VALUES (4, 7, 1, 250.00, 250.00);
INSERT INTO order_item (order_id, product_id, quantity, unit_price, total_price)
VALUES (5, 6, 1, 1800.00, 1800.00);
