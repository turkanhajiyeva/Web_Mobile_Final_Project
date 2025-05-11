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

INSERT INTO orders (table_id, total_amount, status)
VALUES
('a1b2c3d4-e5f6-7890-abcd-ef1234567890', 22.47, 'completed'),
('a1b2c3d4-e5f6-7890-abcd-ef1234567890', 13.98, 'pending');

-- Order 1: 1x Pizza (id: 1), 2x Wings (id: 2)
INSERT INTO order_items (order_id, menu_item_id, quantity)
VALUES
(1, 1, 1),
(1, 2, 2);

-- Order 2: 2x Iced Tea (id: 3)
INSERT INTO order_items (order_id, menu_item_id, quantity)
VALUES
(2, 3, 2);