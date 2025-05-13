CREATE TABLE IF NOT EXISTS logininfo (
    user_id CHAR(128) PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(20) NOT NULL,
    role VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS table_information (
    table_id UUID PRIMARY KEY, 
    table_name VARCHAR(50) NOT NULL,
    qr_code_url VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS menu_items (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    category VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS orders (
    order_id SERIAL PRIMARY KEY,
    table_id UUID NOT NULL,
    order_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10,2),
    status VARCHAR(50) NOT NULL DEFAULT 'pending',
    FOREIGN KEY (table_id) REFERENCES table_information(table_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS order_items (
    order_item_id SERIAL PRIMARY KEY,
    order_id INT NOT NULL,
    menu_item_id INT NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
    FOREIGN KEY (menu_item_id) REFERENCES menu_items(id) ON DELETE CASCADE
);
