-- Insert tables into table_information
INSERT INTO table_information (table_id, table_name, qr_code_url)
VALUES 
    ('d7b1a5d2-3c9c-4f2e-bf08-9a9d8f42b1b1', 'Table 1', ''),
    ('a3f7b8d5-c2b9-40b7-bcd5-f8f29a5f3f56', 'Table 2', ''),
    ('56f3e33e-ca89-468c-9ef9-df0a1fdea9ea','Table 3',''),
    ('67078c88-802d-45d0-a091-29060e016299','Table 4','')
ON CONFLICT (table_id) DO NOTHING;

-- Insert menu items
INSERT INTO menu_items (name, description, price, category)
VALUES
    -- Appetizers
    ('Tarkhun French Fries', 'Crispy fries seasoned with Tarkhun herb for a unique flavor', 7.49, 'Appetizers'),
    ('Tarkhun Chicken Wings', 'Spicy buffalo sauce with a side of ranch', 9.99, 'Appetizers'),
    ('Tarkhun Mozzarella Sticks', 'Crispy cheese sticks with marinara dipping sauce', 6.99, 'Appetizers'),
    ('Crispy Calamari', 'Served with a side of tangy marinara sauce', 8.99, 'Appetizers'),
    ('Bruschetta', 'Toasted bread topped with diced tomatoes, basil, and balsamic glaze', 6.99, 'Appetizers'),
    ('Stuffed Mushrooms', 'Mushrooms stuffed with a creamy cheese and herb filling, baked to golden perfection', 7.49, 'Appetizers'),
    ('Spinach and Artichoke Dip', 'Creamy spinach and artichoke dip, served with crispy tortilla chips', 8.49, 'Appetizers'),
    ('Chicken Skewers', 'Grilled chicken skewers marinated in a smoky barbecue sauce, served with a dipping sauce', 9.49, 'Appetizers'),
    ('Fried Zucchini Sticks', 'Lightly battered and fried zucchini sticks, served with marinara sauce', 7.99, 'Appetizers'),
    ('Fish Tacos', 'Crispy battered fish served in soft corn tortillas with shredded cabbage, salsa, and crema', 8.99, 'Appetizers'),
    ('Charcuterie Board', 'An assortment of cured meats, cheeses, olives, and crackers', 12.99, 'Appetizers'),

    -- Main Courses
    ('Tarkhun Steak', 'A tender steak infused with Tarkhun herb, served with mashed potatoes and vegetables', 14.99, 'Main Courses'),
    ('Arizona Hot Pepperoni Pizza', 'A spicy pepperoni pizza with a zesty kick, topped with fresh peppers and mozzarella', 12.99, 'Main Courses'),
    ('Texas BBQ Ribs', 'Slow-cooked ribs with BBQ sauce and coleslaw', 18.99, 'Main Courses'),
    ('Pineapple Jalapeño Pizza', 'Pizza topped with pineapple, jalapeños, and mozzarella', 13.49, 'Main Courses'),
    ('Grilled Salmon Fillet', 'Served with roasted vegetables and a lemon butter sauce', 16.49, 'Main Courses'),
    ('Margarita Pizza', 'Classic pizza with fresh mozzarella, tomatoes, and basil', 11.99, 'Main Courses'),
    ('Spaghetti Carbonara', 'Classic pasta with eggs, pancetta, and parmesan', 13.49, 'Main Courses'),
    ('BBQ Chicken Pizza', 'Pizza with grilled chicken, BBQ sauce, and red onions', 12.49, 'Main Courses'),
    ('Lemon Herb Grilled Chicken', 'Grilled chicken breast marinated in a lemon herb sauce, served with roasted potatoes and steamed broccoli', 15.49, 'Main Courses'),
    ('Beef Wellington', 'Tender beef wrapped in puff pastry with mushroom duxelles, served with a side of mashed potatoes and gravy', 24.99, 'Main Courses'),
    ('Vegetarian Lasagna', 'Layers of pasta, ricotta cheese, spinach, and marinara sauce, baked to perfection', 12.99, 'Main Courses'),
    ('Seafood Paella', 'A traditional Spanish rice dish with shrimp, mussels, clams, and chorizo', 19.99, 'Main Courses'),
    ('Chicken Parmesan', 'Breaded chicken cutlet topped with marinara sauce and melted mozzarella, served with spaghetti', 14.49, 'Main Courses'),
    ('Grilled Ribeye Steak', 'A perfectly grilled ribeye steak, served with garlic butter and a side of crispy fries', 22.99, 'Main Courses'),
    ('Lobster Tail', 'Butter-poached lobster tail, served with a side of asparagus and mashed potatoes', 29.99, 'Main Courses'),

    -- Drinks
    ('Tarkhun Vodka', 'A refreshing vodka cocktail made with Tarkhun herb for a distinctive taste', 1.99, 'Drinks'),
    ('Tremor Cocktail', 'A bold cocktail with freshly squeezed lemon juice for a tangy and invigorating flavor', 2.49, 'Drinks'),
    ('Tarkhun Iced Tea', 'Refreshing sweetened tea served with lemon', 2.99, 'Drinks'),
    ('Sunset Margarita', 'Classic margarita with lime and tequila', 5.49, 'Drinks'),
    ('Tarkhun Lemonade', 'Freshly squeezed lemonade with a hint of mint', 3.49, 'Drinks'),
    ('Berry Fizz Cocktail', 'A mix of fresh berries, sparkling water, and vodka', 4.99, 'Drinks'),
    ('Tarkhun Lemon Iced Tea', 'A refreshing blend of lemon and iced tea', 3.99, 'Drinks'),
    ('Peach Bellini', 'Prosecco with peach puree for a sweet and bubbly treat', 5.99, 'Drinks')
ON CONFLICT (id) DO NOTHING;

-- Insert login information
INSERT INTO logininfo (user_id, username, password, role)
VALUES 
    ('127a597a-228f-4547-8ab3-e23b951237a6','a_r_i_z_o_na', 'passwordweb', 'Kitchen Staff'),
    ('e3c611d8-5bcb-438c-9ce0-61fb2d4acd87','lilithanchaos', 'passwordmobile', 'Manager'),
    ('41d6dd1d-2e65-4503-b3c2-910e13c6e345','ilpalazz0', 'passwordfinal', 'Kitchen Staff'),
    ('59180e0d-055d-42c0-9944-63ff24e4a3e3','crimsonV0', 'passwordproject', 'Waiter'),
    ('495d1c38-86e7-4bd0-93e9-ebe735d58b22','SevereHedgehog', 'passwordcookie', 'Waiter')
ON CONFLICT (user_id) DO NOTHING;

INSERT INTO orders (order_id, table_id, total_amount, status)
VALUES (1, 'd7b1a5d2-3c9c-4f2e-bf08-9a9d8f42b1b1', 21.98, 'pending')
ON CONFLICT (order_id) DO NOTHING;


INSERT INTO order_items (order_item_id, order_id, menu_item_id, quantity)
VALUES 
    (1, 1, 1, 2) 
ON CONFLICT (order_item_id) DO NOTHING;