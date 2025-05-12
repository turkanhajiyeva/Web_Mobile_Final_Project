CREATE TABLE logininfo (
    user_id CHAR(128) PRIMARY KEY, 
    username VARCHAR(50) NOT NULL,
    password VARCHAR(20) NOT NULL,
    role VARCHAR(30) NOT NULL
);

CREATE TABLE table_information (
    table_id CHAR(36) PRIMARY KEY, 
    table_name VARCHAR(50) NOT NULL,
    qr_code_url VARCHAR(255)
);

CREATE TABLE orders (
    order_id INT PRIMARY KEY AUTO_INCREMENT,
    table_id CHAR(36) NOT NULL,
    order_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10,2),
    FOREIGN KEY (table_id) REFERENCES table_information(table_id)
);

CREATE TABLE order_items (
    order_item_id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT NOT NULL,
    menu_item_id INT NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (menu_item_id) REFERENCES menu_items(id)
);

CREATE TABLE menu_items (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(4,2) NOT NULL,
    category VARCHAR(50)
);
